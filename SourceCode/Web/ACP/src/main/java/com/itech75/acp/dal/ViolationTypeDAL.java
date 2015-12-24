package com.itech75.acp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itech75.acp.entities.ViolationType;

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
}