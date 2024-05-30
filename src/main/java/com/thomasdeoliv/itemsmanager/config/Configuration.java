package com.thomasdeoliv.itemsmanager.config;

import com.thomasdeoliv.itemsmanager.config.exceptions.InvalidConfigurationFormatException;
import com.thomasdeoliv.itemsmanager.config.exceptions.MissingConfigurationException;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * The Configuration class loads, validate and manages configuration settings from an INI file.
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class Configuration {

	/**
	 * The section of the configuration file related to database settings.
	 */
	private final Section databaseSection;

	/**
	 * The section of the configuration file related to socket settings.
	 */
	private final Section socketsSection;

	/**
	 * Constructor that initializes the configuration by loading settings from the INI file.
	 *
	 * @throws MissingConfigurationException       if the configuration file is missing.
	 * @throws InvalidConfigurationFormatException if the configuration file cannot be read or is incorrectly formatted.
	 */
	public Configuration() throws MissingConfigurationException, InvalidConfigurationFormatException {
        
		// Get config file path
		String configPath = Objects.requireNonNull(this.getClass().getResource("/config.ini")).getPath();

		// Assert path is not null
		if (configPath == null) {
			throw new MissingConfigurationException();
		}

		// Load file
		File configFile = new File(configPath);

		// Ensure file exists
		if (!configFile.exists()) {
			throw new MissingConfigurationException();
		}

		// Configuration
		Ini ini;
		try {
			ini = new Ini(configFile);
		} catch (IOException e) {
			throw new InvalidConfigurationFormatException(e);
		}

		// Retrieve database section
		this.databaseSection = ini.get("database");

		// Retrieve sockets section
		this.socketsSection = ini.get("socket");

		// Validate configuration
		if (this.databaseSection.get("url") == null
				|| this.databaseSection.get("username") == null
				|| this.socketsSection.get("host") == null
				|| this.socketsSection.get("port") == null
				|| this.databaseSection.get("password") == null
		) {
			throw new InvalidConfigurationFormatException();
		}

		// Ensure provided port is an integer
		try {
			Integer.parseInt(this.socketsSection.get("port"));
		} catch (NumberFormatException e) {
			throw new InvalidConfigurationFormatException();
		}
	}

	/**
	 * Gets the database JDBC URL from the configuration.
	 *
	 * @return the database JDBC URL.
	 */
	public String getDatabaseUrl() {
		return this.databaseSection.get("url");
	}

	/**
	 * Gets the database username from the configuration.
	 *
	 * @return the database username.
	 */
	public String getDatabaseUsername() {
		return this.databaseSection.get("username");
	}

	/**
	 * Gets the database user password from the configuration.
	 *
	 * @return the database user password.
	 */
	public String getDatabaseUserPassword() {
		return this.databaseSection.get("password");
	}

	/**
	 * Gets the server host from the configuration.
	 *
	 * @return the server host.
	 */
	public String getServerHost() {
		return this.socketsSection.get("host");
	}

	/**
	 * Gets the server port from the configuration.
	 *
	 * @return the server port.
	 */
	public int getServerPort() {
		String portString = this.socketsSection.get("port");
		return Integer.parseInt(portString);
	}
}
