package com.terok.demo;

import static org.mockito.Mockito.mockitoSession;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mozilla.javascript.ast.NewExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.terok.demo.models.Exercises;
import com.terok.demo.repositories.ExerciseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@DataMongoTest
public class ExerciseRepositoryTest {

	Logger logger = LogManager.getLogger();
	
	//@Autowired
	@MockBean
	private ExerciseRepository exerciseRepository;
	
	@Autowired
	WebApplicationContext applicationContext;
	
	private MockMvc mockMvc;
	
	private final static String EXERCISES_URL = "/api/exercises";
	
	private String exerciseId;
	
	private Exercises exercise;
	
	private List<ObjectId> ids = new ArrayList<>();

	private String content = "{ \"sport\": \"Juoksu\", \"distance\": 5, \"hours\": 1, "
			+ "\"minutes\": 5, "
			+ "	\"description\": \"Testi\", \"season\":  \"kesä18\"}";
	
	@Before
	public void setup () {
		this.mockMvc = webAppContextSetup(this.applicationContext)
	            .apply(springSecurity())
	            
	            .build();
		
		Exercises exercise = new Exercises("user");
		exercise.setId();
		
		
		exerciseRepository.save(exercise);
				
	}
	
	@Test
	@WithMockUser
	public void postExerciseWithAuthorization() throws Exception {
		
		
		
		logger.info(exerciseRepository.count());
		MvcResult result = this.mockMvc
			
			.perform(post(EXERCISES_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content))
			
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(content))
			.andDo(print())
			.andReturn();
		
		logger.info(result.getResponse());
		
		logger.info(exerciseRepository.count());
	}
	
	@Test
	@WithMockUser
	public void getExercisesWithAuthorization() throws Exception {
		
		
		
	
		MvcResult result = this.mockMvc
			.perform(get(EXERCISES_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		
		
		logger.info(result.getResponse());
		//result.
		logger.info(exerciseRepository.count());
		logger.info("-------------ENDGETEXERCISESWITHAUTHORIZATION---------------");
		//count result, expect +1 ennen inserttia
	}
	
//	@Test
//	@WithMockUser
//	public void getNonEmptyExerciseList() {
//		Mockito.whe
//	}
	
//	@Test
//	@WithMockUser
//	public void deleteExerciseById
	
	
//	@Test
//	@WithMockUser
//	public void getExerciseById
	
	@After
	public void cleanUp() {
		
//		for (ObjectId i : ids) {
//			exerciseRepository.deleteById(i);
//		}
	}
}
