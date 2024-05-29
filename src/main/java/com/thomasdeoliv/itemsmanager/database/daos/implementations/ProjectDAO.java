package com.thomasdeoliv.itemsmanager.database.daos.implementations;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.daos.IProjectDAO;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryFailedException;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryType;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO implements IProjectDAO {

	private String url;
	private String userName;
	private String userPassword;

	/**
	 * Task DAO constructor.
	 */
	public ProjectDAO(Configuration configuration) {
		try {
			this.url = configuration.getDatabaseUrl();
			this.userName = configuration.getDatabaseUsername();
			this.userPassword = configuration.getDatabaseUserPassword();
		} catch (IOException e) {
			ErrorDialog.handleException(e);
		}
	}

	@Override
	public List<Project> getAllProjects() throws QueryFailedException {
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
						    WHERE items_manager_schema.item.item_related_item_id IS NULL
							ORDER BY items_manager_schema.item.item_start_at DESC;
						""");
				// Loop on each provided results
				while (rs.next()) {
					// Instantiate a project object
					Project project = new Project();

					// Fetch endAt column value
					@Nullable
					Timestamp endAt = rs.getTimestamp("item_end_at");

					// Fill all fields
					project.setId(rs.getLong("item_id"));
					project.setName(rs.getString("item_name"));
					project.setDescription(rs.getString("item_description"));
					project.setStartedAt(rs.getTimestamp("item_start_at"));
					project.setEndedAt(endAt);

					// Add project to list
					projects.add(project);
				}
				// Return statement
				return projects;
			}
		} catch (SQLException ex) {
			// Return statement
			throw new QueryFailedException(QueryType.SELECT, Project.class.getSimpleName(), ex);
		}
	}

	@Override
	public @Nullable Project getProjectById(Long id) throws QueryFailedException {
		// Open a connection to the database.
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
			// Query
			String query = """
					SELECT
						item.*,
					    (SELECT COUNT(*)
					     FROM items_manager_schema.item AS tasks
					     WHERE tasks.item_related_item_id IS NOT NULL
					  						AND tasks.item_id = item.item_id) AS related_tasks
					FROM items_manager_schema.item
					WHERE items_manager_schema.item.item_related_item_id IS NULL
					  AND items_manager_schema.item.item_id = ?
					ORDER BY items_manager_schema.item.item_start_at DESC;
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
					project.setStartedAt(rs.getTimestamp("item_start_at"));
					project.setEndedAt(endAt);
					project.setId(rs.getLong("related_tasks"));
				}
				// Return
				return project;
			}
		} catch (SQLException ex) {
			// Return statement
			throw new QueryFailedException(QueryType.SELECT, Project.class.getSimpleName(), ex);
		}
	}

	@Override
	public void saveEntity(Project entity) throws QueryFailedException {
		// Validate datas
		if (entity.getEndedAt() != null || entity.getRelatedItemId() != null) {
			throw new IllegalArgumentException("Invalid datas provided.");
		}
		// Open a connection to the database.
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
			// Query
			String query = """
					INSERT INTO items_manager_schema.item(item_name, item_description)
					VALUES (?, ?);
					""";
			// Create a statement
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				// Set parameters
				statement.setString(1, entity.getName());
				statement.setString(2, entity.getDescription());
				// Execute query
				ResultSet rs = statement.executeQuery();
				// Ensure query success
				if (!rs.next()) {
					throw new SQLException("Cannot save this project.");
				}
			}
		} catch (SQLException ex) {
			// Return statement
			throw new QueryFailedException(QueryType.INSERT, Project.class.getSimpleName(), ex);
		}
	}

	@Override
	public void updateEntity(Project entity) throws QueryFailedException {
		// Validate datas
		if (entity.getRelatedItemId() != null) {
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
						item_end_at = ?
					WHERE items_manager_schema.item.item_related_item_id IS NULL
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
				statement.setLong(4, entity.getId());
				// Execute query
				ResultSet rs = statement.executeQuery();
				// Ensure query success
				if (!rs.next()) {
					throw new SQLException("Cannot update this project.");
				}
			}
		} catch (SQLException ex) {
			// Return statement
			throw new QueryFailedException(QueryType.UPDATE, Project.class.getSimpleName(), ex);
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
			}
		} catch (SQLException ex) {
			// Return statement
			throw new QueryFailedException(QueryType.DELETE, Project.class.getSimpleName(), ex);
		}
	}
}
