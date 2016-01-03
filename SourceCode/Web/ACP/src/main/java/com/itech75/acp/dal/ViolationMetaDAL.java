package com.itech75.acp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itech75.acp.entities.ViolationMeta;

public class ViolationMetaDAL {
	public static List<ViolationMeta> getViolationMetaList(String id) {
		List<ViolationMeta> result = new ArrayList<ViolationMeta>();
		ViolationMeta violationMeta = null;
		DbHelper dbHelper = new DbHelper();
		try(Connection connection = dbHelper.getConnection()){
			String sql = "select * from acp.violation_meta where vtype_id=?";
			try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
				statement.setString(1, id);
				try(ResultSet resultSet = statement.executeQuery()){
					while (resultSet.next()) {
						violationMeta = readViolationMetafromResultSet(resultSet);
						result.add(violationMeta);
					}
				}
			}
		}
	    catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
		return result;
	}

	public static ViolationMeta getViolationMeta(int id) {
		DbHelper dbHelper = new DbHelper();
		try(Connection connection = dbHelper.getConnection()){
			String sql = "select * from acp.violation_meta where id=?";
			try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
				statement.setInt(1, id);
				try(ResultSet resultSet = statement.executeQuery()){
					if (resultSet.next()) {
						return readViolationMetafromResultSet(resultSet);
					}
				}
			}
		}
	    catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
		return null;
	}

	private static ViolationMeta readViolationMetafromResultSet(ResultSet resultSet) throws SQLException {
		ViolationMeta violationMeta;
		violationMeta = new ViolationMeta(
				resultSet.getInt("id"), 
				resultSet.getInt("vtype_id"),
				resultSet.getString("description"), 
				resultSet.getInt("type"), 
				resultSet.getInt("comparison"),
				resultSet.getString("expected_value"), 
				resultSet.getString("expected_value_unit"),
				resultSet.getString("selection")
		);
		return violationMeta;
	}	
}