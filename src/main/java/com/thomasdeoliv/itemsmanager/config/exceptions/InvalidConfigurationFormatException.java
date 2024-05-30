package com.thomasdeoliv.itemsmanager.config.exceptions;

import java.io.IOException;

/**
 * Custom exception, extending the IOException class, that is thrown when the configuration file format is invalid.
 */
public class InvalidConfigurationFormatException extends IOException {

	/**
	 * Constructs a new InvalidConfigurationFormatException with a default error message.
	 */
	public InvalidConfigurationFormatException() {
		super("Invalid configuration format");
	}

	/**
	 * Constructs a new InvalidConfigurationFormatException with a default error message and the specified cause.
	 *
	 * @param cause the cause of the exception.
	 */
	public InvalidConfigurationFormatException(Throwable cause) {
		super("Invalid configuration format", cause);
	}
}