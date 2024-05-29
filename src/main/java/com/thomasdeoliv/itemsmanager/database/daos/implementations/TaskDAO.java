package com.thomasdeoliv.itemsmanager.database.daos.implementations;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.daos.ITaskDAO;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryFailedException;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryType;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements ITaskDAO {

    private String url;
    private String userName;
    private String userPassword;

    /**
     * Task DAO constructor.
     */
    public TaskDAO(Configuration configuration) {
        try {
            this.url = configuration.getDatabaseUrl();
            this.userName = configuration.getDatabaseUsername();
            this.userPassword = configuration.getDatabaseUserPassword();
        } catch (IOException e) {
            ErrorDialog.handleException(e);
        }
    }

    @Override
    public List<Task> getAllTasks(Long projectId) throws QueryFailedException {
        // Instantiate a list
        List<Task> tasks = new ArrayList<>();
        // Open a connection to the database.
        try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
            // Query
            String query = """
                    SELECT *
                    FROM items_manager_schema.item
                    WHERE items_manager_schema.item.item_related_item_id IS NOT NULL
                    	AND items_manager_schema.item.item_related_item_id = ?
                    ORDER BY items_manager_schema.item.item_start_at DESC;
                    """;
            // Create a statement
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Define params for query
                statement.setLong(1, projectId);
                // Fetch results
                ResultSet rs = statement.executeQuery();
                // Loop on each provided results
                while (rs.next()) {
                    // Instantiate a project object
                    Task task = new Task();
                    // Fetch endAt column value
                    @Nullable
                    Timestamp endAt = rs.getTimestamp("item_end_at");
                    // Fill all fields
                    task.setId(rs.getLong("item_id"));
                    task.setName(rs.getString("item_name"));
                    task.setDescription(rs.getString("item_description"));
                    task.setStartedAt(rs.getTimestamp("item_start_at").toLocalDateTime());
                    task.setEndedAt(endAt != null ? endAt.toLocalDateTime() : null);
                    task.setRelatedItemId(rs.getLong("item_related_item_id"));
                    // Add project to list
                    tasks.add(task);
                }
                // Return statement
                return tasks;
            }
        } catch (SQLException ex) {
            // Return statement
            throw new QueryFailedException(QueryType.SELECT, Task.class.getSimpleName(), ex);
        }
    }

    @Override
    public @Nullable Task getTaskById(Long id) throws QueryFailedException {
        // Open a connection to the database.
        try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
            // Query
            String query = """
                    SELECT *
                    FROM items_manager_schema.item
                    WHERE items_manager_schema.item.item_related_item_id IS NOT NULL
                      AND items_manager_schema.item.item_id = ?
                    ORDER BY items_manager_schema.item.item_start_at DESC;
                    """;
            // Create a statement
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Prepare query
                statement.setLong(1, id);
                // Execute query
                ResultSet rs = statement.executeQuery();
                // Declare Task variable
                Task task = null;
                // Loop on each provided results
                while (rs.next()) {
                    // Instantiate a task object
                    task = new Task();

                    // Fetch endAt column value
                    @Nullable Timestamp endAt = rs.getTimestamp("item_end_at");

                    // Fill all fields
                    task.setId(rs.getLong("item_id"));
                    task.setName(rs.getString("item_name"));
                    task.setDescription(rs.getString("item_description"));
                    task.setStartedAt(rs.getTimestamp("item_start_at").toLocalDateTime());
                    task.setEndedAt(endAt != null ? endAt.toLocalDateTime() : null);
                    task.setRelatedItemId(rs.getLong("item_related_item_id"));
                }
                // Return
                return task;
            }
        } catch (SQLException ex) {
            // Return statement
            throw new QueryFailedException(QueryType.SELECT, Task.class.getSimpleName(), ex);
        }
    }

    @Override
    public void saveEntity(Task entity) throws QueryFailedException {
        // Validate datas
        if (entity.getEndedAt() != null || entity.getRelatedItemId() == null) {
            throw new IllegalArgumentException("Invalid datas provided.");
        }
        // Open a connection to the database.
        try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
            // Query
            String query = """
                    INSERT INTO items_manager_schema.item(item_name, item_description, item_related_item_id)
                    VALUES (?, ?, ?);
                    """;
            // Create a statement
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set parameters
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getDescription());
                statement.setLong(3, entity.getRelatedItemId());
                // Execute query
                ResultSet rs = statement.executeQuery();
                // Ensure query success
                if (!rs.next()) {
                    throw new SQLException("Cannot save this task.");
                }
            }
        } catch (SQLException ex) {
            // Return statement
            throw new QueryFailedException(QueryType.INSERT, Task.class.getSimpleName(), ex);
        }
    }

    @Override
    public void updateEntity(Task entity) throws QueryFailedException {
        // Validate datas
        if (entity.getRelatedItemId() == null) {
            throw new IllegalArgumentException("Invalid datas provided.");
        }
        // Open a connection to the database.
        try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
            // Query
            String query = """
                    UPDATE items_manager_schema.item
                    SET
                    	item_name = ?,
                    	item_description = ?,
                    	item_end_at = ?,
                    	item_related_item_id = ?
                    WHERE items_manager_schema.item.item_related_item_id IS NOT NULL
                    	AND items_manager_schema.item.item_id = ?;
                    """;
            // Create a statement
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set parameters
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getDescription());
                // Set conditionally parameters
                if (entity.getEndedAt() != null) {
                    statement.setTimestamp(3, Timestamp.valueOf(entity.getEndedAt()));
                } else {
                    statement.setNull(3, Types.TIMESTAMP);
                }
                // Set parameters
                statement.setLong(4, entity.getRelatedItemId());
                statement.setLong(5, entity.getId());
                // Execute query
                ResultSet rs = statement.executeQuery();
                // Ensure query success
                if (!rs.next()) {
                    throw new SQLException("Cannot delete this task.");
                }
            }
        } catch (SQLException ex) {
            // Return statement
            throw new QueryFailedException(QueryType.UPDATE, Task.class.getSimpleName(), ex);
        }
    }

    @Override
    public void deleteEntity(Long id) throws QueryFailedException {
        // Open a connection to the database.
        try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
            // Query
            String query = """
                    DELETE
                    FROM items_manager_schema.item
                    WHERE items_manager_schema.item.item_related_item_id IS NOT NULL
                    	AND items_manager_schema.item.item_id = ?
                    """;
            // Create a statement
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set parameters
                statement.setLong(1, id);
                // Execute query
                ResultSet rs = statement.executeQuery();
                // Ensure query success
                if (!rs.next()) {
                    throw new SQLException("Cannot delete this task.");
                }
            }
        } catch (SQLException ex) {
            // Return statement
            throw new QueryFailedException(QueryType.DELETE, Task.class.getSimpleName(), ex);
        }
    }
}
