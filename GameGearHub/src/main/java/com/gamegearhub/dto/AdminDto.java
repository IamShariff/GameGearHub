package com.gamegearhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author mdsharif Whenever Someone wants to check admin details only these
 *         fields they can see
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

	private Integer adminId;

	private String adminName;

	private String adminEmail;

}
