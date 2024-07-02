package com.mavenproject.APITesting.Tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import junit.framework.Assert;
import io.restassured.response.Response;
public class DeleteMessageTests {
	BaseMessageClass msgclass = new BaseMessageClass();
	DataMappingClass dmap;
	From fro = new From("user1");
	To t1 = new To("user2");
	
	@BeforeTest
    public static void setup() {
        RestAssured.baseURI = "http://localhost:3000/api/messages";
    }
	
	@Test(description="verify that API allows successful deletion of the message with valid message id")
	public void verify_Deletion_of_message_is_success() {
		dmap = new DataMappingClass("deleting message", fro, t1);
		String messageId = msgclass.createMessage(dmap,RestAssured.baseURI );
		String messageEndpoint = RestAssured.basePath+messageId;
		msgclass.deleteMessage(messageEndpoint);
		Assert.assertEquals(msgclass.getStatusCode(), 204);
		Assert.assertEquals(msgclass.getStatusText(), "HTTP/1.1 204 No Content");
		msgclass.getMessage(messageEndpoint);
		System.out.println(msgclass.getMessage(messageEndpoint));
		System.out.println(msgclass.getStatusCode());
		Assert.assertEquals(404,msgclass.getStatusCode());
	}
	
	@Test(description="verify that API returns 404 error code while deletion of the message with invalid message id")
	public void verify_API_returns_404_error_code_with_invalid_message_id() {
		msgclass.deleteMessage(RestAssured.baseURI+"9145f7ef-ab21-4ba7-9462-3cac1a28f46b"); //message id does not exist 
		Assert.assertEquals(msgclass.getStatusCode(), 404);
		Assert.assertEquals(msgclass.getStatusText(), "HTTP/1.1 404 Not Found");
	}
	
	
}
