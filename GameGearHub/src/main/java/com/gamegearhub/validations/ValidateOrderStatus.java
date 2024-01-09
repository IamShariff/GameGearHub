package com.gamegearhub.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * This is a custom annotation used to validate orderStatus It is a field level
 * annotation that will run at runtime Logic of this annotation is implemented
 * in OrderStatusValidator Class
 * 
 * @author mdsharif
 *
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OrderStatusValidator.class)
public @interface ValidateOrderStatus {

	public String message() default "Invalid OrderStatus";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
