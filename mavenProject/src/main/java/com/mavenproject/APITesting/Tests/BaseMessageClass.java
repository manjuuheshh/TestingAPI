package com.mavenproject.APITesting.Tests;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BaseMessageClass {
	private Response response;
	
	String messageId;
	
	public String createMessage(DataMappingClass dmap, String api) {	
	response = RestAssured.given().contentType(ContentType.JSON)
						.body(dmap)
						.post(api);
	return response.getBody().jsonPath().getString("id");
	}
	
	 public int getStatusCode(){
	        return response.getStatusCode();
	 }
	 public String getStatusText(){
	        return response.getStatusLine();
	 }
	 
	 public String getResponseBodyUsingKey(String key){
	        JsonPath jsonPath = response.jsonPath();
	        return jsonPath.getString(key);
	    }
	 
	 public Response getMessage(String messageId) {
		 response = RestAssured.given().when()
	                .get(messageId);

		 return response;
	 }
	 
	 public void deleteMessage(String messageId) {
		 response = RestAssured.given().when().delete(messageId);
	 }
}
