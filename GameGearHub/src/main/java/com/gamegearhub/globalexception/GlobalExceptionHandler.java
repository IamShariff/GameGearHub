package com.gamegearhub.globalexception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gamegearhub.util.ErrorResponse;
import com.gamegearhub.util.ResponseHandler;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Whenever these execption occur at any time throughout the program this class
 * will handle it
 * 
 * @author mdsharif
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<Object> genericExceptionHandler(GenericException genericException) {
		final String errorMessage = genericException.getMessage();
		final String fieldName = genericException.getFieldName();
		final HttpStatus errorCode = genericException.getHttpStatusCode();
		return ResponseHandler.generateError(fieldName, errorMessage, errorCode);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErrorResponse>> methodArgsNotValidExceptionHandler(
			MethodArgumentNotValidException messege) {
		List<ErrorResponse> apiResponse = new ArrayList<>();
		messege.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String defaultMessage = error.getDefaultMessage();
			String errorCode = "Invalid " + error.getCode();

			apiResponse.add(new ErrorResponse(fieldName, errorCode, defaultMessage, HttpStatus.BAD_REQUEST));

		});

		return new ResponseEntity<List<ErrorResponse>>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	
	/**
	 * 
	 * @param messege
	 * @return new response entity with error message and 400 Bad Request status
	 * @implNote This method will run when user try to enter data in wrong format
	 */
	@ExceptionHandler(JsonMappingException.class)
	public ResponseEntity<ErrorResponse> handleJsonMappingException(JsonMappingException messege) {
		String errorMessage = "Invalid JSON format";
		ErrorResponse apiResponse = new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ErrorResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param messege
	 * @return new response entity with error message and 400 Bad Request status
	 * @implNote This method will run when user try to enter data in wrong format
	 */
	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<ErrorResponse> handleJsonParseException(JsonParseException messege) {
		String errorMessage = "Invalid JSON format " + messege.getMessage();
		ErrorResponse apiResponse = new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ErrorResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param messege
	 * @return new response entity with error message and 403 Forbidden status
	 * @implNote This exception will run when user doesnt have authorization
	 */

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> customHandleForbiddenException(AccessDeniedException ex) {
		String errorCode = ex.getLocalizedMessage();
		ErrorResponse apiResponse = new ErrorResponse("JWT Token", errorCode, "You are unauthorized for this",
				HttpStatus.FORBIDDEN);
		return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> JwtExpired(ExpiredJwtException ex) {
		String errorMessage = ex.getLocalizedMessage();
		ErrorResponse apiResponse = new ErrorResponse(errorMessage, HttpStatus.FORBIDDEN);
		return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
	}

}
