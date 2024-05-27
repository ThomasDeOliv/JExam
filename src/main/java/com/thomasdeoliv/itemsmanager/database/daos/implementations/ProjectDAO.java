package com.thomasdeoliv.itemsmanager.database.daos.implementations;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.daos.IProjectDAO;
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
	public List<Project> getAllProjects() throws SQLException {
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
					// Get columns values
					long id = rs.getLong("item_id");
					String title = rs.getString("item_name");
					String description = rs.getString("item_description");
					boolean isActive = rs.getBoolean("item_is_active");
					Timestamp startAt = rs.getTimestamp("item_start_at");
					@Nullable Timestamp endAt = rs.getTimestamp("item_end_at");
					// Instantiate a project object
					Project project = new Project();
					// Fill all fields
					project.setId(id);
					project.setName(title);
					project.setDescription(description);
					project.setIsActive(isActive);
					project.setStartedAt(startAt.toLocalDateTime());
					project.setEndedAt(endAt != null ? endAt.toLocalDateTime() : null);
					// Add project to list
					projects.add(project);
				}
			}
		}
		// Return statement
		return projects;
	}

	@Override
	public @Nullable Project getProjectById(Long id) {
		return null;
	}

	@Override
	public void saveEntity(Project entity) {

	}

	@Override
	public void updateEntity(Project entity) {

	}

	@Override
	public void deleteEntity(Long id) {

	}
}
