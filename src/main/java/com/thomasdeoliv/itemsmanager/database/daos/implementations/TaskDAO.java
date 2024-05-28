package com.thomasdeoliv.itemsmanager.database.daos.implementations;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.daos.ITaskDAO;
import com.thomasdeoliv.itemsmanager.database.daos.models.ExtendedResponseDTO;
import com.thomasdeoliv.itemsmanager.database.daos.models.ResponseDTO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
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
	public ExtendedResponseDTO<List<Task>> getAllProjects(Long projectId) {
		// Instantiate a list of projects.
		List<Task> tasks = new ArrayList<>();
		// Open a connection to the database.
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
			// Query
			String query = """
						SELECT *
						FROM items_manager_schema.item
						WHERE items_manager_schema.item.item_related_item_id IS NOT NULL
							AND items_manager_schema.item.item_related_item_id = ?
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
					@Nullable Timestamp endAt = rs.getTimestamp("item_end_at");

					// Fill all fields
					task.setId(rs.getLong("item_id"));
					task.setName(rs.getString("item_name"));
					task.setDescription(rs.getString("item_description"));
					task.setIsActive(rs.getBoolean("item_is_active"));
					task.setStartedAt(rs.getTimestamp("item_start_at").toLocalDateTime());
					task.setEndedAt(endAt != null ? endAt.toLocalDateTime() : null);
					task.setRelatedItemId(rs.getLong("item_related_item_id"));

					// Add project to list
					tasks.add(task);
				}

				// Return statement
				return new ExtendedResponseDTO<>(true, "", tasks);

			} catch (SQLException ex) {
				// Clear list
				tasks.clear();
				// Return statement
				return new ExtendedResponseDTO<>(false, ex.getMessage(), null);
			}
		} catch (SQLException ex) {
			// Clear list
			tasks.clear();
			// Return statement
			return new ExtendedResponseDTO<>(false, ex.getMessage(), null);
		}
	}

	@Override
	public ExtendedResponseDTO<@Nullable Task> getTaskById(Long id) {
		// Open a connection to the database.
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
			// Query
			String query = """
					SELECT *
					FROM items_manager_schema.item
					WHERE items_manager_schema.item.item_related_item_id IS NOT NULL
					  AND items_manager_schema.item.item_id = ?
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
					task.setIsActive(rs.getBoolean("item_is_active"));
					task.setStartedAt(rs.getTimestamp("item_start_at").toLocalDateTime());
					task.setEndedAt(endAt != null ? endAt.toLocalDateTime() : null);
					task.setRelatedItemId(rs.getLong("item_related_item_id"));
				}
				// Return
				return new ExtendedResponseDTO<>(true, "", task);
			} catch (SQLException ex) {
				// Return statement
				return new ExtendedResponseDTO<>(false, ex.getMessage(), null);
			}
		} catch (SQLException ex) {
			// Return statement
			return new ExtendedResponseDTO<>(false, ex.getMessage(), null);
		}
	}

	@Override
	public ResponseDTO saveEntity(Task entity) {
		// Validate datas
		if (entity.getEndedAt() != null || entity.getRelatedItemId() == null) {
			return new ResponseDTO(false, "Invalid datas provided.");
		}
		// Open a connection to the database.
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
			// Query
			String query = """
					INSERT INTO items_manager_schema.item(item_name, item_description, item_is_active, item_start_at, item_end_at, item_related_item_id)
					VALUES (?, ?, ?, ?, ?, ?);
					""";
			// Create a statement
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				// Set parameters
				statement.setString(1, entity.getName());
				statement.setString(2, entity.getDescription());
				statement.setBoolean(3, entity.getIsActive());
				statement.setTimestamp(4, Timestamp.valueOf(entity.getStartedAt()));
				statement.setNull(5, Types.TIMESTAMP);
				statement.setLong(6, entity.getRelatedItemId());
				// Execute query
				ResultSet rs = statement.executeQuery();
				// Ensure query success
				if (!rs.next()) {
					throw new SQLException("Cannot save this task.");
				}
				// Return statement
				return new ResponseDTO(true, "");
			} catch (SQLException ex) {
				// Return statement
				return new ResponseDTO(false, ex.getMessage());
			}
		} catch (SQLException ex) {
			// Return statement
			return new ResponseDTO(false, ex.getMessage());
		}
	}

	@Override
	public ResponseDTO updateEntity(Task entity) {
		// Validate datas
		if (entity.getRelatedItemId() == null) {
			return new ResponseDTO(false, "Invalid datas provided.");
		}
		// Open a connection to the database.
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
			// Query
			String query = """
					UPDATE items_manager_schema.item
					SET
						item_name = ?,
						item_description = ?,
						item_is_active = ?,
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
				statement.setBoolean(3, entity.getIsActive());
				// Set conditionally parameters
				if (entity.getEndedAt() != null) {
					statement.setTimestamp(4, Timestamp.valueOf(entity.getEndedAt()));
				} else {
					statement.setNull(4, Types.TIMESTAMP);
				}
				// Set parameters
				statement.setLong(5, entity.getRelatedItemId());
				statement.setLong(6, entity.getId());
				// Execute query
				ResultSet rs = statement.executeQuery();
				// Ensure query success
				if (!rs.next()) {
					throw new SQLException("Cannot delete this task.");
				}
				// Return statement
				return new ResponseDTO(true, "");
			} catch (SQLException ex) {
				// Return statement
				return new ResponseDTO(false, ex.getMessage());
			}
		} catch (SQLException ex) {
			// Return statement
			return new ResponseDTO(false, ex.getMessage());
		}
	}

	@Override
	public ResponseDTO deleteEntity(Long id) {
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
				// Return statement
				return new ResponseDTO(true, "");
			} catch (SQLException ex) {
				// Return statement
				return new ResponseDTO(false, ex.getMessage());
			}
		} catch (SQLException ex) {
			// Return statement
			return new ResponseDTO(false, ex.getMessage());
		}
	}
}
