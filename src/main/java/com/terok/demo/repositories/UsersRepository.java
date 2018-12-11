package com.terok.demo.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.terok.demo.models.Users;

public interface UsersRepository extends MongoRepository<Users, String> {
	Users findByUserName(String username);
	
	//Users findUsers(ObjectId id);
	
	
}
