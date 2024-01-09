package com.gamegearhub.constants;

/**
 * All the constants used by validation annoations are in this class
 * @author mdsharif
 *
 */
public class ValidationConstants {

	public static final String EMAIL_FORMAT = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	public static final String INVALID_EMAIL = "Email must be of fromat test@gmail.com";
	public static final String INVALID_GENRE = "Genre Should be from: RPG, SHOOTING, ADVENTURE, RETRO, PUZZLE";
	public static final String INVALID_ORDER_STATUS = "Order status should be from: PLACED, REJECTED, DELIVERED, PENDING";
	public static final String DATE_FORMAT = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";
	public static final String INVALID_DATE = "Date must be of format 'YYYY-MM-DD'";
}
