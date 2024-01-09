package com.gamegearhub.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegearhub.entities.User;
import com.gamegearhub.entities.UserImage;

/**
 * 
 * @author mdsharif
 *
 */
public interface UserDao extends JpaRepository<User, Integer> {

	/**
	 * 
	 * @param adminName
	 * @return Optional of user
	 * @implNote This method is used to find User by username
	 */
	Optional<User> findByUserName(String userName);

	/**
	 * 
	 * @param email
	 * @return Optional of User
	 * @implNote This method is used to find user by email
	 */
	Optional<User> findByEmail(String email);

	/**
	 * 
	 * @param image
	 * @return
	 * @implNote This method is used to save userImage
	 */
	UserImage save(UserImage image);

}
