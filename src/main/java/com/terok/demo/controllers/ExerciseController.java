package com.terok.demo.controllers;

import java.util.List;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.terok.demo.models.Exercises;
import com.terok.demo.payload.ApiResponse;
import com.terok.demo.repositories.ExerciseRepository;
import com.terok.demo.repositories.UsersRepository;

/**
 * @author tero
 *
 */
@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Autowired 
	UsersRepository usersRepository;
	
	Logger logger = LogManager.getLogger();

	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<?> addExercise(@RequestBody Exercises exercise){
	//public ResponseEntity<?> addExercise(@RequestBody ExerciseRequest exerciseRequest) {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//TODO luodako servicessa uusi exerciseRequestista vai näin kuin nyt
		//exerciseRequest.setOwner(auth.getName());
		
		String userName = auth.getName();
		
		
		exercise.owner = userName;
		exercise.setId();
		
		
		//Set date today if null
		exercise.date = (exercise.date == null) 
				? (new Date()) : (exercise.date);
		logger.info(String.format("Saving to %s", exercise.owner));
		
		// We could save ids to users, but it may be not necessary
		// and could cause extra complexity
		//Users user = usersRepository.findByUserName(userName);
		//logger.info(user);
		//user.exercises.add(exercise.getId());
		//usersRepository.save(user);
		
		//Save exercise and user
		exerciseRepository.save(exercise);
		logger.info("ADDED " + exercise.sport);
		return ResponseEntity.ok(exercise);
		
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Exercises>> getExercises(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String owner = auth.getName();
		
		logger.info("For user " + owner);
		//TODO findByOwner
		logger.info(exerciseRepository.count());
		
		List<Exercises> exercises = exerciseRepository.findByOwner(owner);
		//logger.info("PALAUTETAAN " + exercises);
		
		//return ResponseEntity.ok(exercises);
		return new ResponseEntity<List<Exercises>>(exercises, HttpStatus.OK );
	}
	
	//GET /exercises/sport/{sport}
	@GetMapping("/sport/{sport}")
	public ResponseEntity<?> getExercisesBySport(@PathVariable String sport) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String owner = auth.getName();
		
		List<Exercises> exercises = exerciseRepository.findByOwnerAndSport(owner, sport);
		
		logger.info(exercises);
		
		return ResponseEntity.ok(exercises);
		
	}
	
	//GET exercises by sport and season
	@GetMapping("/sport/{sport}/{season}")
	public ResponseEntity<?> getExercisesBySport(@PathVariable String sport, String season) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String owner = auth.getName();
		
		List<Exercises> exercises = exerciseRepository.findByOwnerAndSportAndSeason(owner, sport, season);
		
		logger.info(exercises);
		
		return ResponseEntity.ok(exercises);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOneExercise(@PathVariable ObjectId id) {
		//TODO Check ownership??
		
		Exercises exercise = exerciseRepository.findExerciseById(id);
		
		if(checkOwnerShip(exercise)) {
			return ResponseEntity.ok(exercise);
		} else {
			return new ResponseEntity(new ApiResponse(false, "Not allowed"), 
					HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modifyExercise(@PathVariable ObjectId id, 
			@RequestBody Exercises newExercise) {
		
		//Exercises exercise = exerciseRepository.findExerciseById(id);
		Exercises exercise = exerciseRepository.findExerciseById(id);
		
		if(checkOwnerShip(exercise)) {
			newExercise.setId(id);
			newExercise.owner = exercise.owner;
			//exerciseRepository.save(exerciseRepository.findExerciseById(id));
			exerciseRepository.save(newExercise);
			return ResponseEntity.ok(newExercise);
		} else {
			return new ResponseEntity(new ApiResponse(false, "Not allowed"), 
					HttpStatus.BAD_REQUEST);
		}
	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteExercise(@PathVariable ObjectId id) {
		
		Exercises exercise = exerciseRepository.findExerciseById(id);
		String name = exercise.sport;
		if (checkOwnerShip(exercise)) {
			exerciseRepository.delete(exercise);
			return new ResponseEntity(new ApiResponse(true, 
					String.format("Deleted %s ", name)), 
					HttpStatus.OK);
		} else 
			return new ResponseEntity(new ApiResponse(false, "Not allowed"), 
				HttpStatus.BAD_REQUEST);
	}
	
	
	
	private boolean checkOwnerShip(Exercises exercise) {
		return SecurityContextHolder.getContext().getAuthentication()
				.getName().equals(exercise.owner);
	
	}
}
