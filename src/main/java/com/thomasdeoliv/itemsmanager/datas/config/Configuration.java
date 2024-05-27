package com.thomasdeoliv.itemsmanager.datas.config;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import java.io.File;
import java.io.IOException;

public class Configuration {

    /**
     * The current section related to the database.
     */
    private final Section databaseSection;

    /**
     * Constructor
     * @throws IOException if the file is missing.
     */
    public Configuration() throws IOException {
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
    }

    public String getDatabaseMainUrl() {
        return this.databaseSection.get("main_url");
    };

    public String getDatabaseUrl() {
        return this.databaseSection.get("url");
    };

    public String getDatabaseUsername() {
        return this.databaseSection.get("username");
    };

    public String getDatabaseUserPassword() {
        return this.databaseSection.get("password");
    };

    public String getDatabaseName() {
        return this.databaseSection.get("database_name");
    };
}
