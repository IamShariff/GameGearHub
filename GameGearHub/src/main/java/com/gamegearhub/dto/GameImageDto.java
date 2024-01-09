package com.gamegearhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used to show gameImage
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameImageDto {
	
	private long id;
	private String name;
	private String type;

}
