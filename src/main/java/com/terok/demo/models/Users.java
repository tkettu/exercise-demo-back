package com.terok.demo.models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation.EmptyArraysBuilder;

public class Users {
	
	@Id
	public ObjectId _id;
	public String userName;
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
	
	
	public Users(ObjectId _id, String userName, String password) {
		this._id = _id;
		this.userName = userName;
		this.password = password;
		
		
	}

	public String get_id() {
		return _id.toHexString();
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
