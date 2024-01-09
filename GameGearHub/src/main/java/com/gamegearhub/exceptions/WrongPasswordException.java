package com.gamegearhub.exceptions;

import org.springframework.http.HttpStatus;

import com.gamegearhub.globalexception.GenericException;

/**
 * This exception will occur when password is wrong
 * @author mdsharif
 *
 */
public class WrongPasswordException extends GenericException {

	
		private static final long serialVersionUID = 1L;
		private static final HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;

		
		public WrongPasswordException(final String fieldName, final String message) {
			super(fieldName, httpStatusCode, message);
		}

}
