package com.gamegearhub.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gamegearhub.constants.AdminConstants;
import com.gamegearhub.constants.GameConstants;
import com.gamegearhub.constants.HttpMethodConstants;
import com.gamegearhub.constants.UserConstants;
import com.gamegearhub.dto.GameDto;
import com.gamegearhub.dto.OrderDto;
import com.gamegearhub.dto.UserDto;
import com.gamegearhub.entities.Game;
import com.gamegearhub.entities.Order;
import com.gamegearhub.entities.User;
import com.gamegearhub.mapper.GameMapper;
import com.gamegearhub.mapper.OrderMapper;
import com.gamegearhub.mapper.UserMapper;
import com.gamegearhub.rto.GameRto;
import com.gamegearhub.rto.UpdateGameRto;
import com.gamegearhub.rto.UpdateOrderStatusRto;
import com.gamegearhub.service.AdminService;
import com.gamegearhub.util.ResponseHandler;
import com.gamegearhub.validations.LogRequest;
import com.gamegearhub.validations.LogTime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * This class have all the API's for admin
 * 
 * @author mdsharif
 *
 */
@CrossOrigin(origins = GameConstants.ANGULAR_URL)
@RestController
@RequestMapping(AdminConstants.ADMIN_PATH)
@PreAuthorize(AdminConstants.ADMIN_AUTHORITY)
@Tag(name = "Admin Api")
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {

		this.adminService = adminService;
	}

	
	@PostMapping(AdminConstants.ADD_GAME_PATH)
	@Operation(summary = "API for Adding new Game")
	public ResponseEntity<Object> addGame(@Valid @RequestPart(GameConstants.GAME) GameRto gameRto,
			@RequestPart(value = GameConstants.IMAGES, required = false) MultipartFile[] gameImages,
			Authentication authentication) throws IOException {

		Game game = GameMapper.GAMEMAPPER.GameRtoToGame(gameRto);
		Game newlyAddedGame = adminService.addGame(game, gameImages);
		GameDto gameDto = GameMapper.GAMEMAPPER.GameToGameDto(newlyAddedGame);
		gameDto.add(linkTo(methodOn(GameController.class).getGameById(gameDto.getGameId(), null))
				.withRel(GameConstants.GAME_DETAILS).withType(HttpMethodConstants.GET));
		return ResponseHandler.generateResponse(GameConstants.GAME_ADDED_SUCCESS, HttpStatus.CREATED, gameDto);

	}

	
	@DeleteMapping(AdminConstants.DELETE_GAME_PATH)
	@Operation(summary = "API for deleting Game")
	public ResponseEntity<Object> deleteGame(@Valid @PathVariable(GameConstants.GAME_ID) Integer gameId) {
		adminService.deleteGame(gameId);
		return ResponseHandler.generateResponse(GameConstants.GAME_DELETED_SUCCESS, HttpStatus.OK);
	}

	@PutMapping(AdminConstants.UPDATE_GAME_PATH)
	@Operation(summary = "API for Updating existing Game")
	public ResponseEntity<Object> updateGame(@Valid @RequestBody UpdateGameRto game,
			@PathVariable(GameConstants.GAME_ID) Integer gameId) {
		Game gametoUpdate = GameMapper.GAMEMAPPER.updateGameRtoToGame(game);
		Game updatedGame = adminService.updateGame(gametoUpdate, gameId);
		GameDto gameDto = GameMapper.GAMEMAPPER.GameToGameDto(updatedGame);

		gameDto.add(linkTo(methodOn(GameController.class).getGameById(gameId, null)).withRel(GameConstants.GAME_DETAILS)
				.withType(HttpMethodConstants.GET));

		return ResponseHandler.generateResponse(GameConstants.GAME_UPDATED_SUCCESS + gameDto.getGameId(),
				HttpStatus.ACCEPTED, gameDto);
	}

	
	@PutMapping(AdminConstants.UPDATE_ORDER_STATUS)
	@Operation(summary = "API for updating order status")
	public ResponseEntity<Object> updateOrderStatus(@Valid @RequestBody UpdateOrderStatusRto updateOrder,
			@PathVariable(UserConstants.ORDER_ID) Integer orderId) {
		Order order = OrderMapper.MAPPER.updateOrderStatusRtoToOrder(updateOrder);
		String updateOrderStatus = adminService.updateOrderStatus(order, orderId);
		return ResponseHandler.generateResponse(updateOrderStatus, HttpStatus.ACCEPTED);
	}

	@GetMapping(AdminConstants.GET_ALL_USERS_PATH)
	@Operation(summary = "API for getting all user")
	public ResponseEntity<Object> getAllUser() {
		List<User> allUsers = adminService.getAllUsers();
		List<UserDto> users = allUsers.stream().map((user) -> UserMapper.USERMAPPER.userToUserDto(user))
				.collect(Collectors.toList());
		return ResponseHandler.generateResponse("All User fetched successfully", HttpStatus.OK, users);
	}


	@LogTime
	@LogRequest
	@GetMapping(AdminConstants.ALL_ORDERS)
	@Operation(summary = "API for getting all orders")
	public ResponseEntity<Object> getAllOrders() throws InterruptedException, ExecutionException {

		List<Order> allOrders = adminService.getAllOrders();
		List<OrderDto> allOrdersDto = allOrders.stream().map(order -> {
			OrderDto orders = OrderMapper.MAPPER.orderToOderDto(order);

			orders.add(linkTo(methodOn(AdminController.class).updateOrderStatus(null, order.getOrderId()))
					.withRel(GameConstants.UPDATE_ORDER).withType(HttpMethodConstants.PUT));

			orders.add(linkTo(methodOn(AdminController.class).deleteOrder(order.getOrderId()))
					.withRel(GameConstants.DELETE_ORDER).withType(HttpMethodConstants.DELETE));
			return orders;
		}).collect(Collectors.toList());

		return ResponseHandler.generateResponse("All Orders fetched Successfully", HttpStatus.OK, allOrdersDto);
	}


	@DeleteMapping(AdminConstants.DELETE_ORDER_PATH)
	@Operation(summary = "API for deleting order")
	public ResponseEntity<Object> deleteOrder(@Valid @PathVariable(UserConstants.ORDER_ID) Integer orderId) {
		adminService.deleteOrder(orderId);
		return ResponseHandler.generateResponse(GameConstants.GAME_DELETED_SUCCESS, HttpStatus.ACCEPTED);
	}

}
