package com.gamegearhub.entities;



import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model for Order
 * @author mdsharif
 *
 */
@Entity
@Table(name="order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends RepresentationModel<Order>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	private Integer gameId;
	private Integer userId;
	private String status;
	private LocalDate orderedDate;
	private Integer gameQuantity;
	
	
	
	
	
}
