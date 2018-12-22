package com.terok.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExercisesDemoApiApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
    WebApplicationContext applicationContext;

    @Autowired
    ObjectMapper objectMapper;
	
	
	@Before
	public void setup () {
		this.mockMvc = webAppContextSetup(this.applicationContext)
	            .apply(springSecurity())
	            .build();
	}

	
	@Test
	public void contextLoads() {
	}
	
	@Test 
	public void getExercisesWithoutAuthorization() throws Exception {
		this.mockMvc
			.perform(get("/api/exercises").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());
				
	}
	
//	@Test
//	@WithMockUser
//	public void postExerciseWithAuthorization() throws Exception {
//		
//		
//		
//		this.mockMvc
//		.perform(post("/api/exercises")
//				.content(this.objectMapper.writeValueAsBytes(value)))
//		.andExpect(status().isOk());
//	}
	
	@Test
	@WithMockUser
	public void getExercisesWithAuthorization() throws Exception {
		this.mockMvc
			.perform(get("/api/exercises").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
				
	}
	
	
}
