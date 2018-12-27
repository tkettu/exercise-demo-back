package com.terok.demo.models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Email;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation.EmptyArraysBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;

@Document(collection = "users")
public class Users {
	
	@Id
	public ObjectId _id;
	
	
	public String username;
	
	
	@Email
	public String email;

	
	public String password;
	

	private Set<Role> roles = new HashSet<>();
	
	public List<String> exercises = new ArrayList<>();
	
	public List<String> seasons = new ArrayList<>();
	
	public List<String> sports = new ArrayList<>();
	//
	//public Map<String, String> sports = new HashMap<String, String>();

	//public Set<String> sports = 
	//		 .asList( {"Juoksu", "Hiihto", "Pyöräily", "Kävely"} );
	
	//TODO Add Some default sports and user own
	//private Set<String> sports = new HashSet<>();
	
	
	public Users(ObjectId _id, String username, String password) {
		this._id = _id;
		this.username = username;
		this.password = password;
		
		
	}

	public String get_id() {
		return _id.toHexString();
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
		
}
