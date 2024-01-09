package com.gamegearhub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.gamegearhub.dto.GameImageDto;
import com.gamegearhub.entities.GameImage;

/**
 * This interface have methods to convert gameImage to gameImageDto
 * 
 * @implNote Its implementation is in target class
 * @author mdsharif
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface GameImageMapper {

	GameImageMapper MAPPER = Mappers.getMapper(GameImageMapper.class);

	GameImageDto imageToimageDto(GameImage image);

}
