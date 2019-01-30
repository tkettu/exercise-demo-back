package com.terok.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	
	@InjectMocks
	private ExerciseController exerciseController;
	
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
	private List<Exercises> exercises = new ArrayList<>();
	private List<Exercises> allExercises = new ArrayList<>();
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
		
//		exercises.add(new Exercises("user", "Juoksu", 1, 12, 13, 0,0,"",null,""));
//		exercises.add(new Exercises("user", "Hiihto", 2, 40, 30, 0,0,"",null,""));
//		exercises.add(new Exercises("eriUser", "Juoksu", 1, 12, 13, 0,0,"",null,""));
		initExerciseRepo();
//		exerciseRepository.save(new Exercises("user", "Juoksu", 1, 12, 13, 0,0,"",null,""));
//		exerciseRepository.save(new Exercises("user", "Hiihto", 2, 40, 30, 0,0,"",null,""));
//		exerciseRepository.save(new Exercises("eriUser", "Juoksu", 1, 12, 13, 0,0,"",null,""));
		
//		for (Exercises e : exercises) {
//			exerciseRepository.save(e);
//		}
		
		
		logger.info(exercises);
		logger.info(exerciseRepository.count());
		logger.info(exerciseRepository.findAll());
//		Exercises exercise = new Exercises("user");
//		exercise.setId();
//		
//		exerciseRepository.save(exercise);
				
	}
	
	private void initExerciseRepo() {
		Exercises e1 = new Exercises("user");
		e1.sport = "Juoksu";
		exerciseRepository.save(e1);
		exercises.add(e1);
		allExercises.add(e1);
		Exercises e2 = new Exercises();
		e2.owner = "user";
		e2.sport = "Hiihto";
		exerciseRepository.save(e2);
		exercises.add(e2);
		allExercises.add(e2);
		Exercises e3 = new Exercises("eriuser");
		e3.sport = "Juoksu";
		exerciseRepository.save(e3);
		allExercises.add(e3);
		when(exerciseRepository.findByOwner("user")).thenReturn(exercises);
		when(exerciseRepository.findAll()).thenReturn(allExercises);
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
		
		logger.info(result.getResponse());
	}
	
	@Test
	@WithMockUser
	public void shouldReturnExercisesWithAuthorization() throws Exception {
		
//		Exercises newExercise = new Exercises("user");
//		newExercise.setId();
//		newExercise.sport = "Juoksu";
//		exerciseRepository.save(newExercise);

//		exerciseRepository.save(new Exercises("user", "Juoksu", 1, 12, 13, 0,0,"",null,""));
//		exerciseRepository.save(new Exercises("user", "Hiihto", 2, 40, 30, 0,0,"",null,""));
//		exerciseRepository.save(new Exercises("eriUser", "Juoksu", 1, 12, 13, 0,0,"",null,""));
		MvcResult result = this.mockMvc
			.perform(get(EXERCISES_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("exercises"))
			.andReturn();
		
		//logger.info("RESULT" + result.getResponse().getContentAsString().split("\\},\\{"));
		//assertEquals(result.getResponse()., actual);
		//assertEquals(2, result.getResponse().getContentAsString().split("\\},\\{"));
	}
	
//	@Test
//	@WithMockUser
//	public void getNonEmptyExerciseList() {
//		Mockito.whe
//	}
	
//	@Test
//	@WithMockUser
//	public void deleteExerciseById
	
	
	@Test
	@WithMockUser
	public void getExerciseById() throws Exception {
		Exercises exercise = new Exercises();

		ObjectId id = ObjectId.get();
		exercise.setId(id);
		exercise.owner = "user";
		exercise.sport = "Juoksu";
		when(exerciseRepository.findExerciseById(id)).thenReturn(exercise);
		
		MvcResult result = this.mockMvc
				.perform(get(EXERCISES_URL+'/' +id))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("Juoksu"));
		//logger.info("RESULLTI ON " + result.getResponse().getContentAsString().contains("Juoksu"));
//		ResponseEntity<?> response = exerciseController.getOneExercise(id);
//		
//		logger.info(response);
//		verify(exerciseRepository).findExerciseById(id);
//		assertEquals(1,1);
	}
	
	@Test
	@WithMockUser
	public void shouldNotReturnExerciseByDiffUser() throws Exception {
		Exercises exercise = new Exercises();

		ObjectId id = ObjectId.get();
		exercise.setId(id);
		exercise.owner = "eriOwner";
		exercise.sport = "Juoksu";
		when(exerciseRepository.findExerciseById(id)).thenReturn(exercise);
		
		MvcResult result = this.mockMvc
				.perform(get(EXERCISES_URL+'/' +id))
				.andExpect(status().isBadRequest())
				.andDo(print())
				.andReturn();
	}
	
	@Test
	@WithMockUser
	public void shouldReturnExercisesBySport() throws Exception {
		//TODO 
		
	}
	
	@After
	public void cleanUp() {
		
//		for (ObjectId i : ids) {
//			exerciseRepository.deleteById(i);
//		}
	}
}
