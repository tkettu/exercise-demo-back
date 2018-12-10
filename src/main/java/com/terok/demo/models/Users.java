package com.terok.demo.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Users {
	
	@Id
	public ObjectId _id;
	public String userName;
	public String email;

	public String password;
	
	
	public Users(ObjectId _id, String userName, String password) {
		this._id = _id;
		this.userName = userName;
		this.password = password;
	}

	public ObjectId get_id() {
		return _id;
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
	
	
	
	
}
