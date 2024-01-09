package com.gamegearhub.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used when user or admin wants to track order
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto>{

	private Integer orderId;
	private Integer gameId;
	private String status;
	private LocalDate orderedDate;
	private Integer gameQuantity;

}
