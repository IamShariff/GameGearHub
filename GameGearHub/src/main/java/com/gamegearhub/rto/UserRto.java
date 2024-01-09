package com.gamegearhub.rto;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used when new user want to register
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRto {

	@NotBlank
	@Size(min = 3)
	private String userName;

	@NotBlank
	@Size(min = 6)
	private String password;

	@NotBlank
	@Email
	private String email;
	
	@NotNull
	@Digits(integer = 6,message = "invalid", fraction = 0)
	private Long walletBalance;
	
	@Size(min = 6, max = 6, message = "Pincode should be of 6 digit")
	private String userPincode;
}
