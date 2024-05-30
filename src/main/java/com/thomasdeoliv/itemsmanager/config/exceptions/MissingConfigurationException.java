package com.thomasdeoliv.itemsmanager.config.exceptions;

import java.io.IOException;

/**
 * Custom exception, extending the IOException class, that is thrown when the configuration file is missing.
 */
public class MissingConfigurationException extends IOException {

	/**
	 * Constructs a new MissingConfigurationException with a default error message.
	 */
	public MissingConfigurationException() {
		super("Missing configuration file.");
	}
}
