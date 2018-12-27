package com.terok.demo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.bson.types.ObjectId;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
			return ResponseEntity.badRequest().body("Username already taken ");
		}
		
		//user.set_id(ObjectId.get());
				
		String userName = user.username;
		user.setPassword(passwordEncoder.encode(user.password));
		usersRepository.save(user);
		// TODO HttpStatus
		// return response.getStatus();
		return ResponseEntity.ok(user);
	}

	//GET personal sport list
	@GetMapping("/{username}/sports")
	public ResponseEntity<?> getUserSports(@PathVariable String username) {
		
		
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (user == username) {
			List<String> userSports = usersRepository.findSportsByUsername(username);
			return ResponseEntity.ok(userSports);			
		}else {
			return new ResponseEntity(new ApiResponse(false, "Not allowed"), 
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/{username}/sports/{sport}")
	public ResponseEntity<?> addNewSport(@PathVariable String username, String sport) {
		
		String appUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (appUser == username) {
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
