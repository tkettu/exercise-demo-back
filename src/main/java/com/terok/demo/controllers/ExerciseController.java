package com.terok.demo.controllers;

import java.util.List;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.terok.demo.models.Exercises;
import com.terok.demo.repositories.ExerciseRepository;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	Logger logger = LogManager.getLogger();
	
//	@RequestMapping(value="", method=RequestMethod.GET)
//	public String getExercise() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		logger.info(auth.getName());
//		logger.info(auth.getPrincipal());
//		return "HELLO ROKI";
//	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<?> addExercise(@RequestBody Exercises exercise){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		exercise.owner = auth.getName();
		exercise.id = ObjectId.get();
		
		Date date = new Date();
		
		//Set date today if null
		exercise.date = (exercise.date == null) 
				? (new Date()) : (exercise.date);
		logger.info(String.format("Saving to %s", exercise.owner));
		exerciseRepository.save(exercise);
		
		return ResponseEntity.ok("Added");
		
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<?> getExercises(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String owner = auth.getName();
		//TODO findByOwner
		List<Exercises> exercises = exerciseRepository.findByOwner(owner);
		
		return ResponseEntity.ok(exercises);
	}
	
	//TODO PUT Muokkaa by id, varmista omistajuus
	//TODO GET by id, varmista omistajuus
	
}
