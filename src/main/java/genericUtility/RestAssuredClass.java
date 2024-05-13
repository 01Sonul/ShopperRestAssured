package genericUtility;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredClass {

	/**
	 * This method will return json Data from corresponding response body
	 * @author arpan
	 * @param resp
	 * @param path
	 * @return
	 */
	
	public String getJsonData(Response resp,String path) {
		
		String jsonData=resp.jsonPath().get(path);
		return jsonData;
	}
	
	
	public List<Integer> getJsonData2(Response resp,String path) {
		
		List<Integer> jsonData=resp.jsonPath().get(path);
		return jsonData;
	}

	
	public int getJsonData3(Response resp,String path) {
		
		int jsonData=resp.jsonPath().get(path);
		return jsonData;
	}
}
