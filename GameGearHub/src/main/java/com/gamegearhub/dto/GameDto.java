package com.gamegearhub.dto;

import java.util.List;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Admin is sending only these details to the user when they wants to see full game
 * details
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDto extends RepresentationModel<GameDto> {

	private Integer gameId;
	private String gameDescription;
	private Integer gameRating;
	private Long gamePrice;
	private String gameTitle;
	private String gameDate;
	private String genre;
	private Integer quantity;
	private Set<GameImageDto> gameImages;
	private List<String> pincode;
	
}
