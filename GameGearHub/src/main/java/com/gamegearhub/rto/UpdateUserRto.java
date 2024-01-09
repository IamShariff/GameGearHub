package com.gamegearhub.rto;

import com.gamegearhub.constants.ValidationConstants;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used when user want to update their details
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRto {


	@NotBlank
	@Size(min = 3)
	private String userName;

	@NotBlank
	@Pattern(regexp = ValidationConstants.EMAIL_FORMAT, message = ValidationConstants.INVALID_EMAIL)
	private String email;

	@NotBlank
	@Size(min = 6)
	private String password;
	
	@NotNull
	@Digits(integer = 6,message = "invalid", fraction = 0)
	private Long walletBalance;
	
	@Size(min = 6, max = 6, message = "Pincode should be of 6 digit")
	private String userPincode;
}
