package com.gamegearhub.exceptions;
import org.springframework.http.HttpStatus;

import com.gamegearhub.globalexception.GenericException;


public class AlreadyExistException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final HttpStatus httpStatusCode = HttpStatus.CONFLICT;

	
	public AlreadyExistException(final String fieldName, final String message) {
		super(fieldName, httpStatusCode, message);
	}
}