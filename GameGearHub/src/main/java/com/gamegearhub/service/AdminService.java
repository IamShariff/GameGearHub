package com.gamegearhub.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gamegearhub.entities.Game;
import com.gamegearhub.entities.Order;
import com.gamegearhub.entities.User;

import jakarta.validation.Valid;

/**
 * 
 * @author mdsharif
 *
 */
public interface AdminService {

	/**
	 * 
	 * @param admin
	 * @param imageFile
	 * @return newlyAdded Admin
	 * @throws IOException
	 */
	public User addAdmin(User admin, MultipartFile imageFile) throws IOException;

	/**
	 * 
	 * @param game
	 * @param gameImages
	 * @return newlyAddedGame
	 * @throws IOException
	 */
	public Game addGame(Game game, MultipartFile[] gameImages) throws IOException;

	/**
	 * 
	 * @param gameId
	 */
	public void deleteGame(@Valid Integer gameId);

	/**
	 * 
	 * @param game
	 * @param gameId
	 * @return updatedGame
	 */
	public Game updateGame(Game game, Integer gameId);

	/**
	 * 
	 * @param updateOrderRto
	 * @param orderId
	 * @return new orderStatus
	 */
	public String updateOrderStatus(Order updateOrderRto, Integer orderId);

	/**
	 * 
	 * @return list of all users
	 */
	public List<User> getAllUsers();

	/**
	 *
	 * @return list of all orders
	 */
	public List<Order> getAllOrders();

	/**
	 * 
	 * @param orderId
	 */
	public void deleteOrder(Integer orderId);

}
