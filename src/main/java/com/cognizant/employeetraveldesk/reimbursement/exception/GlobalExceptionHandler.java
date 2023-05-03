package com.cognizant.employeetraveldesk.reimbursement.exception;

import java.util.Date;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

	// handle API not found exception
	@ExceptionHandler(APIException.class)
	public ResponseEntity<?> handleAPIException(APIException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// handle Resource not found exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourseNotFoundException(ResourceNotFoundException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// handle Duplicate Resource exception
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<?> handleDuplicateResourseFoundException(DuplicateResourceException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.TOO_MANY_REQUESTS);
	}

	// handle Global Exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// handle Validation Exceptions

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> customValidationException(MethodArgumentNotValidException exception) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Error",
				exception.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// To handle JDBC Exceptions

	@ExceptionHandler(GenericJDBCException.class)
	public ResponseEntity<String> handleGenericJDBCException(GenericJDBCException ex) {
		Throwable rootCause = ex.getSQLException();
		String errorMessage = rootCause.getMessage();
		return new ResponseEntity<>("Error: " + errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// To handle wrong API Calls

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
		
		ErrorDetails errorDetails=new ErrorDetails(new Date(),"Invalid request method.", "Supported methods are: " + exception.getSupportedHttpMethods());
		return  new ResponseEntity<>(errorDetails,HttpStatus.METHOD_NOT_ALLOWED);
	}
}
