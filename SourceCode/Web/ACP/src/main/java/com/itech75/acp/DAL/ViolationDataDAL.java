package com.itech75.acp.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itech75.acp.Entities.ViolationData;
import com.itech75.acp.common.Units;

public class ViolationDataDAL {
	public static List<ViolationData> getViolationDataList(int violationId) {
		List<ViolationData> result = new ArrayList<ViolationData>();
		try {
			ViolationData violationData = null;
			DbHelper dbHelper = new DbHelper();
			Connection connection = dbHelper.getConnection();
			String sql = "select A.*, B.description from acp.violation_data A inner join acp.violation_meta B on A.violation_meta_id=B.id  where violation_id=?";
			PreparedStatement statement = dbHelper.prepareStatement(connection, sql);
			statement.setInt(1, violationId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				violationData = new ViolationData(resultSet.getInt("id"), resultSet.getInt("violation_id"),
						resultSet.getInt("violation_meta_id"), resultSet.getString("value"), 
						Units.valueOf(resultSet.getString("unit")));
				violationData.setViolationMetaDescription(resultSet.getString("description"));
				result.add(violationData);
			}
			resultSet.close();
			statement.close();
			connection.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean delete(int id) throws SQLException{
		DbHelper dbHelper = new DbHelper();
		try(Connection connection = dbHelper.getConnection()){
			String sql = "delete from acp.violation_data where id=?";
			try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
				statement.setInt(1, id);
				int count = statement.executeUpdate();
				return count == 1;
			}
		}
	}
}
