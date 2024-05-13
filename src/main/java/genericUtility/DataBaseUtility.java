package genericUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;




public class DataBaseUtility {

	Connection conn = null;

	/**
	 * This method is used to get Connection with Database.
	 * 
	 * @author arpan
	 * @throws SQsLException
	 */
	public void getConnection() throws SQLException {
		
		Driver driverRef = new Driver();
		DriverManager.registerDriver(driverRef);
		conn = DriverManager.getConnection(IPathConstants.DB_URL, IPathConstants.DB_Username,
				IPathConstants.DB_Password);

	}

	/**
	 * This method is for validating data present in Database or not.
	 * 
	 * @author arpan
	 * @param colNum
	 * @param expectedData
	 * @throws SQLException
	 */

	public String executeQueryAndGetData(int colNum, String query, String expData) throws SQLException {
		String data = null;
		boolean flag = false;
		ResultSet result = conn.createStatement().executeQuery(query);
		while (result.next()) {
			data = result.getString(colNum);
			if (data.equalsIgnoreCase(expData)) {
				flag = true; // flag rising
				break;
			}
		}
		if (flag) {
			System.out.println(data + "---> data verified");
			return expData;
		} else {
			System.out.println("data not verified");
			return "";
		}
	}

	/**
	 * This Method is to Close Connection with Database.
	 * 
	 * @author arpan
	 * @throws SQLException
	 */
	public void disconnectDB() throws SQLException {
		conn.close();
	}
}
