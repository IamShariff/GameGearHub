package com.gamegearhub.dto;

import org.springframework.hateoas.RepresentationModel;

import com.gamegearhub.entities.UserImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used when User want to check their detail
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDto extends RepresentationModel<GetUserDto> {

	private String userName;
	
	private String email;
	
	private String password;
	
	private String userPincode;
	
	private Long walletBalance;

	private UserImage profilePic;
	
	

}
