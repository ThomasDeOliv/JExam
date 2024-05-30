package com.thomasdeoliv.itemsmanager.database.daos.implementations;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.daos.ITaskDAO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;
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
	public List<Task> getAllTasks(Long projectId) {
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
					task.setStartedAt(rs.getTimestamp("item_start_at"));
					task.setEndedAt(endAt);
					task.setRelatedItemId(rs.getLong("item_related_item_id"));
					// Add project to list
					tasks.add(task);
				}
			}
		} catch (SQLException ex) {
			// Show dialog
			ErrorDialog.handleException(ex);
		}
		// Return statement
		return tasks;
	}

	@Override
	@Nullable
	public Task getTaskById(Long id) {
		// Declare Task
		Task task = null;
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
				// Loop on each provided results
				while (rs.next()) {
					// Instantiate a task object
					task = new Task();
					// Fetch endAt column value
					@Nullable
					Timestamp endAt = rs.getTimestamp("item_end_at");
					// Fill all fields
					task.setId(rs.getLong("item_id"));
					task.setName(rs.getString("item_name"));
					task.setDescription(rs.getString("item_description"));
					task.setStartedAt(rs.getTimestamp("item_start_at"));
					task.setEndedAt(endAt);
					task.setRelatedItemId(rs.getLong("item_related_item_id"));
				}
			}
		} catch (SQLException ex) {
			// Show dialog
			ErrorDialog.handleException(ex);
		}
		// Return statement
		return task;
	}

	@Override
	public void saveEntity(Task entity) {
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
			// Show dialog
			ErrorDialog.handleException(ex);
		}
	}

	@Override
	public void updateEntity(Task entity) {
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
					statement.setTimestamp(3, entity.getEndedAt());
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
			// Show dialog
			ErrorDialog.handleException(ex);
		}
	}

	@Override
	public void deleteEntity(Long id) {
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
			// Show dialog
			ErrorDialog.handleException(ex);
		}
	}
}
