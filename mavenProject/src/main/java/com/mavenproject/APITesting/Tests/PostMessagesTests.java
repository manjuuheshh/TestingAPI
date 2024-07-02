package com.mavenproject.APITesting.Tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import junit.framework.Assert;

public class PostMessagesTests {
	BaseMessageClass msgclass = new BaseMessageClass();
	int idLength = 36;
	From fro = new From("user1");
	To t1 = new To("user2");
	From emptyfrom = new From("");
	To emptyTo = new To("");
	String messageString;
	DataMappingClass dmap; 
	
	@BeforeTest
    public static void setup() {
        RestAssured.baseURI = "http://localhost:3000/api/messages";
    }	
	
	@Test(description="verify api works for valid message from User1 to User2")
	public void sendMessage(){
		dmap = new DataMappingClass("hello", fro, t1);
		msgclass.createMessage(dmap, RestAssured.baseURI );
		Assert.assertEquals(msgclass.getStatusCode(), 200);
		String id = msgclass.getResponseBodyUsingKey("id");
		System.out.println(id);
		Assert.assertNotNull(id);
		Assert.assertEquals(id.length(), idLength);		
	}
	
	@Test(description="verify api works for Sending empty message from User1 to User2")
	public void sendEmptyMessage(){
		dmap = new DataMappingClass("", fro, t1);
		msgclass.createMessage(dmap, RestAssured.baseURI );
		Assert.assertEquals(msgclass.getStatusCode(), 200);
		String id = msgclass.getResponseBodyUsingKey("id");
		System.out.println(id);
		Assert.assertNotNull(id);
		Assert.assertEquals(id.length(), idLength);	
	}
	
	@Test(description="verify api should return error when Sending message without sender and receiver id")
	public void sendMessageWithNoSenderAndReceiver(){
		dmap = new DataMappingClass("hi",null,null);
		msgclass.createMessage(dmap, RestAssured.baseURI );
		Assert.assertEquals(msgclass.getStatusCode(), 400);
		String errorMessage = msgclass.getResponseBodyUsingKey("error.name");
		Assert.assertEquals("ServiceError", errorMessage);	
	}
	
	@Test(description="verify api works for Sending message with empty sender and receiver id")
	public void sendMessageWithEmptySenderAndReceiverId(){
		dmap = new DataMappingClass("empty user id", emptyfrom, emptyTo);
		msgclass.createMessage(dmap, RestAssured.baseURI );
		Assert.assertEquals(msgclass.getStatusCode(), 200);
		String id = msgclass.getResponseBodyUsingKey("id");
		System.out.println(id);
		Assert.assertNotNull(id);
		Assert.assertEquals(id.length(), idLength);		
	}
	
	@Test(description="verify api should return error for Sending message without message field")
	public void sendMessageWithNoMessageField(){
		dmap = new DataMappingClass(null, fro, t1);
		msgclass.createMessage(dmap, RestAssured.baseURI );
		Assert.assertEquals(msgclass.getStatusCode(), 400);
		String errorMessage = msgclass.getResponseBodyUsingKey("error.name");
		Assert.assertEquals("ServiceError", errorMessage);		
	}
}
