package com.terok.demo;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.terok.demo.models.Users;
import com.terok.demo.repositories.UsersRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTests {

	Logger logger = LogManager.getLogger();
	
	@Autowired
	WebApplicationContext applicationContext;
	
	@MockBean 
	UsersRepository usersRepository;
	
	private MockMvc mockMvc;
	
	private final static String USER_URL = "/api/user";
	
	private List<String> sports = new ArrayList<>();
	
	 private String newUser = 
	    		"{\"username\": \"testiUser\", "
	    		+ "\"password\": \"veriS3kret\", "
	    		+ "\"email\": \"testi@user.com\"}";
	 
	 //private Users user = new Users()
	
	@Before
	public void setup () {
		this.mockMvc = webAppContextSetup(this.applicationContext)
				.apply(springSecurity())
				//.apply(documentationConfiguration(this.restDocumentation))
				.build();		
		
	}
	 
	@Test
	public void shouldAddNewUser() throws Exception {
		when(usersRepository.findByUsername("testiUser")).thenReturn(null);
		
		MvcResult result = this.mockMvc
			.perform(post(USER_URL + "/registration")
					.contentType(MediaType.APPLICATION_JSON)
					.content(newUser))
			.andExpect(status().isOk())
			.andExpect(content().json("{'username': 'testiUser'}"))
			.andDo(print())
			.andReturn();
			//.andDo(document("user"))
		
		
	}
	
	@Test
	@WithMockUser
	public void shouldReturnDefaultSportList() throws Exception{
		
//		sports.add("Juoksu");
//		sports.add("Hiihto");
//		sports.add("Kävely");
		
		//when(usersRepository.findSportsByUsername("user")).thenReturn(sports);
		//when(usersRepository.findByUsername("user")).thenReturn(sports);
		MvcResult result = this.mockMvc
				.perform(get(USER_URL + "/sports"))
				.andExpect(status().isOk())
				//.andExpect(content().json(jsonContent))
				.andDo(print())
				.andReturn();
	}
	
	@Test
	public void shouldAddNewSport() {
		
	}
}
