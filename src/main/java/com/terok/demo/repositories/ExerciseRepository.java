package com.terok.demo.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.terok.demo.models.Exercises;

public interface ExerciseRepository extends MongoRepository<Exercises, ObjectId> {
	Exercises findExerciseById(ObjectId id);
	
	List<Exercises> findByOwner(String owner);
	
	List<Exercises> findByOwnerAndSport(String owner, String sport);

	List<Exercises> findByOwnerAndSportAndSeason(String owner, String sport, String season);

	
	// Paging and sorting --> https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
}
