package com.itech75.acp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.itech75.acp.entities.Violation;
import com.itech75.acp.entities.ViolationData;

public class ViolationDAL {
	public static Violation getViolation(int id) {
		Violation violation = null;
		String sql = "select v.id as id, v.title as title, v.description as description, " +
				            "v.timestamp as timestamp, v.latitude as latitude, v.longitude as longitude, " + 
				            "v.userid as userid, u.username as username "+
                "from acp.violations v left join acp.users u on v.userid = u.id "+
                "where v.id=?";
		DbHelper dbHelper = new DbHelper();
			
		try(Connection connection = dbHelper.getConnection()){
			try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
				statement.setInt(1, id);
				try(ResultSet resultSet = statement.executeQuery()){
					if (resultSet.next()) {
						violation = loadViolationFromResultSet(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return violation;
	}

	public static List<Violation> getViolations() {
		List<Violation> result = new ArrayList<Violation>();
		DbHelper dbHelper = new DbHelper();
		try(Connection connection = dbHelper.getConnection()){
			String sql = "select v.id as id, v.title as title, v.description as description, " +
		                         "v.timestamp as timestamp, v.latitude as latitude, v.longitude as longitude, " + 
		                         "v.userid as userid, u.username as username "+
                                 "from acp.violations v left join acp.users u on v.userid = u.id "+
                                 "order by id desc " +
                                 "limit 5";
			try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
				try(ResultSet resultSet = statement.executeQuery()){
					while (resultSet.next()) {
						Violation violation = loadViolationFromResultSet(resultSet);
						result.add(violation);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static byte[] getViolationImage(int id) {
		DbHelper dbHelper = new DbHelper();
		
		try(Connection connection = dbHelper.getConnection()){
			String sql = "select image from acp.violations where Id=?";
			try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
				statement.setInt(1, id);
				try(ResultSet resultSet = statement.executeQuery()){
					if (resultSet.next()) {
						return resultSet.getBytes("image");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static Violation loadViolationFromResultSet(ResultSet resultSet) throws SQLException {
		Violation violation;
		violation = new Violation();
		violation.setId(resultSet.getInt("Id"));
		violation.setTitle(resultSet.getString("title"));
		violation.setDescription(resultSet.getString("description"));
		violation.setTimeStamp(resultSet.getDate("timestamp"));
		violation.setLatitude(resultSet.getDouble("latitude"));
		violation.setLongitude(resultSet.getDouble("longitude"));
		violation.setUserId(resultSet.getInt("userid"));
		violation.setUserName(resultSet.getString("username"));
		return violation;
	}
	
	public static void save(Violation violation){
		String sqlUpdateViolation = "Update acp.violations set description=?, title=?, latitude=?, longitude=? where id=?";
		String sqlDeleteViolationData = "Delete from acp.violation_data where violation_id=?";
		String sqlInsertViolationData = "Insert into acp.violation_data (violation_id, violation_meta_id, value, unit) values (?,?,?,?)";
		
		DbHelper dbhelper = new DbHelper();
		try(Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sqlUpdateViolation)){
				statement.setString(1, violation.getDescription());
				statement.setString(2, violation.getTitle());
				statement.setDouble(3, violation.getLatitude());
				statement.setDouble(4, violation.getLongitude());
				statement.setInt(5, violation.getId());
				
				statement.executeUpdate();
			}
			
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sqlDeleteViolationData)){
				statement.setInt(1, violation.getId());
				statement.executeUpdate();
			}
	
			for (ViolationData data : violation.getViolationData()) {
				try(PreparedStatement statement = dbhelper.prepareStatement(connection, sqlInsertViolationData)){
					statement.setInt(1, violation.getId());
					statement.setInt(2, data.getViolationMetaId());
					statement.setString(3, data.getValue());
					statement.setString(4, data.getUnit().toString());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean createViolation(Violation violation) {
		try {
			DbHelper dbHelper = new DbHelper();
			Connection connection = dbHelper.getConnection();
			String sql = "insert into acp.violations (description, image, latitude, longitude, username, title) values(?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = dbHelper.prepareStatement(connection, sql);
			statement.setString(1, violation.getDescription());
			byte[] buffer = Base64.getMimeDecoder().decode(violation.getImageBase64());
			statement.setBytes(2, buffer);
			statement.setDouble(3, violation.getLatitude());
			statement.setDouble(4, violation.getLongitude());
			statement.setString(5, violation.getUserName());
			statement.setString(6, violation.getTitle());
			int count = statement.executeUpdate();
			statement.close();
			connection.close();
			return (count == 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
}