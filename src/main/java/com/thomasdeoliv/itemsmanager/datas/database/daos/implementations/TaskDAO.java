package com.thomasdeoliv.itemsmanager.datas.database.daos.implementations;

import com.thomasdeoliv.itemsmanager.datas.config.Configuration;

import com.thomasdeoliv.itemsmanager.datas.database.daos.ITaskDAO;
import com.thomasdeoliv.itemsmanager.datas.database.entities.implementations.Task;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements ITaskDAO {

    private final String url;
    private final String userName;
    private final String userPassword;

    /**
     * Task DAO constructor.
     */
    public TaskDAO(Configuration configuration) {
        this.url = configuration.getDatabaseUrl();
        this.userName = configuration.getDatabaseUsername();
        this.userPassword = configuration.getDatabaseUserPassword();
    }

    @Override
    public List<Task> getAllProjects(Long projectId) throws SQLException {
        // Instantiate a list of tasks.
        List<Task> tasks = new ArrayList<Task>();

        // Create query to get all tasks (no related ID)
        String query = """
            SELECT *
            FROM tasks_manager_schema.item
            WHERE tasks_manager_schema.item.item_related_item_id = ?;
        """;

        // Prepare a PreparedStatement from previous query
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setLong(1, projectId);

        // Open a connection to the database.
        try (Connection conn = DriverManager.getConnection(this.url, this.userName, this.userPassword);

             ResultSet rs = stmt.executeQuery()) {

            // Loop
            while (rs.next()) {

                // Get columns values
                long id = rs.getLong("item_id");
                String title = rs.getString("item_name");
                String description = rs.getString("item_description");
                boolean isActive = rs.getBoolean("item_is_active");
                Timestamp startAt = rs.getTimestamp("item_start_at");
                @Nullable Timestamp endAt = rs.getTimestamp("item_end_at");
                long relatedItemId = rs.getLong("item_related_item_id");

                // Instantiate a task object
                Task task = new Task();

                // Fill all fields
                task.setId(id);
                task.setName(title);
                task.setDescription(description);
                task.setIsActive(isActive);
                task.setStartedAt(startAt.toLocalDateTime());
                task.setEndedAt(endAt != null ? endAt.toLocalDateTime() : null);

                // Add task to list
                tasks.add(task);
            }

            // Close connection
            conn.close();
        } catch (SQLException e) {
            // Print stack trace
            e.printStackTrace();
        }

        // Return statement
        return tasks;
    }

    @Override
    public @Nullable Task getTaskById(Long id) {
        return null;
    }

    @Override
    public void saveEntity(Task entity) {

    }

    @Override
    public void updateEntity(Task entity) {

    }

    @Override
    public void deleteEntity(Long id) {

    }
}
