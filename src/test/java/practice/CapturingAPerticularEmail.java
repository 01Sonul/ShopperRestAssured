package practice;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.when;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.response.Response;


public class CapturingAPerticularEmail {
	
	List<String> allEId;
	
	@Test
	public void getAPerticularId() {
		
		baseURI = "https://reqres.in";
		String expEmail = "tobias.funke@reqres.in";
		
		Response resp = when().get("/api/users?page=2");
		
		//capture all id
		allEId = resp.jsonPath().get("data.email");
				
		for(String EId: allEId) {
			if(EId.equals(expEmail)) {
				System.out.println(expEmail + " is available");
				break;
			}
		}
		resp.then().log().all().assertThat().statusCode(200);	
		
	}	
}
