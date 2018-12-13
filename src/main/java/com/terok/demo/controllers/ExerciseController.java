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
import com.terok.demo.payload.ExerciseRequest;
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
	//public ResponseEntity<?> addExercise(@RequestBody ExerciseRequest exerciseRequest) {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//TODO luodako servicessa uusi exerciseRequestista vai näin kuin nyt
		//exerciseRequest.setOwner(auth.getName());
		
//		Date date = new Date();
//		
//		Exercises exercise = new Exercises(ObjectId.get(), 
//				auth.getName(),
//				exerciseRequest.getSport(),
//				exerciseRequest.getHours(),
//				exerciseRequest.getMinutes(),
//				exerciseRequest.getDistance(),
//				exerciseRequest.getAvgHeartRate(),
//				exerciseRequest.getMaxHeartRate(),
//				exerciseRequest.getDescription(),
//				e);
		
		exercise.owner = auth.getName();
		exercise.setId(ObjectId.get());
		
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
	
	@GetMapping("/{id}")
	ResponseEntity<?> getOneExercise(@PathVariable ObjectId id) {
		//TODO Check ownership??
		
		Exercises exercise = exerciseRepository.findExerciseById(id);
		
		if(checkOwnerShip(exercise)) {
			return ResponseEntity.ok(exercise);
		} else {
			return new ResponseEntity(new ApiResponse(false, "Not allowed"), 
					HttpStatus.BAD_REQUEST);
		}
		//return ResponseEntity.ok(exerciseRepository.findById(id));
		
	}
	
	@PutMapping("/{id}")
	ResponseEntity<?> modifyExercise(@PathVariable ObjectId id, 
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
	ResponseEntity<?> deleteExercise(@PathVariable ObjectId id) {
		
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
