package com.gamegearhub.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used when user want to see all game details
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllGamesDto extends RepresentationModel<AllGamesDto> {

	
	
	
	private Integer gameId;
	private String gameTitle;
	private String genre;
	private Long gamePrice;
}
