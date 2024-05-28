package com.thomasdeoliv.itemsmanager.database.daos.implementations;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.daos.IProjectDAO;
import com.thomasdeoliv.itemsmanager.database.daos.models.ExtendedResponseDTO;
import com.thomasdeoliv.itemsmanager.database.daos.models.ResponseDTO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO implements IProjectDAO {

	private final String url;
	private final String userName;
	private final String userPassword;

	/**
	 * Task DAO constructor.
	 */
	public ProjectDAO(Configuration configuration) {
		this.url = configuration.getDatabaseUrl();
		this.userName = configuration.getDatabaseUsername();
		this.userPassword = configuration.getDatabaseUserPassword();
	}

	@Override
	public ExtendedResponseDTO<List<Project>> getAllProjects() {
		// Instantiate a list of projects.
		List<Project> projects = new ArrayList<>();
		// Open a connection to the database.
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
			// Create a statement
			try (Statement statement = connection.createStatement()) {
				// Fetch results
				ResultSet rs = statement.executeQuery("""
						    SELECT *
						    FROM items_manager_schema.item
						    WHERE items_manager_schema.item.item_related_item_id IS NULL;
						""");
				// Loop on each provided results
				while (rs.next()) {
					// Instantiate a project object
					Project project = new Project();

					// Fetch endAt column value
					@Nullable Timestamp endAt = rs.getTimestamp("item_end_at");

					// Fill all fields
					project.setId(rs.getLong("item_id"));
					project.setName(rs.getString("item_name"));
					project.setDescription(rs.getString("item_description"));
					project.setIsActive(rs.getBoolean("item_is_active"));
					project.setStartedAt(rs.getTimestamp("item_start_at").toLocalDateTime());
					project.setEndedAt(endAt != null ? endAt.toLocalDateTime() : null);

					// Add project to list
					projects.add(project);
				}
				// Return statement
				return new ExtendedResponseDTO<>(true, "", projects);
			} catch (SQLException ex) {
				// Clear list
				projects.clear();
				// Return statement
				return new ExtendedResponseDTO<>(false, ex.getMessage(), projects);
			}
		} catch (SQLException ex) {
			// Clear list
			projects.clear();
			// Return statement
			return new ExtendedResponseDTO<>(false, ex.getMessage(), projects);
		}
	}

	@Override
	public ExtendedResponseDTO<@Nullable Project> getProjectById(Long id) {
		// Open a connection to the database.
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
			// Query
			String query = """
					SELECT *
					FROM items_manager_schema.item
					WHERE items_manager_schema.item.item_related_item_id IS NULL
					  AND items_manager_schema.item.item_id = ?
					""";
			// Create a statement
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				// Prepare query
				statement.setLong(1, id);
				// Execute query
				ResultSet rs = statement.executeQuery();
				// Declare Project variable
				Project project = null;
				// Loop on each provided results
				while (rs.next()) {
					// Instantiate a project object
					project = new Project();

					// Fetch endAt column value
					@Nullable Timestamp endAt = rs.getTimestamp("item_end_at");

					// Fill all fields
					project.setId(rs.getLong("item_id"));
					project.setName(rs.getString("item_name"));
					project.setDescription(rs.getString("item_description"));
					project.setIsActive(rs.getBoolean("item_is_active"));
					project.setStartedAt(rs.getTimestamp("item_start_at").toLocalDateTime());
					project.setEndedAt(endAt != null ? endAt.toLocalDateTime() : null);
				}
				// Return
				return new ExtendedResponseDTO<>(true, "", project);
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
	public ResponseDTO saveEntity(Project entity) {
		// Validate datas
		if (entity.getEndedAt() != null || entity.getRelatedItemId() != null) {
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
				statement.setNull(6, Types.BIGINT);
				// Execute query
				ResultSet rs = statement.executeQuery();
				// Ensure query success
				if (!rs.next()) {
					throw new SQLException("Cannot save this project.");
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
	public ResponseDTO updateEntity(Project entity) {
		// Validate datas
		if (entity.getRelatedItemId() != null) {
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
						item_end_at = ?
					WHERE items_manager_schema.item.item_related_item_id IS NULL
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
				statement.setLong(5, entity.getId());
				// Execute query
				ResultSet rs = statement.executeQuery();
				// Ensure query success
				if (!rs.next()) {
					throw new SQLException("Cannot update this project.");
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
					WHERE items_manager_schema.item.item_related_item_id IS NULL
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
					throw new SQLException("Cannot delete this project.");
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
