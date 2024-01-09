package com.gamegearhub.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegearhub.entities.Game;

/**
 * 
 * @author mdsharif
 *
 */
public interface GameDao extends JpaRepository<Game, Integer> {

	/**
	 * 
	 * @param gameTitle
	 * @return Optional of Game
	 * @implNote This method is used to find game by gameTitle
	 */
	Optional<Game> findBygameTitle(String gameTitle);

	/**
	 * 
	 * @param category
	 * @return Game
	 * @implNote This method is used to find list of game by genre
	 */
	List<Game> findBygenre(String genre);

}
