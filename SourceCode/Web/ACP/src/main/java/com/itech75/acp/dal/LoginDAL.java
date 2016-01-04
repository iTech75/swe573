package com.itech75.acp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAL {
	public static int checkLogin(String username, String password){
		String sql = "select id from acp.users where email=? and password=?";
		
		DbHelper dbhelper = new DbHelper();
		try (Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sql)){
				statement.setString(1, username);
				statement.setString(2, password);
				try(ResultSet resultSet = statement.executeQuery()){
					if(resultSet.next()){
						return resultSet.getInt("id");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public static String registerUser(String username, String email, String password) {
		String sqlcheck = "select id from acp.users where email=? or username=?";
		String sqlinsert = "insert into acp.users (username, email, password) values (?, ?, ?)";
		
		DbHelper dbhelper = new DbHelper();
		try (Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sqlcheck)){
				statement.setString(1, email);
				statement.setString(2, username);
				try(ResultSet resultSet = statement.executeQuery()){
					if(resultSet.next()){
						return "Username or email is in use by other user, please choose different ones!";
					}
				}
			}
			
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sqlinsert)){
				statement.setString(1, username);
				statement.setString(2, email);
				statement.setString(3, password);
				int i = statement.executeUpdate();
				if(i != 1){
					return "Unexpected error!";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		return null;
	}
	
	public static int findUserId(String email){
		String sql = "select id from acp.users where email=?";
		
		DbHelper dbhelper = new DbHelper();
		try (Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sql)){
				statement.setString(1, email);
				try(ResultSet resultSet = statement.executeQuery()){
					if(resultSet.next()){
						return resultSet.getInt("id");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}