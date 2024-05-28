package com.thomasdeoliv.itemsmanager.database.creation;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.creation.exceptions.InitDbException;
import com.thomasdeoliv.itemsmanager.database.creation.exceptions.InitSchemaException;

import java.sql.*;

public class Seeder {

	private final String mainUrl;
	private final String url;
	private final String userName;
	private final String userPassword;
	private final String databaseName;
	private final String schemaName;

	public Seeder(Configuration configuration) {
		this.mainUrl = configuration.getDatabaseMainUrl();
		this.url = configuration.getDatabaseUrl();
		this.userName = configuration.getDatabaseUsername();
		this.userPassword = configuration.getDatabaseUserPassword();
		this.databaseName = configuration.getDatabaseName();
		this.schemaName = configuration.getSchemaName();
	}

	/**
	 * Ensure the creation of the database with POSTGRES.
	 */
	public void EnsureDatabaseCreated() throws InitDbException {
		// Connect to the master database
		try (Connection connection = DriverManager.getConnection(this.mainUrl, this.userName, this.userPassword)) {
			// Create a statement
			try (PreparedStatement firstStatement = connection.prepareStatement("SELECT 1 FROM pg_catalog.pg_database WHERE datname = ?")) {
				// Set parameters
				firstStatement.setString(1, this.databaseName);
				// Execute query
				ResultSet firstResultSet = firstStatement.executeQuery();
				// If the previous query has no result
				if (firstResultSet.next()) {
					throw new SQLDataException("Database already exists.");
				}
				// Create a statement
				try (Statement secondStatement = connection.createStatement()) {
					// Execute query
					secondStatement.executeUpdate("CREATE DATABASE " + this.databaseName); // <- Have to use concat here, cannot prepare a statement...
				} catch (SQLException ex) {
					// Throw a custom exception
					throw new InitDbException("An error occurred while trying to create the database.", ex);
				}
			} catch (SQLException ex) {
				// Throw a custom exception
				throw new InitDbException("The database already exists.", ex);
			}
		} catch (SQLException ex) {
			// Throw a custom exception
			throw new InitDbException("Cannot connect to the server. Please check your credentials.", ex);
		}
	}

	/**
	 * Ensure the creation of the schema and tables with POSTGRES.
	 */
	public void EnsureSchemaAndTablesCreated() throws SQLException {
		// Connect to the master database
		try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.userPassword)) {

			// Create a first statement for creating schema
			try (Statement firstStatement = connection.createStatement()) {
				// Execute the statement
				firstStatement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + this.schemaName);
			} catch (SQLException ex) {
				// Throw a custom exception
				throw new InitSchemaException("Cannot create database schema.", ex);
			}

			// Create a second statement to set the search path
			try (Statement secondStatement = connection.createStatement()) {
				// Execute the statement
				secondStatement.executeUpdate("SET search_path TO " + this.schemaName);
			} catch (SQLException ex) {
				// Throw a custom exception
				throw new InitSchemaException("Impossible to set the search path.", ex);
			}

			try (Statement thirdStatement = connection.createStatement()) {
				// Create a third statement for creating the table
				String createTableQuery = """
						    CREATE TABLE IF NOT EXISTS item (
						        item_id BIGSERIAL NOT NULL,
						        item_name VARCHAR(256) NOT NULL,
						        item_description TEXT NULL,
						        item_is_active BOOLEAN NOT NULL DEFAULT true,
						        item_start_at TIMESTAMPTZ NOT NULL DEFAULT now(),
						        item_end_at TIMESTAMPTZ NULL DEFAULT NULL,
						        item_related_item_id BIGINT NULL DEFAULT NULL,
						        CONSTRAINT PK_item__item_id PRIMARY KEY (item_id),
						        CONSTRAINT FK_item_item__item_related_item_id FOREIGN KEY (item_related_item_id) REFERENCES item(item_id)
						    )
						""";
				// Execute the statement
				thirdStatement.executeUpdate(createTableQuery);
			} catch (SQLException ex) {
				// Throw a custom exception
				throw new InitSchemaException("Can't create database tables.", ex);
			}
		} catch (SQLException ex) {
			// Throw a custom exception
			throw new InitDbException("Cannot connect to the server. Please check your credentials.", ex);
		}
	}
}
