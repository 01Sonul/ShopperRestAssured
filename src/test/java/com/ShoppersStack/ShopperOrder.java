package com.ShoppersStack;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.List;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojoLibrary.Pojo_Address;
import pojoLibrary.Pojo_ShopperOrder;

public class ShopperOrder {

	String jwtToken;
	int sId;
	int aId;
	List<Object> allAid;
	List<Object> allOrderId;
	List<Integer> allProductId;
	
	@Test
	public void login() {
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
		resp.then()/*.log().all()*/.assertThat().statusCode(200);
		
		//Capture jwtToken & shopperId
		jwtToken = resp.jsonPath().get("data.jwtToken");
		sId = resp.jsonPath().get("data.userId");
	}
	
	@Test(dependsOnMethods = "login")
	public void getAllAdress() {
		
		Response resp = given().auth().oauth2(jwtToken).pathParam("shopperId", sId)
		.when().get("/shoppers/{shopperId}/address");
		resp.then()/*.log().all()*/.assertThat().statusCode(200);	
		
		//capture all addressId for getPerticularAdress2
		allAid = resp.jsonPath().get("data.addressId");	
		
	}
	
	@Test(dependsOnMethods = "login")
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
	
	
	@Test(dependsOnMethods = "addProductToCart")
	public void placeOrder() {		
		
		baseURI = "https://www.shoppersstack.com/shopping";
		
		Pojo_Address add = new Pojo_Address("38236");
		Pojo_ShopperOrder job1 =new Pojo_ShopperOrder(add,"COD");
		
//		JSONObject job = new JSONObject();
//		job.put("addressId", "38236");
//		
//		JSONObject job1 = new JSONObject();
//		job1.put("address", job);
//		job1.put("paymentMode", "COD");
//		
		
		Response resp = given().auth().oauth2(jwtToken).body(job1).contentType(ContentType.JSON).pathParam("shopperId", sId)
		.when().post("/shoppers/{shopperId}/orders");
		resp.then().log().all().assertThat().statusCode(201);
		
	}

	
	@Test(dependsOnMethods = "login")
	public void displayOrders() {
		
		Response resp = given().auth().oauth2(jwtToken).pathParam("shopperId", sId)
		.when().get("/shoppers/{shopperId}/orders");
		resp.then().log().all().assertThat().statusCode(200);
				
		//capture orderId
		allOrderId = resp.jsonPath().get("data.orderId");
		System.out.println(allOrderId);
	
	}
	
	
	@Test(dependsOnMethods = "displayOrders")
	public void updateOrderStatus() {
		
		int expOrderId = 18111;
		
		for(Object one: allOrderId)	{
			if(one.equals(expOrderId)) {
				
				given().auth().oauth2(jwtToken).pathParams("shopperId", sId, "orderId", expOrderId).queryParam("status", "DELIVERED")
				.when().patch("/shoppers/{shopperId}/orders/{orderId}")
				.then().log().all().assertThat().statusCode(200);		
				break;
			}
		}
	}
		
	
	@Test(dependsOnMethods = "updateOrderStatus")
	public void generateInvoice() {
		
		Response resp = given().auth().oauth2(jwtToken).pathParams("shopperId", sId, "orderId", 18111)
		.when().get("/shoppers/{shopperId}/orders/{orderId}/invoice");
		resp.then().log().all().assertThat().statusCode(200);	
	
	}
	
}
