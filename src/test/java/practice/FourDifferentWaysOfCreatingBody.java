package practice;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import pojoLibrary.Pojo_FourDifferentWaysOfCreatingBody;

public class FourDifferentWaysOfCreatingBody {

	//using JsonObject
	@Test
	public void loginUsingJsonObject() {
		
		baseURI = "https://www.shoppersstack.com/shopping";
		
		JSONObject job = new JSONObject();
		job.put("email", "jenasanghamitra166@gmail.com");
		job.put("password", "Sonu@1999#");
		job.put("role", "SHOPPER");
		
		//pre-condition
		given().body(job).contentType(ContentType.JSON)
		//CRUD 
		.when().post("/users/login")
		//Validation
		//.then().log().all().statusCode(200);
		.then().log().all().assertThat().statusCode(200);
			
	}
	
	//using HashMap
	@Test
	public void loginUsingHashMap() {
		
		baseURI = "https://www.shoppersstack.com/shopping";
		
		HashMap<String, String> job = new HashMap<String, String>();
		job.put("email", "jenasanghamitra166@gmail.com");
		job.put("password", "Sonu@1999#");
		job.put("role", "SHOPPER");
		
		//pre-condition
		given().body(job).contentType(ContentType.JSON)
		//CRUD 
		.when().post("/users/login")
		//Validation
		//.then().log().all().statusCode(200);
		.then().log().all().assertThat().statusCode(200);
			
	}
	
	//using Pojo
	@Test
	public void loginUsingPojoClass() {
		
		baseURI = "https://www.shoppersstack.com/shopping";
			
		Pojo_FourDifferentWaysOfCreatingBody job = new Pojo_FourDifferentWaysOfCreatingBody("jenasanghamitra166@gmail.com", "Sonu@1999#", "SHOPPER");
		//pre-condition
		given().body(job).contentType(ContentType.JSON)
		//CRUD 
		.when().post("/users/login")
		//Validation
		.then().log().all().assertThat().statusCode(200);
			
	}
	
	//using File
	@Test
	public void loginUsingFile() {
		
		baseURI = "https://www.shoppersstack.com/shopping";
		
		File job = new File("./src/main/resources/File.json");
		//pre-condition
		given().body(job).contentType(ContentType.JSON)
		//CRUD 
		.when().post("/users/login")
		//Validation
		.then().log().all().assertThat().statusCode(200);
			
	}
			
}
