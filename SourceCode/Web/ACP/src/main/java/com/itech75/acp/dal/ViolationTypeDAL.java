package com.itech75.acp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.itech75.acp.entities.ViolationType;

/*
 * loads a selected ViolationType from database. id defines the record to be loaded.
 */
public class ViolationTypeDAL {
	public static ViolationType getViolationType(String id) throws SQLException{
		String sql = "select id, description from acp.violation_types where id=?";
		DbHelper dbHelper = new DbHelper();
			
		try(Connection connection = dbHelper.getConnection()){
			try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
				statement.setString(1, id);
				try(ResultSet resultSet = statement.executeQuery()){
					if (resultSet.next()) {
						ViolationType result = new ViolationType();
						result.setId(resultSet.getString("id"));
						result.setDescription(resultSet.getString("description"));
					}
				}
			}
		}
		return null;
	}
	
	/*
	 * Load all Violation types from database.
	 */
	public static List<ViolationType> getViolationTypes() throws SQLException{
		String sql = "select id, description from acp.violation_types";
		DbHelper dbHelper = new DbHelper();
		List<ViolationType> result = new ArrayList<ViolationType>();
			
		try(Connection connection = dbHelper.getConnection()){
			try(Statement statement = dbHelper.getStatement(connection)){
				try(ResultSet resultSet = statement.executeQuery(sql)){
					while (resultSet.next()) {
						ViolationType violationType = new ViolationType();
						violationType.setId(resultSet.getString("id"));
						violationType.setDescription(resultSet.getString("description"));
						result.add(violationType);
					}
					return result;
				}
			}
		}
	}
}