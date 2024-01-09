package com.gamegearhub.rto;

import java.util.List;

import com.gamegearhub.constants.ValidationConstants;
import com.gamegearhub.validations.ValidateGenre;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGameRto {

	
	@NotBlank()
	@Size(min = 5)
	private String gameDescription;

	@Nonnull
	@Digits(integer = 1,message = "invalid", fraction = 0)
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
	
	@Nonnull
	private Long gamePrice;
	
	@Nonnull
	private List<String> pincode;


}
