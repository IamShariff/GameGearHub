package com.gamegearhub.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gamegearhub.constants.ExceptionConstants;
import com.gamegearhub.dao.UserDao;
import com.gamegearhub.entities.User;
import com.gamegearhub.exceptions.NotFoundException;

/**
 * In this class, we are retrieving user details from userDao and assigning
 * them to the Spring Security's UserDetails object.
 * @author mdsharif
 *
 */
@Component
public class SpringUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userInfo = userDao.findByEmail(email);// detils mene khud banayi hai
		return userInfo.map(SpringUserDetails::new)
				.orElseThrow(() -> new NotFoundException(ExceptionConstants.USER_DONT_EXIST, email));

	}

}
