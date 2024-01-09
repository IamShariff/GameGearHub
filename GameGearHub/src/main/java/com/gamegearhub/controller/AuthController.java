package com.gamegearhub.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gamegearhub.constants.AdminConstants;
import com.gamegearhub.constants.ExceptionConstants;
import com.gamegearhub.constants.GameConstants;
import com.gamegearhub.constants.UserConstants;
import com.gamegearhub.dto.UserDto;
import com.gamegearhub.entities.User;
import com.gamegearhub.exceptions.NotFoundException;
import com.gamegearhub.jwtservice.JwtService;
import com.gamegearhub.mapper.UserMapper;
import com.gamegearhub.rto.UserRto;
import com.gamegearhub.service.AdminService;
import com.gamegearhub.service.UserService;
import com.gamegearhub.util.JwtRequest;
import com.gamegearhub.validations.LogRequest;
import com.gamegearhub.validations.LogResponse;
import com.gamegearhub.validations.LogTime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * This class will be used for login, adding and authenticating user
 * 
 * @author mdsharif
 *
 */
@CrossOrigin(origins = GameConstants.ANGULAR_URL)
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Api")
public class AuthController {

	private final UserService userService;

	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	private final AdminService adminService;


	public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager,
			AdminService adminService) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.adminService = adminService;
	}

	@LogTime
	@LogRequest
	@LogResponse
	@PostMapping("/authenticate")
	@Operation(summary = "API for generating Jwt Token")
	/**
	 * 
	 * @param jwtRequest This method is used to authenticating user credentials and
	 *                   sending newly created jwt token
	 * @return
	 */
	public String authenticateAndGetToken(@RequestBody JwtRequest jwtRequest) {

		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));

		if (authenticate.isAuthenticated()) {

			return jwtService.generateToken(jwtRequest);

		} else {
			throw new NotFoundException(ExceptionConstants.USER_DONT_EXIST, jwtRequest.getEmail());
		}

	}

	/**
	 * @param UserRto
	 * @return newUser and 201 created status
	 * @throws IOException
	 * @apiNote This Api is used to add new user to database
	 */
	@PostMapping(UserConstants.ADD_USER_PATH)
	@Operation(summary = "API for adding new user")
	public ResponseEntity<UserDto> addUser(@Valid @RequestPart(UserConstants.USER) UserRto userRto,
			@RequestPart(value = "profilePic", required = false) MultipartFile imageFile) throws IOException {

		User newUser = UserMapper.USERMAPPER.userRtoToUser(userRto);
		User adduser = userService.adduser(newUser, imageFile);
		UserDto userDto = UserMapper.USERMAPPER.userToUserDto(adduser);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param userRto
	 * @return newlyCreated Admin
	 * @throws IOException
	 * @apiNote This Api will be used only when admin is not present and to add him
	 */
	@PostMapping(AdminConstants.ADD_ADMIN)
	@Operation(summary = "API for adding Admin")
	public ResponseEntity<UserDto> addAdmin(@RequestPart(UserConstants.USER) UserRto userRto,
			@RequestPart(value = UserConstants.PROFILE_PIC, required = false) MultipartFile imageFile)
			throws IOException {
		User admin = UserMapper.USERMAPPER.userRtoToUser(userRto);
		User newAdmin = adminService.addAdmin(admin, imageFile);
		UserDto adminDto = UserMapper.USERMAPPER.userToUserDto(newAdmin);
		return new ResponseEntity<UserDto>(adminDto, HttpStatus.CREATED);

	}
}
