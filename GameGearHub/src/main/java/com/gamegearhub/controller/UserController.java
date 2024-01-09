package com.gamegearhub.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamegearhub.constants.GameConstants;
import com.gamegearhub.constants.HttpMethodConstants;
import com.gamegearhub.constants.UserConstants;
import com.gamegearhub.dto.AllOrdersDto;
import com.gamegearhub.dto.GetUserDto;
import com.gamegearhub.dto.OrderDto;
import com.gamegearhub.dto.UserDto;
import com.gamegearhub.entities.Order;
import com.gamegearhub.entities.User;
import com.gamegearhub.exceptions.WrongPasswordException;
import com.gamegearhub.mapper.OrderMapper;
import com.gamegearhub.mapper.UserMapper;
import com.gamegearhub.rto.DeleteUserRto;
import com.gamegearhub.rto.OrderGameRto;
import com.gamegearhub.rto.UpdateUserRto;
import com.gamegearhub.service.UserService;
import com.gamegearhub.util.ResponseHandler;
import com.gamegearhub.validations.LogRequest;
import com.gamegearhub.validations.LogResponse;
import com.gamegearhub.validations.LogTime;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * This class have all the API's for User
 * 
 * @author mdsharif
 *
 */
@CrossOrigin(origins = GameConstants.ANGULAR_URL)
@RestController
@RequestMapping(UserConstants.USER_PATH)
@PreAuthorize(UserConstants.USER_AUTHORITY)
@Tag(name = "User Api")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PutMapping(UserConstants.UPDATE_USER_PATH)
	@Operation(summary = "API for updating existing user")
	@ApiResponse(responseCode = "200", description = "User updated successfully")
	@ApiResponse(responseCode = "400", description = "Bad request or validation error")
	public ResponseEntity<Object> updateUser(@Valid @RequestBody UpdateUserRto userRto, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();
		User user = UserMapper.USERMAPPER.updateuserRtoToUser(userRto);
		User updateUser = userService.updateUser(user, email);
		UserDto userDto = UserMapper.USERMAPPER.userToUserDto(updateUser);

		return ResponseHandler.generateResponse("User Updated Successfully", HttpStatus.OK, userDto);
	}

	@DeleteMapping(UserConstants.DELETE_USER_PATH)
	@Operation(summary = "API for deleting User")
	@ApiResponse(responseCode = "200", description = "User Deleted successfully")
	@ApiResponse(responseCode = "400", description = "Bad request or validation error")
	public ResponseEntity<Object> deleteUser(@Valid @RequestBody DeleteUserRto user, Authentication authentication)
			throws WrongPasswordException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User usertoBeDeleted = UserMapper.USERMAPPER.deleteUserRtoToUser(user);
		userService.deleteUser(usertoBeDeleted, userDetails.getUsername());
		return ResponseHandler.generateResponse("User Deleted Successfully", HttpStatus.OK);
	}

	@PostMapping(UserConstants.ORDER_GAME_PATH)
	@LogRequest
	@LogResponse
	@LogTime
	@Operation(summary = "API for order game")
	@ApiResponse(responseCode = "201", description = "Game ordered successfully")
	@ApiResponse(responseCode = "400", description = "Bad request or validation error")
	public ResponseEntity<String> orderGame(@RequestBody OrderGameRto orderGame,
			@Parameter(description = "The game id", required = true) @PathVariable(GameConstants.GAME_ID) Integer gameId,
			Authentication authentication) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();
		Order orderGameRtoToOrder = OrderMapper.MAPPER.orderGameRtoToOrder(orderGame);
		Integer orderId = userService.orderGame(orderGameRtoToOrder, email, gameId, orderGame.getPincode());

		return ResponseEntity.ok(GameConstants.ORDERED_SUCCESS + orderId);
	}

	@GetMapping(UserConstants.TRACK_ORDER_PATH)
	@PreAuthorize(UserConstants.USER_AUTHORITY)
	@Operation(summary = "API for tracking order")
	@ApiResponse(responseCode = "200", description = "Order tracked successfully")
	@ApiResponse(responseCode = "400", description = "Bad request or validation error")
	public ResponseEntity<Object> trackOrder(
			@Parameter(description = "The order ID to track", required = true) @PathVariable(UserConstants.ORDER_ID) Integer orderId,
			Authentication authentication) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Order orderDetails = userService.trackOrder(orderId, userDetails.getUsername());
		OrderDto orderDto = OrderMapper.MAPPER.orderToOderDto(orderDetails);
		return ResponseHandler.generateResponse("Order details", HttpStatus.OK, orderDto);
	}

	@GetMapping(UserConstants.GET_ALL_ORDERS_PATH)
	@Operation(summary = "API for getting all orders of user")
	public ResponseEntity<Object> getAllOrderOfUser(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		List<Order> allOrdersByUser = userService.getAllOrdersByUser(userDetails.getUsername());
		List<AllOrdersDto> orders = allOrdersByUser.stream().map(order -> {
			AllOrdersDto gameOrders = OrderMapper.MAPPER.ordersToAllOrderDto(order);
			gameOrders.add(linkTo(methodOn(UserController.class).trackOrder(order.getOrderId(), authentication))
					.withRel(GameConstants.TRACK_ORDER).withType(HttpMethodConstants.GET));
			return gameOrders;
		}).collect(Collectors.toList());
		return ResponseHandler.generateResponse("All Orders", HttpStatus.OK, orders);
	}

	@GetMapping(UserConstants.GET_USER_PATH)
	@Operation(summary = "API for getting user details by id")
	public ResponseEntity<Object> getUserById(@Valid @PathVariable(UserConstants.USER_ID) Integer userId,
			Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		User userDetails = userService.getUserDetails(userId, user.getUsername());

		GetUserDto userDto = UserMapper.USERMAPPER.UserToGetUserDto(userDetails);
		userDto.setProfilePic(userDetails.getProfieImage());
		userDto.add(linkTo(methodOn(UserController.class).updateUser(null, authentication))
				.withRel(GameConstants.UPDATE_PROFILE).withType(HttpMethodConstants.PUT));
		return ResponseHandler.generateResponse("User fetched successfully", HttpStatus.OK, userDto);
	}

}
