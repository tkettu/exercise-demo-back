package com.terok.demo;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.terok.demo.controllers.ExerciseController;
import com.terok.demo.models.Exercises;
import com.terok.demo.repositories.ExerciseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ExerciseRepositoryTest {

	Logger logger = LogManager.getLogger();
	
	//TODO use test repository, add and remove one exercise
	@MockBean
	//@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Autowired
	WebApplicationContext applicationContext;
	
	private MockMvc mockMvc;
	
	private final static String EXERCISES_URL = "/api/exercises";
	
	private String exerciseId;
	
	private Exercises exercise;
	
	private List<ObjectId> ids = new ArrayList<>();

	private String newExercise = "{ \"sport\": \"Juoksu\", \"distance\": 5, \"hours\": 1, "
			+ "\"minutes\": 5, "
			+ "	\"description\": \"Testi\", \"season\":  \"kesä18\"}";
	
	 @Rule
	 public JUnitRestDocumentation restDocumentation =
	    	 new JUnitRestDocumentation("target/generated-snippets");

	
	//https://docs.spring.io/spring-restdocs/docs/1.1.1.RELEASE/reference/html5/#customizing-requests-and-responses
	@Before
	public void setup () {
//		
//		this.documentationHandler = document("{method-name}", 
//				preprocessRequest(removeHeaders("Foo")),
//				preprocessResponse(prettyPrint()));
		
		this.mockMvc = webAppContextSetup(this.applicationContext)
	            .apply(springSecurity())
	            .apply(documentationConfiguration(this.restDocumentation))
	            .build();
		
//		Exercises exercise = new Exercises("user");
//		exercise.setId();
//		
//		exerciseRepository.save(exercise);
				
	}
	
	@Test
	@WithMockUser
	public void shouldAddExerciseWithAuthorization() throws Exception {
		
		//logger.info(exerciseRepository.count());
		MvcResult result = this.mockMvc
			
			.perform(post(EXERCISES_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(newExercise))
			
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(newExercise))
			.andDo(print())
			.andDo(document("newexercise"))
			.andReturn();
		
		//logger.info(result.getResponse());
	}
	
	@Test
	@WithMockUser
	public void shouldReturnExercisesWithAuthorization() throws Exception {
		
		MvcResult result = this.mockMvc
			.perform(get(EXERCISES_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("exercises"))
			.andReturn();
		
		//logger.info(result.getResponse());
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
