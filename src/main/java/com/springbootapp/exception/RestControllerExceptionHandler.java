package com.springbootapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> response(BadRequestException exception) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}
}
