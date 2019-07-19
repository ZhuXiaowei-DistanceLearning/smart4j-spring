package org.smart4j.framework.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtil {
	static Connection con=null;
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		 try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/votesys", "root", "123456");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void closeConnection(Connection con){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
	}

}
