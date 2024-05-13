package genericUtility;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseClassAPI {
	
	public DataBaseUtility dbUtil = new DataBaseUtility();
	public RestAssuredClass raClass = new RestAssuredClass();
	public JavaUtility jUtil = new JavaUtility();
	
	public RequestSpecification request;
	public ResponseSpecification response;
	
	@BeforeSuite
	public void openConnection() throws Throwable {
		
		//Connection to DB
		//dbUtil.getConnection();
		
		//ReqResp Builders
		RequestSpecBuilder reqSB = new RequestSpecBuilder();
		request = reqSB.setBaseUri("https://www.shoppersstack.com/shopping")
		.setContentType(ContentType.JSON).build();
		
//		ResponseSpecBuilder resSb = new ResponseSpecBuilder();
//		response = resSb.expectContentType(ContentType.JSON).build();
		
	}
	

	@AfterSuite
	public void closeConnection() throws Throwable {
		
		//closing Connection to DB
		//dbUtil.disconnectDB();
	}
	
	
	
	

}
