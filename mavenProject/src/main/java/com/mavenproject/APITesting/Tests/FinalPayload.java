package com.mavenproject.APITesting.Tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FinalPayload {
	public static void main(String[] args) throws JsonProcessingException {
		From fro = new From("user1");
		To t1 = new To("user2");
		DataMappingClass dmap = new DataMappingClass("hello", fro, t1);
		ObjectMapper oMap = new ObjectMapper();
		String myData = oMap.writerWithDefaultPrettyPrinter().writeValueAsString(dmap);
		System.out.println(myData);		
	}

}
