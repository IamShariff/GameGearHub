package com.gamegearhub.exceptions;

import org.springframework.http.HttpStatus;

import com.gamegearhub.globalexception.GenericException;

import lombok.Getter;
import lombok.Setter;

/**
 * This exception will occur when there's not enough game to order
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
public class InsufficientException extends GenericException {

	 
		private static final long serialVersionUID = 1L;
		private static final HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;

		
		public InsufficientException(final String fieldName, final String message) {
			super(fieldName, httpStatusCode, message);
		}

}
