package com.terok.demo.controllers;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.mongodb.util.JSON;
import com.terok.demo.models.Users;
import com.terok.demo.repositories.UsersRepository;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	Logger logger = LogManager.getLogger();
	/*
	 * POST login user
	 */
	
	@RequestMapping(value="", method = RequestMethod.POST)
	public String login(@RequestBody Users user) {
		
		logger.info("Login as " +  user.userName);
		Users user2 = usersRepository.findByUserName(user.userName); 
		if(user != null) {
			if(passwordEncoder.matches(user.password, user2.getPassword())){
				
				return "TOKEN"; //TODO token
			}
			
		}
		
		return "Username or password wrong " + HttpStatus.UNAUTHORIZED.toString();
	}
	

}
