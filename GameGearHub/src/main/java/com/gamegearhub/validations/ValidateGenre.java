package com.gamegearhub.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * This is a custom annotation used to validate genre It is a field level
 * annotation that will run at runtime Logic of this annotation is implemented
 * in GenreValidator Class
 * 
 * @author mdsharif
 *
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenreValidator.class)
public @interface ValidateGenre {

	public String message() default "Invalid Genre";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
