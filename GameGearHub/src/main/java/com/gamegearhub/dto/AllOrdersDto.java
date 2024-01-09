package com.gamegearhub.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used when user want to check all orders
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllOrdersDto extends RepresentationModel<AllGamesDto> {
	
	private Integer orderId;
	private Integer gameId;
	

}
