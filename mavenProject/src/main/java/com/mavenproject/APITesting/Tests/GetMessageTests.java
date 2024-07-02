package com.mavenproject.APITesting.Tests;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class GetMessageTests {
	BaseMessageClass msgclass = new BaseMessageClass();
	DataMappingClass dmap;
	From fro = new From("user1");
	To t1 = new To("user2");
	
	@BeforeTest
    public static void setup() {
		RestAssured.basePath = "http://localhost:3000/api/messages/";
        
    }	
	
	@Test(description="Verifying that API able to GET all messages in the endpoint irrespective of the user")
	public void Verify_that_API_fetches_all_messages_from_messages_enpoint() {
		dmap = new DataMappingClass("hello", fro, t1);
		msgclass.getMessage(RestAssured.basePath);
		System.out.println(msgclass.getMessage(RestAssured.basePath));
		Assert.assertEquals(msgclass.getStatusCode(),200);
	}
	
	@Test(description="Verifying that API able to GET message for a particular message id")
	public void Verify_that_API_fetches_message_for_a_particular_message_id() {
		dmap = new DataMappingClass("hello", fro, t1);
		String messageId = msgclass.createMessage(dmap,RestAssured.basePath );
		String messageEndpoint = RestAssured.basePath+messageId;
		msgclass.getMessage(messageEndpoint);
		Assert.assertEquals(msgclass.getStatusCode(),200);
		Assert.assertNotNull(msgclass.getResponseBodyUsingKey("id"));
		Assert.assertEquals(messageId, msgclass.getResponseBodyUsingKey("id"));
		Assert.assertNotNull(msgclass.getResponseBodyUsingKey("message"));
		Assert.assertEquals("hello", msgclass.getResponseBodyUsingKey("message"));
		Assert.assertEquals("user1", msgclass.getResponseBodyUsingKey("from.id"));
		Assert.assertEquals("user2", msgclass.getResponseBodyUsingKey("to.id"));
	}
	
	@Test(description="Verifying that API returns 404 error code for invalid message id")
	public void Verify_that_API_returns_error_for_invalid_message_id() {
		String messageId = "1e2ee6d9";
		String messageEndpoint = RestAssured.basePath+messageId;
		msgclass.getMessage(messageEndpoint);
		Assert.assertEquals(msgclass.getStatusCode(),404);
	}
	
	@Test(description="Verifying that API returns 404 error code for null message id")
	public void Verify_that_API_returns_error_for_null_message_id() {
		String messageId = null;
		String messageEndpoint = RestAssured.basePath+messageId;
		msgclass.getMessage(messageEndpoint);
		Assert.assertEquals(msgclass.getStatusCode(),404);
	}
	
	@Test(description="Verifying that generated message ids are unique")
	public void Verify_that_API_generated_message_ids_are_unique() {
		msgclass.getMessage(RestAssured.basePath);
		System.out.println(msgclass.getMessage(RestAssured.basePath).body().jsonPath().prettify());
		System.out.println(msgclass.getResponseBodyUsingKey("id"));
		String ids = msgclass.getResponseBodyUsingKey("id");
		String idArray[]=  ids.split(", ");;
		
		String message[] = msgclass.getResponseBodyUsingKey("message").split(", ");

		HashMap<String, String> mp = new HashMap<>();
		 for (int i = 0; i < idArray.length; i++) { 	      
			 mp.put(idArray[i], message[i]); 
	        } 
		 System.out.println("String to hashmap: " + mp); 
		
		 HashMap<String, Integer> frequency_map = new HashMap<>();
	        for(int i = 0; i < idArray.length; i++){
	            String messageId = idArray[i];
	            System.out.println("for i = "+i+" & messageId :" +messageId );
	            if(frequency_map.containsKey(messageId) == true){
	                int old_freq = frequency_map.get(messageId);
	                System.out.println(old_freq);
	                int new_freq = old_freq + 1;
	                System.out.println(new_freq);
	                frequency_map.put(messageId, new_freq);
	            } else {
	                frequency_map.put(messageId, 1);
	            }
	        }

	        Set<String> keys = frequency_map.keySet();
	        String message2 =idArray[0];
	        for(String key : keys){
	            if(frequency_map.get(key) > frequency_map.get(message2)){
	            	message2 = key;
	            }
	        }
	        System.out.println(message2);
	}
}