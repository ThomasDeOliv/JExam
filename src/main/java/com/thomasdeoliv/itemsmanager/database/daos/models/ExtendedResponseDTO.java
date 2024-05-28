package com.thomasdeoliv.itemsmanager.database.daos.models;

import org.jetbrains.annotations.Nullable;

public class ExtendedResponseDTO<T> extends ResponseDTO {

	@Nullable
	private final T datas;

	public ExtendedResponseDTO(boolean success, String errorMessage, @Nullable T datas) {
		super(success, errorMessage);
		this.datas = datas;
	}

	@Nullable
	public T getData() {
		return datas;
	}
}
