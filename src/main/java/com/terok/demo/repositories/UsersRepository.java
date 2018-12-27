package com.terok.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.terok.demo.models.Users;

public interface UsersRepository extends MongoRepository<Users, ObjectId> {
	Users findByUsername(String username);
	Optional<Users> findById(ObjectId id);
	
	List<String> findSportsByUsername(String username);
	//Users findById(ObjectId _id);
	//Users findUsers(ObjectId id);
	
	
}
