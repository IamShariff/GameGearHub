package com.gamegearhub.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gamegearhub.constants.ExceptionConstants;
import com.gamegearhub.constants.GameConstants;
import com.gamegearhub.dao.GameDao;
import com.gamegearhub.entities.Game;
import com.gamegearhub.exceptions.NotFoundException;
import com.gamegearhub.service.GameService;

/**
 * 
 * @author mdsharif
 *
 */
@Service
public class GameServiceImpl implements GameService {

	private final GameDao gameDao;

	
	public GameServiceImpl(GameDao gameDao) {
		this.gameDao = gameDao;
	}

	/**
	 * @param: gameId
	 * @return: GameDto If id is present in db
	 * @throws It can throw NotFoundException
	 * 
	 */
	@Override
	public Game getGameById(Integer gameId) {
		Game game = gameDao.findById(gameId).orElseThrow(() -> new NotFoundException(GameConstants.GAME_ID,
				ExceptionConstants.DONT_EXIST + gameId));
		return game;
	}

	/**
	 * @implNote: It will return all games present in db
	 */
	@Override
	public List<Game> getAllGames() {

		List<Game> findAll = gameDao.findAll();
		return findAll;
	}

	/**
	 * @param: sortingChoice
	 * @implNote: It will sort game based on sorting choice
	 */
	@Override
	public List<Game> sortGame(String sortChoice) {
		List<Game> sortedGame = new ArrayList<>();

		if (Objects.equals(sortChoice.toUpperCase(), "RATING")) {
			sortedGame = gameDao.findAll(Sort.by(Sort.Direction.ASC, "gameRating"));
		} else if (Objects.equals(sortChoice.toUpperCase(), "DATE")) {
			sortedGame = gameDao.findAll().stream().sorted((game1, game2) -> LocalDate.parse(game1.getGameDate())
					.compareTo(LocalDate.parse(game2.getGameDate()))).collect(Collectors.toList());
		}
		return sortedGame;

	}

	/**
	 * @param: genre
	 * @return: Return list of games with given genre
	 */
	@Override
	public List<Game> CategorizedByGenre(String genre) {

		List<Game> findBygenre = gameDao.findBygenre(genre);
		return findBygenre;
	}
}
