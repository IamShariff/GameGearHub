package com.gamegearhub.exceptions;

import org.springframework.http.HttpStatus;

import com.gamegearhub.globalexception.GenericException;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * This exception will occur when user or admin searched for something and it's
 * not present in DB
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
public class NotFoundException extends GenericException {

	private static final long serialVersionUID = 1L;
	private static final HttpStatus httpStatusCode = HttpStatus.NOT_FOUND;

	public NotFoundException(final String fieldName, final String message) {
		super(fieldName, httpStatusCode, message);
	}

}
