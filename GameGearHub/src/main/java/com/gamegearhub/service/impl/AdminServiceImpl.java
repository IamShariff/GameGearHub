package com.gamegearhub.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gamegearhub.constants.AdminConstants;
import com.gamegearhub.constants.ExceptionConstants;
import com.gamegearhub.constants.GameConstants;
import com.gamegearhub.constants.UserConstants;
import com.gamegearhub.dao.GameDao;
import com.gamegearhub.dao.OrderDao;
import com.gamegearhub.dao.UserDao;
import com.gamegearhub.entities.Game;
import com.gamegearhub.entities.GameImage;
import com.gamegearhub.entities.Order;
import com.gamegearhub.entities.User;
import com.gamegearhub.entities.UserImage;
import com.gamegearhub.exceptions.AlreadyExistException;
import com.gamegearhub.exceptions.NotFoundException;
import com.gamegearhub.service.AdminService;
import com.gamegearhub.validations.LogRequest;
import com.gamegearhub.validations.LogTime;

/**
 * 
 * @author mdsharif
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	private final GameDao gameDao;

	private final OrderDao oderDao;

	private final UserDao userDao;

	private final PasswordEncoder passwordEncoder;

	public AdminServiceImpl(GameDao gameDao, OrderDao oderDao, UserDao userDao, PasswordEncoder passwordEncoder) {
		this.gameDao = gameDao;
		this.oderDao = oderDao;
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * @param admin
	 * @return newlyAddedAdmin
	 * @throws IOException
	 * @implNote This method will be run only one time when there will be no admin
	 * 
	 */
	@Override
	public User addAdmin(User admin, MultipartFile imageFile) throws IOException {
		if (!Objects.equals(admin.getUserName().toUpperCase(), UserConstants.BLAKE)) {
			throw new NotFoundException(ExceptionConstants.CANT_BE_ADMIN, admin.getUserName());
		}
		Optional<User> findByUserName = userDao.findByUserName(admin.getUserName());
		if (findByUserName.isPresent()) {
			throw new AlreadyExistException(ExceptionConstants.ADMIN_ALREADY_EXISTS, admin.getUserName());
		}
		admin.setRole(AdminConstants.ADMIN_ROLE);
		UserImage image = new UserImage(imageFile.getOriginalFilename(), imageFile.getContentType(),
				imageFile.getBytes());
		UserImage savedImage = userDao.save(image);
		admin.setProfieImage(savedImage);

		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		User newAdmin = userDao.save(admin);
		return newAdmin;
	}

	/**
	 * @param game
	 * @return newly added game
	 * @throws IOException
	 */
	@Override
	public Game addGame(Game game, MultipartFile[] imageFiles) throws IOException {

		Set<GameImage> images = addImages(imageFiles);
		game.setGameImages(images);

		List<String> pincodes = game.getPincode().stream().collect(Collectors.toList());

		game.setPincode(pincodes);
		gameDao.save(game);
		Game newlyAddedGame = gameDao.save(game);
		return newlyAddedGame;

	}

	/**
	 * 
	 * @param imageFiles
	 * @return set of images
	 * @throws IOException
	 */
	private Set<GameImage> addImages(MultipartFile[] imageFiles) throws IOException {
		Set<GameImage> images = new HashSet<>();
		for (MultipartFile image : imageFiles) {
			GameImage imageModel = new GameImage(image.getOriginalFilename(), image.getContentType(), image.getBytes());
			images.add(imageModel);
		}
		return images;
	}

	/**
	 * @param game
	 * @implNote This method is used to delete game
	 * @throws It can throw NotFound exception exception
	 * 
	 */
	@Override
	public void deleteGame(Integer gameId) {

		Game gameToBeDeleted = gameDao.findById(gameId).orElseThrow(
				() -> new NotFoundException(GameConstants.GAME_ID, ExceptionConstants.GAME_DONT_EXIST + gameId));

		gameDao.delete(gameToBeDeleted);

	}

	/**
	 * @param game
	 * @implNote gameid is present in database then it will get updated with new
	 *           Data
	 * @throws It can throw NotFound exception
	 * 
	 */
	@Override
	public Game updateGame(Game game, Integer gameId) {

		Game gameToUpdate = gameDao.findById(gameId).orElseThrow(
				() -> new NotFoundException(GameConstants.GAME_ID, ExceptionConstants.GAME_DONT_EXIST + gameId));

		gameToUpdate.setGameDescription(game.getGameDescription());
		gameToUpdate.setGameRating(game.getGameRating());
		gameToUpdate.setGameTitle(game.getGameTitle());
		gameToUpdate.setGenre(game.getGenre());
		gameToUpdate.setQuantity(game.getQuantity());
		gameToUpdate.setGamePrice(game.getGamePrice());
		List<String> pincodes = new ArrayList<>();
		for (String pincode : game.getPincode()) {
			pincodes.add(pincode);
		}
		gameToUpdate.setPincode(pincodes);

		Game updatedGame = gameDao.save(gameToUpdate);

		return updatedGame;
	}

	/**
	 * @param UpdateOrder
	 * @return Ordered Placed message
	 * @exception It can throw NotFound exception
	 */
	@Override
	public String updateOrderStatus(Order updateOrder, Integer orderId) {

		Order orderToBeUpdated = oderDao.findById(orderId).orElseThrow(
				() -> new NotFoundException(GameConstants.ORDER_ID, ExceptionConstants.NO_ORDER_EXIST + orderId));
		orderToBeUpdated.setStatus(updateOrder.getStatus());
		oderDao.save(orderToBeUpdated);
		return GameConstants.ORDER_UPDATED + orderToBeUpdated.getStatus();
	}

	/**
	 * @return List of all users
	 * @implNote: This method will return the list of all users;
	 */
	@Override
	public List<User> getAllUsers() {
		List<User> allUsers = userDao.findAll();
		return allUsers;
	}

	/**
	 * @return List of all orders
	 * @implNote This method is used to get all orders
	 */
	@Override
	@LogTime
	@LogRequest
	public List<Order> getAllOrders() {
		List<Order> allOrders = oderDao.findAll();
		return allOrders;
	}

	/**
	 * @param orderId
	 * @implNote This method is used to delete the order
	 */
	@Override
	public void deleteOrder(Integer orderId) {
		Optional<Order> order = oderDao.findById(orderId);
		if (order.isEmpty()) {
			throw new NotFoundException(GameConstants.ORDER_ID, ExceptionConstants.NO_ORDER_EXIST + orderId);
		}
		oderDao.delete(order.get());

	}

}
