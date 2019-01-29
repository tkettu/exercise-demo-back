package com.terok.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import com.terok.demo.controllers.ExerciseController;
import com.terok.demo.models.Exercises;
import com.terok.demo.repositories.ExerciseRepository;
import com.terok.demo.repositories.UsersRepository;

public class ExerciseControllerUnitTest {
	
	Logger logger = LogManager.getLogger();
	
	@InjectMocks
	private ExerciseController exerciseController;
	
	@Mock
	private ExerciseRepository exerciseRepository;
	
	@Mock
	private UsersRepository usersRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	@WithMockUser
	public void testGetExerciseById() {
		Exercises exercise = new Exercises();

		ObjectId id = ObjectId.get();
		exercise.setId(id);
		exercise.owner = "user";
		when(exerciseRepository.findExerciseById(id)).thenReturn(exercise);
		
		ResponseEntity<?> response = exerciseController.getOneExercise(id);
		logger.info(response);
		verify(exerciseRepository).findExerciseById(id);
		assertEquals(1,1);
		
	}

}
