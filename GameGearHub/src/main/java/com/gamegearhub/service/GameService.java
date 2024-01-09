package com.gamegearhub.service;

import java.util.List;

import com.gamegearhub.entities.Game;

/**
 * 
 * @author mdsharif
 *
 */
public interface GameService {
	
	/**
	 * 
	 * @param gameId
	 * @return game having given id
	 */
	public Game getGameById(Integer gameId);

	/**
	 * 
	 * @return list of all games
	 */
	public List<Game> getAllGames();

	/**
	 * 
	 * @param sortChoice
	 * @return Sorted list of game according to sortingChoice
	 */
	public List<Game> sortGame(String sortChoice);
	
	/**
	 * 
	 * @param category
	 * @return list of games with given category
	 */
	public List<Game> CategorizedByGenre(String category);

}
