package com.thomasdeoliv.itemsmanager.config.exceptions;

import java.io.IOException;

public class InvalidConfigurationFormatException extends IOException {
    
    public InvalidConfigurationFormatException() {
        super("Invalid configuration format");
    }

    public InvalidConfigurationFormatException(Throwable cause) {
        super("Invalid configuration format", cause);
    }
}
