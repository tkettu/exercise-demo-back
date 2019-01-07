package com.terok.demo.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.terok.demo.payload.JwtAuthenticationResponse;
import com.terok.demo.payload.LoginRequest;
import com.terok.demo.repositories.UsersRepository;
import com.terok.demo.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
    JwtTokenProvider tokenProvider;
	
	Logger logger = LogManager.getLogger();
	
	/*
	 * POST login user
	 */
	@RequestMapping(value="", method = RequestMethod.POST)
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest user) {
		
		logger.info("NAME IS " + user.getUsername());
		
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                		//user.userName,
                        //user.password
                )
        );
		
		logger.info(authentication.getName());
		logger.info(authentication.isAuthenticated());
		logger.info(authentication.getPrincipal().toString());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
        String jwt = tokenProvider.generateToken(authentication);
        //TODO Return token and username or just token
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user.getUsername()));
	}
	

}
