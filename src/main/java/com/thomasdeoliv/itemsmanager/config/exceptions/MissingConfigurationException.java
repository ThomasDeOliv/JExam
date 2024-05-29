package com.thomasdeoliv.itemsmanager.config.exceptions;

import java.io.IOException;

public class MissingConfigurationException extends IOException {
    public MissingConfigurationException() {
        super("Missing configuration file.");
    }
}
