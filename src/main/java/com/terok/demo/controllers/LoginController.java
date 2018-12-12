package com.terok.demo.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mongodb.util.JSON;
import com.terok.demo.models.Users;
import com.terok.demo.payload.JwtAuthenticationResponse;
import com.terok.demo.payload.LoginRequest;
import com.terok.demo.repositories.UsersRepository;
import com.terok.demo.security.JwtTokenProvider;
import com.terok.demo.security.UserPrincipal;

@RestController
@RequestMapping("/api/login")
public class LoginController {
//	
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
		
		//TODO TOKEN SETTING
        String jwt = tokenProvider.generateToken(authentication);
        logger.info("LOGIN JWT " + jwt);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
		//return ResponseEntity.ok(new JwtAuthenticationResponse(authentication.getName() +"FALSETOKEN"));
//		logger.info("Login as " +  user.userName);
//		Users user2 = usersRepository.findByUserName(user.userName); 
//		if(user != null) {
//			if(passwordEncoder.matches(user.password, user2.getPassword())){
////				UsernamePasswordAuthenticationToken authReq
////				 = new UsernamePasswordAuthenticationToken(user.userName, user.password);
////				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////				auth.setAuthenticated(true);
//				
//				//authReq.setAuthenticated(true);
//				//SecurityContext sc = SecurityContextHolder.getContext();
//				//securityContext.setAuthentication(auth);
//				return "TOKEN"; //TODO token
//			}
			
		//}
		
		//return "Username or password wrong " + HttpStatus.UNAUTHORIZED.toString();
	}
	

}
