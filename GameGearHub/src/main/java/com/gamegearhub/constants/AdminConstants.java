package com.gamegearhub.constants;

/**
 * All the constants used by admin are in this class
 * 
 * @author mdsharif
 *
 */
public class AdminConstants {

	public static final String ADD_ADMIN_PATH = "/add";
	public static final String ADD_GAME_PATH = "/addgame";
	public static final String UPDATE_GAME_PATH = "/updategame/{gameId}";
	public static final String DELETE_GAME_PATH = "/deletegame/{gameId}";
	public static final String UPDATE_ORDER_STATUS = "/updateorder/{orderId}";
	public static final String GET_ALL_USERS_PATH = "/allusers";
	public static final String ADMIN_AUTHORITY = "hasAuthority('ADMIN')";
	public static final String ADD_ADMIN = "/addadmin";
	public static final String ADMIN_ROLE = "ADMIN";
	public static final String ALL_ORDERS = "/allorders";
	public static final String DELETE_ORDER_PATH = "deleteorder/{orderId}";
	public static final String ADMIN_PATH = "/admin";
	

}
