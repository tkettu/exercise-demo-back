package com.terok.demo.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.terok.demo.models.Users;

public interface UsersRepository extends MongoRepository<Users, ObjectId> {
	Users findByUserName(String username);
	Optional<Users> findById(ObjectId id);
	//Users findById(ObjectId _id);
	//Users findUsers(ObjectId id);
	
	
}
