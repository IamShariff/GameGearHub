package com.gamegearhub.rto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used when user want to delete their profile
 * 
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserRto {

	@NotNull
	private Integer userId;

}
