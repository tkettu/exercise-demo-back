package com.terok.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terok.demo.repositories.ExerciseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExercisesDemoApiApplicationTests {

	private MockMvc mockMvc;
	
	Logger logger = LogManager.getLogger();
	
	@Autowired
    WebApplicationContext applicationContext;

    @Autowired
    ObjectMapper objectMapper;
	
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    private final static String EXERCISES_URL = "/api/exercises";
	
	@Before
	public void setup () {
		this.mockMvc = webAppContextSetup(this.applicationContext)
	            .apply(springSecurity())
	            .build();
		
				exerciseRepository.deleteAll();
				//exerciseRepository.save(exercise)
	}

	
	@Test
	public void contextLoads() {
	}
	
	@Test 
	public void getExercisesWithoutAuthorization() throws Exception {
		this.mockMvc
			.perform(get(EXERCISES_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());
				
	}
	
	@Test
	@WithMockUser
	public void postExerciseWithAuthorization() throws Exception {
		
		String content = "{ \"sport\": \"Juoksu\", \"distance\": 5, \"hours\": 1, "
				+ "\"minutes\": 5, "
				+ "	\"description\": \"Testi\", \"season\":  \"kesä18\"}";
		
		logger.info(exerciseRepository.count());
		
		MvcResult result = this.mockMvc
			
			.perform(post(EXERCISES_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content))
			
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		logger.info(result);
		logger.info(exerciseRepository.count());
	}
	
	@Test
	@WithMockUser
	public void getExercisesWithAuthorization() throws Exception {
		this.mockMvc
			.perform(get(EXERCISES_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
				
	}
	
	@After
	public void cleanUp() {
		logger.info("CLEANING");
		exerciseRepository.deleteAll();
		//TODO clean test database
	}
	
	
}
