package com.gamegearhub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.gamegearhub.dto.GetUserDto;
import com.gamegearhub.dto.UserDto;
import com.gamegearhub.entities.User;
import com.gamegearhub.rto.DeleteUserRto;
import com.gamegearhub.rto.UpdateUserRto;
import com.gamegearhub.rto.UserRto;

/**
 * This interface have methods to convert admin,adminDto and adminRto
 * 
 * @implNote Its implementation is in target class
 * @author mdsharif
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface UserMapper {

	UserMapper USERMAPPER = Mappers.getMapper(UserMapper.class);

	/**
	 * 
	 * @param userRto
	 * @return User
	 */
	User userRtoToUser(UserRto userRto);

	/**
	 * 
	 * @param user
	 * @return UserDto
	 */
	UserDto userToUserDto(User user);

	/**
	 * 
	 * @param updateUserRto 
	 * @return User
	 */
	User updateuserRtoToUser(UpdateUserRto updateUserRto);

	/**
	 * 
	 * @param deleteUserRto
	 * @return user
	 */
	User deleteUserRtoToUser(DeleteUserRto deleteUserRto);
	
	/**
	 * 
	 * @param user
	 * @return GetUserDto
	 */
	GetUserDto UserToGetUserDto(User user);
	

	
	

}
