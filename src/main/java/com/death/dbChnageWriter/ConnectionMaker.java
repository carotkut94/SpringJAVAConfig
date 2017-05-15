package com.death.dbChnageWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
public class ConnectionMaker {
	
	
	public static Connection getConnection()
	{
		Connection con= null;
		try{
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/users";
		String user = "root";
		String password = "";
		Class.forName(driverName);
		con = DriverManager.getConnection(url, user, password);
		return con;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	public static java.util.Date getLastJobExecutionDate() throws Exception
	{
		Connection con = getConnection();
		ResultSet rs = null;
		String sql = "select END_TIME from batch_job_execution ORDER BY JOB_EXECUTION_ID DESC LIMIT 1";
		java.sql.Statement statement  = con.createStatement();
		
		rs = statement.executeQuery(sql);
		java.util.Date lastDate = null;
		if(rs.next()){
			lastDate =  rs.getTimestamp(1);
		}
		else{
			return null;
		}
		
		return lastDate;
	}
}
