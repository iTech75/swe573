package com.itech75.acp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoteDAL {
	public static boolean addVote(int violationId, int vote, int userId) {
		String sql = "insert into acp.votes (violation_id, vote, userid) values(?,?,?)";
		String updateSql = "update acp.violations set public_severity=(SELECT avg(vote) FROM acp.votes where violation_id=?) where id=?";
		
		DbHelper dbhelper = new DbHelper();
		int count = 0;
		try (Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sql)){
				statement.setInt(1, violationId);
				statement.setInt(2, vote);
				statement.setInt(3, userId);
				
				count = statement.executeUpdate();
			}
			
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, updateSql)){
				statement.setInt(1, violationId);
				statement.setInt(2, violationId);
				
				count += statement.executeUpdate();
			}
			
			return count == 2;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getVoteForViolation(int violationId, int userid) {
		String sql = "SELECT vote FROM acp.votes where violation_id=? and userid=?";
		
		DbHelper dbhelper = new DbHelper();
		try (Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sql)){
				statement.setInt(1, violationId);
				statement.setInt(2, userid);
				try(ResultSet resultSet = statement.executeQuery()){
					if(resultSet.next()){
						return resultSet.getInt("vote");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}	
	
	public static boolean deleteVoteForViolation(int violationId, int userid) {
		String sql = "Delete FROM acp.votes where violation_id=? and userid=?";
		
		DbHelper dbhelper = new DbHelper();
		try (Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sql)){
				statement.setInt(1, violationId);
				statement.setInt(2, userid);
				
				int count = statement.executeUpdate();
				return count == 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
}