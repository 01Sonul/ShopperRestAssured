package com.ShoppersStack.ApiTestScripts;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.List;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import genericUtility.BaseClassAPI;
import genericUtility.EndPointsLibrary;
import genericUtility.RestAssuredClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import pojoLibrary.Pojo_LoginAndCaptureJwt_1;

public class ShoppersLoginAndGetProduct_1 extends BaseClassAPI{

	
	String jwtToken;
	int sId;
	List<Integer> allProductId;
	
	@Test
	public void loginAndCaptureJwt() {
		
		Pojo_LoginAndCaptureJwt_1 pLogin = new Pojo_LoginAndCaptureJwt_1("jenasanghamitra166@gmail.com", "Sonu@1999#", "SHOPPER");
		
		//pre-condition
		Response resp = given().spec(request).body(pLogin)
		//CRUD
		.when().post(EndPointsLibrary.login);
		//Validation
		resp.then().log().all().spec(response).assertThat().statusCode(200);
		
		//Capture jwtToken
		jwtToken = raClass.getJsonData(resp,"data.jwtToken" );
		System.out.println(jwtToken);
	}
	
	
	
	@Test(dependsOnMethods = "loginAndCaptureJwt")
	public void getAllProducts() {
		
		//pre-condition
		Response resp = given().spec(request).auth().oauth2(jwtToken).queryParam("zoneId", "Alpha")
		//CRUD
		.when().get(EndPointsLibrary.getAllProducts);
		//Validate
		resp.then().log().all().spec(response).assertThat().statusCode(200);
		
		//capturing all productId
		allProductId = raClass.getJsonData2(resp, "data.productId");
		System.out.println(allProductId);
	}
}
