package com.practice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class MyExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleException(Exception e){
		ErrorResponse err = new ErrorResponse("Entity not found", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(Exception e){
		ErrorResponse err = new ErrorResponse("Invalid Argument", e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneralException(Exception e){
		ErrorResponse err = new ErrorResponse("Internal server error", e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		
	}
	
	public static class ErrorResponse {
		
		private String error;
		private String message;
		public ErrorResponse(String error, String message) {
			
			this.error = error;
			this.message = message;
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		
		
	}

}
