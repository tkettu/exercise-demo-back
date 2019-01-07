package com.terok.demo.models;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exercises")
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

	public Exercises() {this.id = ObjectId.get();}
	
	public Exercises(String owner) {
		this.id = ObjectId.get();
		this.owner = owner;
	}
	
	public Exercises(String owner, String sport, int hours, int minutes, double distance, int avgHeartRate,
			int maxHeartRate, String description, Date date, String season) {
		
		this.id = ObjectId.get();
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
	
	public void setId() {
		this.id = ObjectId.get();
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
//	@Override
//	public String toString() {
//		return String.format("%s %s, distance: %f, time: %d:%d, at %s "
//									, owner, sport, distance, hours, 
//									minutes, date.toString() );
//		
//	}
	
}
