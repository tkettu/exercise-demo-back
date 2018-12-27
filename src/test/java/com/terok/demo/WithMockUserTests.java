package com.terok.demo;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.terok.demo.models.Users;
import com.terok.demo.repositories.UsersRepository;

import javax.annotation.Resource;;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class WithMockUserTests {

	Logger logger = LogManager.getLogger();
	
	@Autowired
	WebApplicationContext applicationContext;
	
	//@MockBean 
	//UsersRepository usersRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
    private MockMvc mockMvc;
	
	private final static String USER_URL = "/api/user";
    private final static String LOGIN_URL = "/api/login";
	
    private String newUser = 
    		"{\"username\": \"testiUser\", "
    		+ "\"password\": \"veriS3kret\", "
    		+ "\"email\": \"testi@user.com\"}";
    
    private String falseUser = "{\"username\": \"criminal\", "
    		+ "\"password\": \"123456\"}";
    
    private String trueUser = "{\"username\": \"user\", "
    		+ "\"password\": \"password\"}";
    		
    
    
    @Before
	public void setup () {
		this.mockMvc = webAppContextSetup(this.applicationContext)
	            .apply(springSecurity())
	            .build();
		
    }
    
	@Test
	public void signUpNewuser() throws Exception {
		
		
		
		usersRepository.deleteById(usersRepository
					.findByUsername("testiUser")._id);
		
		
		
		this.mockMvc
			.perform(post(USER_URL + "/registration")
					.contentType(MediaType.APPLICATION_JSON)
					.content(newUser))
			.andExpect(status().isOk());
	}
	
	@Test
	public void loginWithWrongCredentials() throws Exception {
		
		this.mockMvc
			.perform(post(LOGIN_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(falseUser))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	//@WithMockUser
	public void loginAsUser() throws Exception {
	
		if (usersRepository.findByUsername("testiUser") != null) {
			this.mockMvc
			.perform(post(LOGIN_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(newUser))
			.andExpect(status().isOk());			
		}
	}
	
}
