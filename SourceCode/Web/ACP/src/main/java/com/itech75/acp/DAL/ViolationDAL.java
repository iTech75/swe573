package com.itech75.acp.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.itech75.acp.Entities.Violation;
import com.itech75.acp.Entities.ViolationData;

public class ViolationDAL {
	public static boolean save(Violation violation){
		String sqlUpdateViolation = "Update acp.violations set description=?, title=?, type=? where id=?";
		String sqlDeleteViolationData = "Delete from acp.violation_data where violation_id=?";
		String sqlInsertViolationData = "Insert into acp.violation_data (violation_id, violation_meta_id, value, unit) values (?,?,?,?)";
		
		DbHelper dbhelper = new DbHelper();
		Connection connection = dbhelper.getConnection();
		PreparedStatement statement = dbhelper.prepareStatement(connection, sqlUpdateViolation);
		try {
			statement.setString(1, violation.getDescription());
			statement.setString(2, violation.getTitle());
			statement.setInt(3, violation.getType());
			statement.setInt(4, violation.getId());
			
			int count = statement.executeUpdate();

			statement.close();
			
			statement = dbhelper.prepareStatement(connection, sqlDeleteViolationData);
			statement.setInt(1, violation.getId());
			count = statement.executeUpdate();
			statement.close();

			for (ViolationData data : violation.getViolationData()) {
				statement = dbhelper.prepareStatement(connection, sqlInsertViolationData);
				statement.setInt(1, violation.getId());
				statement.setInt(2, data.getViolationMetaId());
				statement.setString(3, data.getValue());
				statement.setString(4, data.getUnit().toString());
				count = statement.executeUpdate();
				statement.close();
			}
			
			connection.close();
			return count == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
		}
		
		return false;
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
	
	public static List<Violation> getViolations() {
		List<Violation> result = new ArrayList<Violation>();
		try {
			DbHelper dbHelper = new DbHelper();
			Connection connection = dbHelper.getConnection();
			String sql = "select Id, title, description, username, latitude, longitude from acp.violations order by Id desc limit 5";
			PreparedStatement statement = dbHelper.prepareStatement(connection, sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Violation violation = new Violation(resultSet.getInt("Id"), resultSet.getString("title"),
						resultSet.getString("description"), null, "", resultSet.getString("userName"),
						resultSet.getDouble("latitude"), resultSet.getDouble("longitude"), new Date());
				result.add(violation);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static Violation getViolation(int id) {
		Violation violation = null;
		try {
			DbHelper dbHelper = new DbHelper();
			Connection connection = dbHelper.getConnection();
			String sql = "select Id, title, description, username, latitude, longitude, timestamp, type from acp.violations where Id=?";
			PreparedStatement statement = dbHelper.prepareStatement(connection, sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				violation = new Violation(resultSet.getInt("Id"), resultSet.getString("title"),
						resultSet.getString("description"), null, "", resultSet.getString("userName"),
						resultSet.getDouble("latitude"), resultSet.getDouble("longitude"),
						resultSet.getDate("timestamp"));
				violation.setType(resultSet.getInt("type"));
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return violation;
	}
}
