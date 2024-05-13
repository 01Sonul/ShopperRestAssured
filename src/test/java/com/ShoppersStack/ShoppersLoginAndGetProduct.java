package com.ShoppersStack;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.List;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class ShoppersLoginAndGetProduct {

	
	String jwtToken;
	int sId;
	List<Integer> allProductId;
	
	@Test
	public void loginAndCaptureJwt() {
		baseURI = "https://www.shoppersstack.com/shopping";
		
		JSONObject job = new JSONObject();
		job.put("email", "jenasanghamitra166@gmail.com");
		job.put("password", "Sonu@1999#");
		job.put("role", "SHOPPER");
		
		
		//pre-condition
		  Response resp = given().body(job).contentType(ContentType.JSON)
		//CRUD
		.when().post("/users/login");
		//Validation
		resp.then().log().all().assertThat().statusCode(200);
		//Capture jwtToken
		jwtToken = resp.jsonPath().get("data.jwtToken");
		sId = resp.jsonPath().get("data.userId");
	}
	
	@Test(dependsOnMethods = "loginAndCaptureJwt")
	public void getProduct() {
		/*
		 * basic auth -> basic("un", "pwd")
		 * bearerToken -> oauth2(token)
		 * oauth2.0 -> oauth2(token)
		 * oauth1.0 -> oauth2(token)
		 */
		//pre-condition
		given().auth().oauth2(jwtToken)
		//CRUD
		.when().get("/products/alpha")
		//Validate
		.then().log().all().assertThat().statusCode(200);
	}
	
	
	@Test(dependsOnMethods = "loginAndCaptureJwt")
	public void getAllProducts() {
		
		//pre-condition
		Response resp = given().auth().oauth2(jwtToken).queryParam("zoneId", "Alpha")
		//CRUD
		.when().get("/products/alpha");
		//Validate
		resp.then()/*.log().all()*/.assertThat().statusCode(200);
		
		//capturing all productId
		allProductId = resp.jsonPath().get("data.productId");
		System.out.println(allProductId);
	}
}
