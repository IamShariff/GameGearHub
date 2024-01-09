package com.gamegearhub.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is used when user will login
 * @author mdsharif
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
	
	public String email;
	public String password;

}
