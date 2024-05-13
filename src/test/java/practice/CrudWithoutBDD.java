package practice;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CrudWithoutBDD {
	
	@Test
	public void login() {
		
		
		JSONObject job = new JSONObject();
		job.put("email", "jenasanghamitra166@gmail.com");
		job.put("password", "Sonu@1999#");
		job.put("role", "SHOPPER");
		
		Response resp = RestAssured.given().body(job).contentType(ContentType.JSON).post("https://www.shoppersstack.com/shopping/users/login");
		resp.then().log().all();
		
		int statusCode = resp.getStatusCode();
		String statusLine = resp.getStatusLine();
		System.out.println(statusCode + "->" + statusLine);
		Assert.assertEquals(statusCode, 200);
		System.out.println("200 OK");
	}

}
