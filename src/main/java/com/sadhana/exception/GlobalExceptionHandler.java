package com.sadhana.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors().forEach(error ->
	                errors.put(error.getField(), error.getDefaultMessage())
	        );
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	    }

	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
	        Map<String, String> error = new HashMap<>();
	        error.put("error", "Failed to process analysis request");
	        error.put("message", ex.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	    }
	}

