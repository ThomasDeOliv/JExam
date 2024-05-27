package com.thomasdeoliv.itemsmanager.datas.database.creation;

import com.thomasdeoliv.itemsmanager.datas.config.Configuration;

import java.sql.*;

public class Seeder {
    private final String mainUrl;
    private final String url;
    private final String userName;
    private final String userPassword;
    private final String databaseName;

    public Seeder(Configuration configuration) {
        this.mainUrl = configuration.getDatabaseMainUrl();
        this.url = configuration.getDatabaseUrl();
        this.userName = configuration.getDatabaseUsername();
        this.userPassword = configuration.getDatabaseUserPassword();
        this.databaseName = configuration.getDatabaseName();
    }

    public void EnsureDatabaseCreated() {
        // Connect to the master database
        try (Connection connection = DriverManager.getConnection(this.mainUrl, this.userName, this.userPassword);
             Statement statement = connection.createStatement()) {

            // Try to create the tasks_manager if not exists
            ResultSet resultSet = statement.executeQuery(String.format("SELECT 1 FROM pg_database WHERE datname = '%s';", this.databaseName));

            // If the previous query has no result
            if (!resultSet.next()) {
                statement.execute(String.format("CREATE DATABASE %s", this.databaseName));
            }

        } catch (SQLException e) {
            // Throw exception if no connection can be established
            throw new RuntimeException(e);
        }
    }

    public void EnsureSchemaAndTablesCreated() {
        // Connect to the master database
        try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword);
             Statement statement = connection.createStatement()) {

            // Create schema
            statement.execute("CREATE SCHEMA IF NOT EXISTS tasks_manager_schema");

            // Set search path
            statement.execute("SET search_path TO tasks_manager_schema");

            // Create table in previous schema
            statement.execute("""
                CREATE TABLE IF NOT EXISTS item(
                   item_id BIGSERIAL NOT NULL,
                   item_name VARCHAR(256) NOT NULL,
                   item_description TEXT NULL,
                   item_is_active BOOLEAN NOT NULL DEFAULT true,
                   item_start_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                   item_end_at TIMESTAMPTZ NULL,
                   item_related_item_id BIGINT NULL DEFAULT NULL,
                   CONSTRAINT PK_item__item_id PRIMARY KEY (item_id),
                   CONSTRAINT FK_item_item__item_related_item_id FOREIGN KEY (item_related_item_id) REFERENCES item(item_id)
                )
            """);

            // Close statement
            statement.close();

            // Close connection
            connection.close();
        } catch (SQLException e) {
            // Throw exception if no connection can be established
            throw new RuntimeException(e);
        }
    }
}
