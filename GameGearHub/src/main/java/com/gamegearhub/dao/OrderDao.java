package com.gamegearhub.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamegearhub.entities.Order;

/**
 * 
 * @author mdsharif
 *
 */
public interface OrderDao extends JpaRepository<Order, Integer> {

	/**
	 * 
	 * @param userId
	 * @return Optional of Order list
	 * @implNote This method is used to find list of orders by userId
	 */
	Optional<List<Order>> findByuserId(Integer userId);

}
