package com.gamegearhub.rto;

import com.gamegearhub.constants.ValidationConstants;
import com.gamegearhub.validations.ValidateOrderStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used when Admin wants to update orderstatus
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusRto {

	
	@NotBlank
	@ValidateOrderStatus(message = ValidationConstants.INVALID_ORDER_STATUS)
	private String status;

}
