package com.springbootapp.exception;

import lombok.Data;

@Data
public class ExceptionResponse {

	private String error;

	public ExceptionResponse(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
}
