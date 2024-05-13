package com.ShoppersStack.ApiTestScripts;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import java.util.List;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import genericUtility.BaseClassAPI;
import genericUtility.EndPointsLibrary;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojoLibrary.Pojo_LoginAndCaptureJwt_1;
import pojoLibrary.Pojo_ShoppersAddress_1;


public class ShoppersAddress_1 extends BaseClassAPI{ //Using pathParam()
	
	String jwtToken;
	int sId;
	int aId;
	List<Object> allAid;
	
	@Test
	public void login() {

		Pojo_LoginAndCaptureJwt_1 pLogin = new Pojo_LoginAndCaptureJwt_1("jenasanghamitra166@gmail.com", "Sonu@1999#", "SHOPPER");
		
		//pre-condition
		Response resp = given().spec(request).body(pLogin)
		//CRUD
		.when().post(EndPointsLibrary.login);
		//Validation
		resp.then().log().all().assertThat().statusCode(200);
		
		//Capture jwtToken & shopperId
		jwtToken = raClass.getJsonData(resp, "data.jwtToken");
		sId = raClass.getJsonData3(resp, "data.userId");
		System.out.println("This is sid "+sId);
	}
	
	
	@Test(dependsOnMethods = "login")
	public void addNewAddress() {
		System.out.println("This is sid in nwAD "+sId);
		Pojo_ShoppersAddress_1 pShopAdd = new Pojo_ShoppersAddress_1(0, "45 Simran Niwas", "Andhra Pradesh", "India", "Ashoka Nagar", "xyz"+jUtil.getRandomNumber(), "7608710778", "432103", "Karnataka", "Simmi Garden",  "SHOPPER");
			
		//pre-condition
		Response resp = given().auth().oauth2(jwtToken).spec(request).body(pShopAdd).pathParam("shopperId", sId)
		//CRUD
		.when().post("/shoppers/{shopperId}/address");
		//Validation
		//resp.then().log().all();
//		String name=resp.jsonPath().get("data.name");
//		System.out.println("name is "+name);
		
	}
	
	
	@Test(dependsOnMethods = "login")
	public void getAllAdress() {
		
		Response resp = given().auth().oauth2(jwtToken).pathParam("shopperId", sId)
		.when().get("/shoppers/{shopperId}/address");
		resp.then().log().all().assertThat().statusCode(200);	
		
		//capture addressId for getPerticularAdress1
		aId = resp.jsonPath().get("data[0].addressId");
		
		//capture all addressId for getPerticularAdress2
		allAid = resp.jsonPath().get("data.addressId");	
	}
	
	
	@Test(dependsOnMethods = "getAllAdress") 	//static response validation
	public void getPerticularAdress1() { 	//using addressId
		
		given().auth().oauth2(jwtToken).pathParam("shopperId", sId).pathParam("addressId", aId)
		.when().get("/shoppers/{shopperId}/address/{addressId}")
		.then().log().all().assertThat().statusCode(200);	
		
	}
	
	
	@Test(dependsOnMethods = "getAllAdress") 	//dynamic response validation
	public void getPerticularAdress2() { 	//using addressId
		
		int expectedAid = 38094;
				
		for(Object e: allAid)	{
			if(e.equals(expectedAid)) {
				Response resp = given().auth().oauth2(jwtToken).pathParam("shopperId", sId).pathParam("addressId", e)
				.when().get("/shoppers/{shopperId}/address/{addressId}");
				resp.then().log().all().assertThat().statusCode(200);
			}
		}
	}
	
	//changing 38094 aid's name & city
	@Test(dependsOnMethods = "getAllAdress")
	public void updateAdress() {
		
		JSONObject job = new JSONObject();
		job.put("addressId", "0");
		job.put("buildingInfo", "12a Tilak Niwas");
		job.put("city", "Odisha");
		job.put("country", "India");
		job.put("landmark", "near Milk Factory");
		job.put("name", "Ritu");
		job.put("phone", "7608710778");
		job.put("pincode", "560016");
		job.put("state", "Karnataka");
		job.put("streetInfo", "Pooja Garden");
		job.put("type", "SHOPPER");
		
		given().auth().oauth2(jwtToken).body(job).contentType(ContentType.JSON).pathParam("shopperId", sId).pathParam("addressId", 38094)
		.when().put("/shoppers/{shopperId}/address/{addressId}")
		.then().log().all().assertThat().statusCode(200);		
		
	}
	
	@Test(dependsOnMethods = "getAllAdress")
	public void deleteAnAddress() {
		
		int expectedAid = 38115;
		
		for(Object e: allAid)	{
			if(e.equals(expectedAid)) {
				given().auth().oauth2(jwtToken).pathParam("shopperId", sId).pathParam("addressId", e)
				.when().delete("/shoppers/{shopperId}/address/{addressId}")
				.then().log().all().assertThat().statusCode(204);		
								
			}
		}		
	}
	
	
	
}
