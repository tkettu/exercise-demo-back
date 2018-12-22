package com.terok.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//@SpringBootTest
@ContextConfiguration
public class WithMockUserTests {

	Logger logger = LogManager.getLogger();
	
	
    //private MockMvc mockMvc;
	
	
	/*@Test
	public void signUpNewuser() throws Exception {
		this.mockMvc.perform(post("/api/user"))
	}*/
	
	@Test
	@WithMockUser("user")
	public void getExercises() {
		
	}
	
//	@Test
//	public void loginWithoutCredentials() throws Exception {
//		logger.info("LOGIN without Credentials");
//		this.mockMvc.perform(post("/api/login"))
//			.andExpect(status().isUnauthorized());
//	}
	
	
//	@Test
//	@WithMockUser
//	public void getExercisesWithMockUser() throws Exception {
//		
//		logger.info("TESTING MOCK");
//		this.mockMvc.perform(get("/api/exercises"))
//			.andExpect(status().isOk())
//			;
//		
//	}
}
