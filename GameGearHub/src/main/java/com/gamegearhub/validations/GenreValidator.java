package com.gamegearhub.validations;

import java.util.Arrays;

import com.gamegearhub.enums.GameGenre;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * This class is implemeting the business logic for ValidateGenre annotation
 * 
 * @author mdsharif
 *
 */
public class GenreValidator implements ConstraintValidator<ValidateGenre, String> {

	/**
	 * It will verify wheather entered genre is present or not in gameGenre
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		boolean isGenreExist = Arrays.stream(GameGenre.values()).anyMatch((genre)->genre.name().equals(value));
		return isGenreExist;
	}

}
