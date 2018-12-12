package com.terok.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.terok.demo.models.Users;
import com.terok.demo.repositories.UsersRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {

	Logger logger = LogManager.getLogger();

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	/*
	 * POST new User
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST,
					consumes="application/json")
	public String addUser(@Valid @RequestBody Users user) {
		
		logger.info(String.format("Adding %s", user.userName));
		
		if(usersRepository.findByUserName(user.userName) != null) {
			return "Username already taken " + HttpStatus.BAD_REQUEST.toString();
		}
		
		//user.set_id(ObjectId.get());
				
		String userName = user.userName;
		user.setPassword(passwordEncoder.encode(user.password));
		usersRepository.save(user);
		// TODO HttpStatus
		// return response.getStatus();
		return String.format("User %s added", userName);
	}

}
