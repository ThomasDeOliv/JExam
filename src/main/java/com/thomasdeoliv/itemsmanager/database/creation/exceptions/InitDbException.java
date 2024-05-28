package com.thomasdeoliv.itemsmanager.database.creation.exceptions;

import java.sql.SQLException;

public class InitDbException extends SQLException {
	public InitDbException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
