package practice;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class CrudWithBDD {
	
	@Test
	public void login() {
		
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

}
