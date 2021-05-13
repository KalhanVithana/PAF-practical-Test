package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
//database class
	private static String url="jdbc:mysql://localhost:3306/cus";
	private static String user = "root";
	private static String password = "";
	private static Connection con;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection Success");
		System.out.println("conected");
		return con;
	}
	
		
	}

