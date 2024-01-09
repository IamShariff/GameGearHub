package com.gamegearhub.rto;

import java.util.List;

import com.gamegearhub.constants.ValidationConstants;
import com.gamegearhub.validations.ValidateGenre;

import jakarta.annotation.Nonnull;
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
 * This class is used for adding new game to db
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameRto {

	@NotBlank
	@Size(min = 5)
	private String gameDescription;

	@NotNull
	@Digits(integer = 1,message = "Game Rating should be less than 10", fraction = 0)
	private Integer gameRating;

	@NotBlank
	@Size(min = 4)
	private String gameTitle;

	
	@Pattern(regexp = ValidationConstants.DATE_FORMAT, message = ValidationConstants.INVALID_DATE)
	private String gameDate;

	@ValidateGenre(message = ValidationConstants.INVALID_GENRE)
	private String genre;

	@Nonnull
	private Integer quantity;
	
	@NotNull
	@Digits(integer = 5,message = "Game price should be less then 99999", fraction = 0)
	private Integer gamePrice;
	
	@NotNull
	private List<String> pincode;

}
