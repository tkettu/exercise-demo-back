package com.terok.demo.models;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Exercises {
	
	@Id
	public ObjectId id;
	
	//TODO kumpi id, vai username
	//Viittaus Useriin
	//public ObjectId owner;
	public String owner;
	
	public String sport;

	public int hours;
	public int minutes;
	
	public double distance;
	
	public Date date;
	
	
}
