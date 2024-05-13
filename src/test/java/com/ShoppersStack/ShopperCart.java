package com.ShoppersStack;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.List;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ShopperCart {

	String jwtToken;
	int sId;
	List<Integer> allProductId;
	List<Integer> allItemId;
	
	@Test
	public void loginAndCaptureJwt() {
		baseURI = "https://www.shoppersstack.com/shopping";
		
		JSONObject job = new JSONObject();
		job.put("email", "jenasanghamitra166@gmail.com");
		job.put("password", "Sonu@1999#");
		job.put("role", "SHOPPER");
		

		  Response resp = given().body(job).contentType(ContentType.JSON)
		.when().post("/users/login");
		resp.then()/*.log().all()*/.assertThat().statusCode(200);
		
		//Capture jwtToken & shopperId
		jwtToken = resp.jsonPath().get("data.jwtToken");
		sId = resp.jsonPath().get("data.userId");
		
	}
	
	
	@Test(dependsOnMethods = "loginAndCaptureJwt")
	public void getAllProducts() {
		
		Response resp = given().auth().oauth2(jwtToken).queryParam("zoneId", "Alpha")
		.when().get("/products/alpha");
		resp.then()/*.log().all()*/.assertThat().statusCode(200);
		
		//capturing all productId
		allProductId = resp.jsonPath().get("data.productId");
		System.out.println(allProductId);
	}
	
	
	@Test(dependsOnMethods = "getAllProducts")
	public void addProductToCart() {
		
		int expProductId = 9;

		for(Object pro: allProductId)	{
			if(pro.equals(expProductId)) {
				
				JSONObject job = new JSONObject();
				job.put("productId", pro);
				job.put("quantity", 1);
				
				given().auth().oauth2(jwtToken).body(job).contentType(ContentType.JSON).pathParam("shopperId", sId)
				.when().post("/shoppers/{shopperId}/carts")
				.then().log().all().assertThat().statusCode(201);		
				break;				
			}
		}
	}
	
	
	@Test(dependsOnMethods = "getAllProducts")
	public void getAllProductsFromCart() {
		Response resp = given().auth().oauth2(jwtToken).pathParam("shopperId", sId)
		.when().get("/shoppers/{shopperId}/carts");
		resp.then().log().all().assertThat().statusCode(200);
		
		//capture ItemId
		allItemId = resp.jsonPath().get("data.itemId");
		System.out.println(allItemId);
	}
	
	
	@Test(dependsOnMethods = "getAllProductsFromCart")
	public void updateProductsInCart() {
		
		int expItemId = 143610;
		
		for(Object item: allItemId)	{
			if(item.equals(expItemId)) {
				
				JSONObject job = new JSONObject();
				job.put("productId", 0);
				job.put("quantity", 3);
				
				given().auth().oauth2(jwtToken).body(job).contentType(ContentType.JSON).pathParams("shopperId", sId, "itemId", expItemId)
				.when().put("/shoppers/{shopperId}/carts/{itemId}")
				.then().log().all().assertThat().statusCode(200);		
				break;
			}
		}
	}
			
	
	@Test(dependsOnMethods = "getAllProducts")
	public void deleteProductsFromCart() {
		
		int expProId = 31;
		
		for(Object p: allProductId)	{
			if(p.equals(expProId)) {
				given().auth().oauth2(jwtToken).pathParams("shopperId", sId, "productId", expProId)
				.when().delete("/shoppers/{shopperId}/carts/{productId}")
				.then().log().all().assertThat().statusCode(200);		
								
			}
		}
	}
	
	
}
