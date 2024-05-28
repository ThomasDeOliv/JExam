package com.thomasdeoliv.itemsmanager.database.creation.exceptions;

import java.sql.SQLException;

public class InitSchemaException extends SQLException {
	public InitSchemaException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}