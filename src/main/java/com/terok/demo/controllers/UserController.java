package com.terok.demo.controllers;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.terok.demo.models.Users;
import com.terok.demo.payload.ApiResponse;
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
	public ResponseEntity<?> addUser(@RequestBody Users user) {
		
		logger.info(user);
		logger.info(String.format("Adding %s", user.username));
		
		if(usersRepository.findByUsername(user.username) != null) {
			logger.info("USERNAME TAKEN");
			return ResponseEntity.badRequest().body("Username already taken ");
		}
		logger.info("TASSA");
		user.setPassword(passwordEncoder.encode(user.password));
		user.setId();
		usersRepository.save(user);
		
		return ResponseEntity.ok(user);
	}

	//GET personal sport list
	@GetMapping("/{username}/sports")
	public ResponseEntity<?> getUserSports(@PathVariable String username) {
		
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info(user);
		logger.info(username);
		
		if (user.equals(username)) {
			//List<String> userSports = usersRepository.findSportsByUsername(username);
			Users users = usersRepository.findByUsername(username);
			List<String> userSports = users.sports;
			return ResponseEntity.ok(userSports);			
		}else {
			return new ResponseEntity(new ApiResponse(false, "Not allowed"), 
					HttpStatus.BAD_REQUEST);
		}
	}
	
	//GET default sport list
	@GetMapping("/sports")
	public ResponseEntity<?> getDefaultSports() {
		
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (user != null) {
			
			List<String> sports = Arrays.asList("Juoksu", "Hiihto", "Kävely", "Pyöräily");
			logger.info("SPORTIT ON " + sports);
			return ResponseEntity.ok(sports);
		}else {
			return new ResponseEntity(new ApiResponse(false, "Not allowed"), 
					HttpStatus.BAD_REQUEST);
		}
	}
	
	//POST new sport to personal list
	@PostMapping("/{username}/sports/{sport}")
	public ResponseEntity<?> addNewSport(@PathVariable String username, String sport) {
		
		String appUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (appUser.equals(username)) {
			Users user = usersRepository.findByUsername(username);
			user.sports.add(sport);
			usersRepository.save(user);
			
			return ResponseEntity.ok("ADDED new sport");
		}else {
			return new ResponseEntity(new ApiResponse(false, "Not allowed"), 
					HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
