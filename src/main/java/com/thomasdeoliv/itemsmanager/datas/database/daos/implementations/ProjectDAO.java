package com.thomasdeoliv.itemsmanager.datas.database.daos.implementations;

import com.thomasdeoliv.itemsmanager.datas.config.Configuration;

import com.thomasdeoliv.itemsmanager.datas.database.daos.IProjectDAO;
import com.thomasdeoliv.itemsmanager.datas.database.entities.implementations.Project;
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
    public List<Project> getAllProjects() {
        // Instantiate a list of projects.
        List<Project> projects = new ArrayList<Project>();

        // Create query to get all projects (no related ID)
        String query = """
            SELECT *
            FROM tasks_manager_schema.item
            WHERE tasks_manager_schema.item.item_related_item_id IS NULL;
        """;

        // Open a connection to the database.
        try (Connection conn = DriverManager.getConnection(this.url, this.userName, this.userPassword);
             PreparedStatement stmt = conn.prepareStatement(query);
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

            // Close connection
            conn.close();
        } catch (SQLException e) {
            // Print stack trace
            e.printStackTrace();
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
