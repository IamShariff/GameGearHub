package com.gamegearhub.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gamegearhub.constants.ExceptionConstants;
import com.gamegearhub.constants.GameConstants;
import com.gamegearhub.constants.UserConstants;
import com.gamegearhub.dao.GameDao;
import com.gamegearhub.dao.OrderDao;
import com.gamegearhub.dao.UserDao;
import com.gamegearhub.entities.Game;
import com.gamegearhub.entities.Order;
import com.gamegearhub.entities.User;
import com.gamegearhub.entities.UserImage;
import com.gamegearhub.exceptions.AlreadyExistException;
import com.gamegearhub.exceptions.InsufficientException;
import com.gamegearhub.exceptions.NotFoundException;
import com.gamegearhub.service.UserService;

/**
 * 
 * @author mdsharif
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	private final GameDao gameDao;

	private final OrderDao orderDao;

	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserDao userDao, GameDao gameDao, OrderDao orderDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.gameDao = gameDao;
		this.orderDao = orderDao;
		this.passwordEncoder = passwordEncoder;

	}

	/**
	 * @param: user
	 * @return: newlyAddedUser
	 * @throws IOException
	 * @implNote: New user will be added if no user with that email exist in db
	 *            before and no user can be of name Blake
	 * @throws It can throw AdminNotExist CANT_BE_ADMIN,USER_ALREADY_EXIST exception
	 */
	@Override
	public User adduser(User user, MultipartFile imageFile) throws IOException {

		if (Objects.equals(user.getUserName().toUpperCase(), UserConstants.BLAKE)) {
			throw new NotFoundException(ExceptionConstants.CANT_BE_ADMIN, user.getUserName());
		}
		Optional<User> findByEmail = userDao.findByEmail(user.getEmail());
		if (findByEmail.isPresent()) {
			throw new AlreadyExistException("email", ExceptionConstants.USER_ALREADY_EXIST + user.getEmail());
		}
		UserImage image = new UserImage(imageFile.getOriginalFilename(), imageFile.getContentType(),
				imageFile.getBytes());
		UserImage savedImage = userDao.save(image);
		user.setProfieImage(savedImage);
		user.setRole(UserConstants.USER_ROLE);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User newlyAddedUser = userDao.save(user);
		return newlyAddedUser;

	}

	/**
	 * @param: UpdateUser,user's email
	 * @return: UpdatedUserData
	 * @implNote: This method is used to update user details
	 */
	@Override
	public User updateUser(User updateuser, String email) {

		User user = userDao.findByEmail(email).get();
		user.setUserName(updateuser.getUserName());
		user.setEmail(updateuser.getEmail());
		user.setPassword(passwordEncoder.encode(updateuser.getPassword()));
		user.setWalletBalance(updateuser.getWalletBalance());
		User updatedUser = userDao.save(user);
		return updatedUser;
	}

	/**
	 * @param: DeleteUser
	 * @implNote: If userId is present in db then It will get deleted
	 * @throws It can throw UserDontExist or WrongPassword exception
	 */
	@Override
	public void deleteUser(User user, String email) {
		User userToBeDeleted = userDao.findById(user.getUserId())
				.orElseThrow(() -> new NotFoundException("gameId", ExceptionConstants.USER_DONT_EXIST));
		User userDB = userDao.findByEmail(email).get();

		if (!user.getUserId().equals(userDB.getUserId())) {
			throw new NotFoundException("gameId", ExceptionConstants.USER_DONT_EXIST);
		}
		userDao.delete(userToBeDeleted);

	}

	/**
	 * @param: OrderGameRto
	 * @implNote: If gameid is present and have enough quantity then the game will
	 *            be ordered and user will get order placed message with orderId
	 * @exception: This method can throw NotFound,InsufficientMomey,InsufficientGame
	 *                  exceptions
	 */
	@Override
	@Async
	public Integer orderGame(Order orderedGame, String email, Integer gameId, String pincode) {
		Game game = gameDao.findById(gameId).orElseThrow(
				() -> new NotFoundException(GameConstants.GAME_ID, ExceptionConstants.GAME_DONT_EXIST + gameId));

		if (orderedGame.getGameQuantity() > game.getQuantity() || orderedGame.getGameQuantity() == 0) {

			throw new InsufficientException(GameConstants.GAME_QUANTITY, ExceptionConstants.INSUFFICIENT_GAME_QUANTITY);

		}

		User user = userDao.findByEmail(email).get();
		if (!game.getPincode().contains(pincode)) {
			throw new NotFoundException(UserConstants.PINCODE, ExceptionConstants.NOT_DELIVERABLE + pincode);
		}

		Long totalPrice = orderedGame.getGameQuantity() * game.getGamePrice();

		if (totalPrice > user.getWalletBalance()) {
			throw new InsufficientException(UserConstants.WALLET, ExceptionConstants.INSUFFICIENT_MONEY);
		}

		User admin = userDao.findByUserName(UserConstants.BLAKE).get();
		Long adminUpdatedWalletBalance = admin.getWalletBalance() + totalPrice;
		Long userUpdatedWalletBalance = user.getWalletBalance() - totalPrice;
		user.setWalletBalance(userUpdatedWalletBalance);
		admin.setWalletBalance(adminUpdatedWalletBalance);

		Integer updatedGameQuantity = game.getQuantity() - orderedGame.getGameQuantity();
		LocalDate date = LocalDate.now();

		orderedGame.setGameId(gameId);
		orderedGame.setUserId(user.getUserId());
		orderedGame.setOrderedDate(date);
		orderedGame.setStatus(GameConstants.ORDERED);
		Order newOrder = orderDao.save(orderedGame);
		game.setQuantity(updatedGameQuantity);
		gameDao.save(game);
		userDao.save(user);
		userDao.save(admin);
		return newOrder.getOrderId();
	}

	/**
	 * @param: orderId
	 * @return: orderdetails of given orderId
	 * @exception: It can throw NoOrderExist exception
	 */
	@Override
	public Order trackOrder(Integer orderId, String email) {

		User user = userDao.findByEmail(email).get();

		List<Order> allOrders = orderDao.findByuserId(user.getUserId()).get();
		if (allOrders.isEmpty()) {
			throw new NotFoundException(GameConstants.ORDER_ID, ExceptionConstants.USER_HAVE_NO_ORDERS);

		}

		List<Order> orders = allOrders.stream().filter((order) -> order.getOrderId().equals(orderId))
				.collect(Collectors.toList());
		Order order = orders.get(0);

		return order;
	}

	/**
	 * @param: userId
	 * @return: list of all orders by given userId
	 * @exception: It can throw NoOrderExist exception
	 */
	@Override
	public List<Order> getAllOrdersByUser(String email) {

		User user = userDao.findByEmail(email).get();
		List<Order> orders = orderDao.findByuserId(user.getUserId()).get();
		if (orders.isEmpty()) {
			throw new NotFoundException("email", ExceptionConstants.USER_HAVE_NO_ORDERS + user.getUserId());

		}

		return orders;

	}

	/**
	 * @param userId
	 * @param email
	 * @return user with given id
	 */
	@Override
	public User getUserDetails(Integer userId, String email) {
		User user = userDao.findByEmail(email).get();
		User findById = userDao.findById(userId).get();
		if (Objects.isNull(findById) || !Objects.equals(userId, user.getUserId())) {
			throw new NotFoundException(UserConstants.USER_ID, ExceptionConstants.USER_DONT_EXIST + userId);
		}

		return findById;
	}

}
