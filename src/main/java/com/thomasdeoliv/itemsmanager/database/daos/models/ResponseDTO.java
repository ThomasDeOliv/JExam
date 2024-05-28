package com.thomasdeoliv.itemsmanager.database.daos.models;

public class ResponseDTO {

	private final boolean success;
	private final String errorMessage;

	public ResponseDTO(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
