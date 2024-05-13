package practice;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Req_Resp_SpecBuilder {
	
	@Test
	public void login() {
		
		JSONObject job = new JSONObject();
		job.put("email", "jenasanghamitra166@gmail.com");
		job.put("password", "Sonu@1999#");
		job.put("role", "SHOPPER");
		
		
		RequestSpecBuilder rqsb = new RequestSpecBuilder();
		RequestSpecification request = rqsb.setBaseUri("https://www.shoppersstack.com/shopping").setContentType(ContentType.JSON).setBody(job).build();
		
		ResponseSpecBuilder rssb = new ResponseSpecBuilder();
		ResponseSpecification response = rssb.expectContentType(ContentType.JSON).expectStatusCode(200).build();
		
		//pre-condition
		given().spec(request)
		//CRUD 
		.when().post("/users/login")
		//Validation
		.then().log().all().spec(response);
			
	}

}
