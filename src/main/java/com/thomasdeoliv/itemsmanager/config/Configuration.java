package com.thomasdeoliv.itemsmanager.config;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class Configuration {
	/**
	 * The current section related to the database.
	 */
	private Section databaseSection;

	/**
	 * Constructor
	 */
	public Configuration() {
		try {
			// Load file
			File configFile = new File("src/main/resources/config.ini");
			// Ensure file exists
			if (configFile.exists()) {
				// Configuration
				Ini ini = new Ini(configFile);
				// Retrieve section
				this.databaseSection = ini.get("database");
			} else {
				// Throw exception if the file is missing.
				throw new IOException("Missing configuration file...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDatabaseMainUrl() {
		return this.databaseSection.get("main_url");
	}

	public String getDatabaseUrl() {
		return this.databaseSection.get("url");
	}

	public String getDatabaseUsername() {
		return this.databaseSection.get("username");
	}

	public String getDatabaseUserPassword() {
		return this.databaseSection.get("password");
	}

	public String getDatabaseName() {
		return this.databaseSection.get("database_name");
	}

	public String getSchemaName() {
		return this.databaseSection.get("schema_name");
	}
}
