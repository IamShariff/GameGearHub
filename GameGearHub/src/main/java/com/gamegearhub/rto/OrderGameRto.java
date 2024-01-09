package com.gamegearhub.rto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used when user will order the game
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderGameRto {

	@NotNull
	private Integer gameQuantity;
	
	@Size(min = 6, max = 6, message = "Pincode should be of 6 digit")
	private String pincode;
}
