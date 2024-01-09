package com.gamegearhub.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gamegearhub.entities.Order;
import com.gamegearhub.entities.User;

/**
 * 
 * @author mdsharif
 *
 */
public interface UserService {

	/**
	 * 
	 * @param user
	 * @param imageFile
	 * @return newly added User
	 * @throws IOException
	 */
	public User adduser(User user, MultipartFile imageFile) throws IOException;

	/**
	 * 
	 * @param user
	 * @param email
	 * @return updated user
	 */
	public User updateUser(User user, String email);

	/**
	 * 
	 * @param user
	 * @param email
	 */
	public void deleteUser(User user, String email);

	/**
	 * 
	 * @param orderedGame
	 * @param email
	 * @param gameId
	 * @param pincode
	 * @return orderId
	 */
	public Integer orderGame(Order orderedGame, String email, Integer gameId, String pincode);

	/**
	 * 
	 * @param orderId
	 * @param email
	 * @return orderDetails by orderId
	 */
	public Order trackOrder(Integer orderId, String email);

	/**
	 * 
	 * @param email
	 * @return list of all orders by user
	 */
	public List<Order> getAllOrdersByUser(String email);

	/**
	 * 
	 * @param userId
	 * @param email
	 * @return user details having given id
	 */
	public User getUserDetails(Integer userId, String email);

}
