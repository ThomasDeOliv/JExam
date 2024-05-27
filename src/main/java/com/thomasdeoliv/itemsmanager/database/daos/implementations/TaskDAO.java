package com.thomasdeoliv.itemsmanager.database.daos.implementations;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.daos.ITaskDAO;
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
	public List<Task> getAllProjects(Long projectId) throws SQLException {
		// Instantiate a list of projects.
		List<Task> tasks = new ArrayList<>();
		// Open a connection to the database.
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {
			// Query
			String query = """
						SELECT *
						FROM items_manager_schema.item
						WHERE items_manager_schema.item.item_related_item_id = ?
					""";
			// Create a statement
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				// Define params for query
				statement.setLong(1, projectId);
				// Fetch results
				ResultSet rs = statement.executeQuery();
				// Loop on each provided results
				while (rs.next()) {
					// Get columns values
					long id = rs.getLong("item_id");
					String title = rs.getString("item_name");
					String description = rs.getString("item_description");
					boolean isActive = rs.getBoolean("item_is_active");
					Timestamp startAt = rs.getTimestamp("item_start_at");
					@Nullable Timestamp endAt = rs.getTimestamp("item_end_at");
					long relatedItemId = rs.getLong("item_related_item_id");
					// Instantiate a project object
					Task task = new Task();
					// Fill all fields
					task.setId(id);
					task.setName(title);
					task.setDescription(description);
					task.setIsActive(isActive);
					task.setStartedAt(startAt.toLocalDateTime());
					task.setEndedAt(endAt != null ? endAt.toLocalDateTime() : null);
					task.setRelatedItemId(relatedItemId);

					// Add project to list
					tasks.add(task);
				}
			}
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
