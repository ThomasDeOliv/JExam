package com.thomasdeoliv.itemsmanager.database.daos.exceptions;

import java.sql.SQLException;

public class QueryFailedException extends SQLException {
	public QueryFailedException(QueryType queryType, String entityName, Throwable throwable) {
		super(String.format("Error while doing operation %s on entity %s", queryType.toString(), entityName), throwable);
	}
}
