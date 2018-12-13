package com.terok.demo.models;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Exercises {
	
	@Id
	private ObjectId id;
	
	//TODO kumpi id, vai username
	//Viittaus Useriin
	//public ObjectId owner;
	public String owner;
	

	public String sport;

	public int hours;
	public int minutes;
	
	public double distance;
	
	public int avgHeartRate;
	public int maxHeartRate;
	
	public String description;
	public Date date;
	
	public String season; //

	public Exercises(ObjectId id, String owner, String sport, int hours, int minutes, double distance, int avgHeartRate,
			int maxHeartRate, String description, Date date, String season) {
		super();
		this.id = id;
		this.owner = owner;
		this.sport = sport;
		this.hours = hours;
		this.minutes = minutes;
		this.distance = distance;
		this.avgHeartRate = avgHeartRate;
		this.maxHeartRate = maxHeartRate;
		this.description = description;
		this.date = date;
		this.season = season;
	}
	
	public String getId() {
		return id.toHexString();
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
}
