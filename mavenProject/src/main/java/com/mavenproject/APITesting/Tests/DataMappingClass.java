package com.mavenproject.APITesting.Tests;

import io.restassured.path.json.JsonPath;

public class DataMappingClass {
	From from;
    To to;
    String message;
    
    public DataMappingClass(String message, From from, To to) {
    	this.message = message;
    	this.from = from;
    	this.to = to;
    }
	 public From getFrom() {
		return from;
	}
	public void setFrom(From from) {
		this.from = from;
	}
	public To getTo() {
		return to;
	}
	public void setTo(To to) {
		this.to = to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
