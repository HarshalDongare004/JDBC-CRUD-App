package in.ineuron.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;



public class jdbcUtil {
	
	private jdbcUtil() {
		
	}
	static {
		//step 1 load and register the driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static Connection getJdbcConnection() throws SQLException, IOException{
		
		String fileLoc = "D:\\ServletProgram\\JDBCCRUDAPP\\src\\main\\java\\in\\ineuron\\properties\\application.properties";
		HikariConfig config = new HikariConfig(fileLoc);
		HikariDataSource  dataSource = new HikariDataSource(config);
		
		return dataSource.getConnection();
		
		//return physicalConnection();
	}
	
	@SuppressWarnings("unused")
	public static Connection physicalConnection() throws FileNotFoundException, SQLException,IOException{
		
		//take data from properties file
				FileInputStream fis = new FileInputStream("D:\\ServletProgram\\JDBCCRUDAPP\\src\\main\\java\\in\\ineuron\\properties\\application.properties");
				Properties properties = new Properties();
				properties.load(fis);
		
		//step 2 establish connection
		
		
		Connection connection = DriverManager.getConnection(properties.getProperty("jdbcUrl"),
				properties.getProperty("user"),properties.getProperty("password"));
		
		return connection;

//		return dataSource.getConnection();
				
	}
	
//	public static void cleanUp(Connection conn,Statement statement,ResultSet resultSet) throws SQLException{
//		//step 6 : close the resources
//		if (conn!= null){
//			conn.close();
//			
//		} if(statement!= null) {
//			statement.close();
//		}if(resultSet!= null) {
//			resultSet.close();
//		}
//		
//	}
}
