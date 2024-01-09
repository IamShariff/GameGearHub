package com.gamegearhub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.gamegearhub.dto.AllOrdersDto;
import com.gamegearhub.dto.OrderDto;
import com.gamegearhub.entities.Order;
import com.gamegearhub.rto.OrderGameRto;
import com.gamegearhub.rto.UpdateOrderStatusRto;

/**
 * This interface have methods to convert order,orderDto and orderRto
 * 
 * @implNote Its implementation is in target class
 * @author mdsharif
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface OrderMapper {

	OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

	/**
	 * 
	 * @param orderGameRto
	 * @return Order
	 */
	Order orderRtoToOrder(OrderGameRto orderGameRto);

	/**
	 * 
	 * @param updateOrderStatusRto
	 * @return Order
	 */
	Order updateOrderStatusRtoToOrder(UpdateOrderStatusRto updateOrderStatusRto);

	/**
	 * 
	 * @param order
	 * @return OrderGameDto
	 */
	OrderDto orderToOderDto(Order order);

	/**
	 * 
	 * @param orderGameRto
	 * @return Order
	 */
	Order orderGameRtoToOrder(OrderGameRto orderGameRto);
	
	/**
	 * 
	 * @param order
	 * @return AllOrdersDto
	 */
	AllOrdersDto ordersToAllOrderDto(Order order);

}
