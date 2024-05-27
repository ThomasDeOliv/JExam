package com.thomasdeoliv.itemsmanager;

import com.thomasdeoliv.itemsmanager.datas.config.Configuration;
import com.thomasdeoliv.itemsmanager.datas.database.creation.Seeder;
import com.thomasdeoliv.itemsmanager.datas.database.daos.implementations.ProjectDAO;
import com.thomasdeoliv.itemsmanager.datas.database.entities.implementations.Project;

import java.util.List;

/**
 * The class which contains the Main function.
 */
public class Launcher {

    /**
     * The Main entry point of the application.
     *
     * @param args the command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        try {
            // Fetch configuration
            Configuration config = new Configuration();
            // Create database if requested by the user
            if (args.length != 0 && args[0].equals("--init-db")) {
                // Deploy database
                Seeder seeder = new Seeder(config);
                seeder.EnsureDatabaseCreated();
                // Build schema and tables
                seeder.EnsureSchemaAndTablesCreated();
            }
            //
            ProjectDAO projectDAO = new ProjectDAO(config);
            List<Project> projects = projectDAO.getAllEntities();
            for (Project project : projects) {
                System.out.println(project.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}