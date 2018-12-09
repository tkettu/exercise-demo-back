package com.terok.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getExercise() {
		return "HELLO ROKI";
	}
}
