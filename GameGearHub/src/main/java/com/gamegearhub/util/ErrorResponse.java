package com.gamegearhub.util;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used to sent user/admin status of their API calls
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends RepresentationModel<ErrorResponse> {

	private String fieldName;
	private String errorCode;
	private String message;
	private HttpStatus status;

	public ErrorResponse(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

}
