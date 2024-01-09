package com.gamegearhub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.gamegearhub.dto.AllGamesDto;
import com.gamegearhub.dto.GameDto;
import com.gamegearhub.entities.Game;
import com.gamegearhub.rto.GameRto;
import com.gamegearhub.rto.UpdateGameRto;

/**
 * This interface have methods to convert game,gameDto and gameRto
 * 
 * @implNote Its implementation is in target class
 * @author mdsharif
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface GameMapper {

	GameMapper GAMEMAPPER = Mappers.getMapper(GameMapper.class);

	/**
	 * 
	 * @param gameRto
	 * @return Game
	 */
	Game GameRtoToGame(GameRto gameRto);

	/**
	 * 
	 * @param game
	 * @return GameDto
	 */
	GameDto GameToGameDto(Game game);

	/**
	 * 
	 * @param Game
	 * @return AllGamesDto
	 */
	AllGamesDto gameToAllGamesDto(Game game);
	
	/**
	 * 
	 * @param updateGameRto
	 * @return Game
	 */
	Game updateGameRtoToGame(UpdateGameRto updateGameRto);

}
