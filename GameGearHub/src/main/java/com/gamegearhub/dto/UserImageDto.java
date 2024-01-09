package com.gamegearhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used to show userImage
 * @author mdsharif
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserImageDto {
	
	private long id;
	private String name;
	private String type;

}
