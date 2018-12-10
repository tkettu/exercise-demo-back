package com.terok.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.terok.demo.models.Users;

@RestController
@RequestMapping("/api/user")
public class UserController {

	Logger logger = LogManager.getLogger();

	/*
	 * POST new User
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST,
					consumes="application/json")
	public String addUser( @RequestBody Users user) {
		user.set_id(ObjectId.get());
		
		String msg = String.format("Adding %s", user.getUserName());
		logger.info(msg);
		
		String userName = user.userName;
		String email = user.email;
		// Todo Check if username or email taken
		String password = user.password;
		// HttpStatus
		// return response.getStatus();
		return String.format("User %s added", userName);
	}

}
