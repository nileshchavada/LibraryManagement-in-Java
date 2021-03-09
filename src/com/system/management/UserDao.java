package com.system.management;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.system.bean.AdminDetails;

public class UserDao {
	
	
private static final Logger logger = Logger.getLogger(BookDao.class.getName());
	
	public AdminDetails validateUserLogin(String id, String password) {
		int loginSuccess= 0;
		AdminDetails admin = new AdminDetails();
		try {
			 
			Connection con = DataSourceFactory.getConnection();
			String sql = "call library_database.sp_admin_auth(?,?)";
	        CallableStatement statement = con.prepareCall(sql);
	        statement.setString(1, id);
	        statement.setString(2, password);
	        ResultSet result = statement.executeQuery();
	        while(result.next()) {
	        	loginSuccess = Integer.parseInt(result.getString(1));
	        	if(loginSuccess>0)
	        		admin.setAdminId(id);
	        }
	 
	        con.close();
	 
	 

		} catch (Exception e) {
			logger.error("Exception occured in method validateUserLogin.."+e.getStackTrace());
		}

		return admin;

	}


}
