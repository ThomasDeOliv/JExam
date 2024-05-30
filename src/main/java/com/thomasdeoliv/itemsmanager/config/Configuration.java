package com.thomasdeoliv.itemsmanager.config;

import com.thomasdeoliv.itemsmanager.config.exceptions.InvalidConfigurationFormatException;
import com.thomasdeoliv.itemsmanager.config.exceptions.MissingConfigurationException;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class Configuration {
    /**
     * The current section related to the database.
     */
    private final Section databaseSection;

    /**
     * The current section related to the sockets.
     */
    private final Section socketsSection;

    /**
     * Constructor
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
    }

    public String getDatabaseUrl() throws InvalidConfigurationFormatException {
        if (this.databaseSection.get("url") == null) {
            throw new InvalidConfigurationFormatException();
        }
        return this.databaseSection.get("url");
    }

    public String getDatabaseUsername() throws InvalidConfigurationFormatException {
        if (this.databaseSection.get("username") == null) {
            throw new InvalidConfigurationFormatException();
        }
        return this.databaseSection.get("username");
    }

    public String getDatabaseUserPassword() throws InvalidConfigurationFormatException {
        if (this.databaseSection.get("password") == null) {
            throw new InvalidConfigurationFormatException();
        }
        return this.databaseSection.get("password");
    }

    public String getServerHost() throws InvalidConfigurationFormatException {
        if (this.socketsSection.get("host") == null) {
            throw new InvalidConfigurationFormatException();
        }
        return this.socketsSection.get("host");
    }

    public int getServerPort() throws InvalidConfigurationFormatException {
        if (this.socketsSection.get("port") == null) {
            throw new InvalidConfigurationFormatException();
        }
        String portString = this.socketsSection.get("port");
        return Integer.parseInt(portString);
    }
}
