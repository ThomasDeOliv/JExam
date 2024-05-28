package com.thomasdeoliv.itemsmanager;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.creation.Seeder;

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

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}