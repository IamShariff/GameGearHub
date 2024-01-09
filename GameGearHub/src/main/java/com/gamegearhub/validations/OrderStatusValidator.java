package com.gamegearhub.validations;

import java.util.Arrays;

import com.gamegearhub.enums.OrderStatusGenre;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * This class is implemeting the business logic for ValidateOrderStatus
 * annotation
 * 
 * @author mdsharif
 *
 */
public class OrderStatusValidator implements ConstraintValidator<ValidateOrderStatus, String> {

	@Override
	public boolean isValid(String orderStatus, ConstraintValidatorContext context) {

		/**
		 * It will verify wheather entered orderStatus is present or not in OrderStatus
		 * Genre
		 */
		boolean isOrderStatusExists = Arrays.stream(OrderStatusGenre.values())
				.anyMatch((status) -> status.name().equalsIgnoreCase(orderStatus));
		return isOrderStatusExists;
	}

}
