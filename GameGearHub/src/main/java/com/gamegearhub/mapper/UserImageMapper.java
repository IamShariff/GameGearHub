package com.gamegearhub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.gamegearhub.dto.UserImageDto;
import com.gamegearhub.entities.UserImage;

/**
 * This interface have methods to convert userImage to userImageDto
 * 
 * @implNote Its implementation is in target class
 * @author mdsharif
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface UserImageMapper {

	UserImageMapper MAPPER = Mappers.getMapper(UserImageMapper.class);

	UserImageDto userImageToUserImageDto(UserImage userImage);

}
