package com.gamegearhub.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamegearhub.constants.GameConstants;
import com.gamegearhub.constants.HttpMethodConstants;
import com.gamegearhub.dto.GameDto;
import com.gamegearhub.entities.Game;
import com.gamegearhub.mapper.GameMapper;
import com.gamegearhub.service.GameService;
import com.gamegearhub.util.ResponseHandler;
import com.gamegearhub.validations.LogRequest;
import com.gamegearhub.validations.LogResponse;
import com.gamegearhub.validations.LogTime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.validation.Valid;

/**
 * This class have all game's API's
 * 
 * @author mdsharif
 *
 */
@CrossOrigin(origins = GameConstants.ANGULAR_URL)
@RestController
@RequestMapping(GameConstants.GAME_PATH)
@Tag(name = "game Api")
public class GameController {

	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	/**
	 * 
	 * @param gameId
	 * @return game and 200 Ok status
	 * @apiNote This api is used to get single game
	 */
	@GetMapping(GameConstants.GET_GAME_BY_ID_PATH)
	@Operation(summary = "API for getting single game details by its id")
	public ResponseEntity<Object> getGameById(@PathVariable(GameConstants.GAME_ID) Integer gameId,
			Authentication authentication) {
		Game gameById = gameService.getGameById(gameId);

		GameDto gameDto = GameMapper.GAMEMAPPER.GameToGameDto(gameById);

		gameDto.add(linkTo(methodOn(UserController.class).orderGame(null, gameDto.getGameId(), authentication))
				.withRel(GameConstants.ORDER_GAME).withType(HttpMethodConstants.POST));

		return ResponseHandler.generateResponse(GameConstants.GAME_GET_SUCCESS + gameDto.getGameId(), HttpStatus.OK,
				gameDto);
	}

	@LogTime
	@LogRequest
	@LogResponse
	@GetMapping(GameConstants.GET_ALL_GAME_PATH)
	@Operation(summary = "API for getting all games")
	public ResponseEntity<List<Game>> getAllGames() {

		List<Game> allGames = gameService.getAllGames();
		return ResponseEntity.ok(allGames);
	}

	@GetMapping(GameConstants.SORT_GAME_PATH)
	@Operation(summary = "API for sorting game")
	public ResponseEntity<Object> sortGame(@PathVariable(GameConstants.SORTING_CHOICE) String sortingChoice) {
		List<Game> sortGame = gameService.sortGame(sortingChoice);
		List<GameDto> games = sortGame.stream().map(((game) -> GameMapper.GAMEMAPPER.GameToGameDto(game)))
				.collect(Collectors.toList());
		return ResponseHandler.generateResponse("Sorted Game", HttpStatus.OK, games);
	}

	@GetMapping(GameConstants.CATEGORIZED_BY_GENRE_PATH)
	@Operation(summary = "API for getting game by genre")
	public ResponseEntity<Object> categorizedByGenre(@Valid @PathVariable(GameConstants.GENRE) String genre) {
		List<Game> categorizedByGenre = gameService.CategorizedByGenre(genre);
		List<GameDto> games = categorizedByGenre.stream().map(((game) -> GameMapper.GAMEMAPPER.GameToGameDto(game)))
				.collect(Collectors.toList());
		return ResponseHandler.generateResponse("Games Categorized by " + genre, HttpStatus.OK, games);
	}

}
