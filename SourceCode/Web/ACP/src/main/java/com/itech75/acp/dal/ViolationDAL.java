package com.itech75.acp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.itech75.acp.common.ViolationStates;
import com.itech75.acp.core.BusinessEngine;
import com.itech75.acp.entities.Violation;
import com.itech75.acp.entities.ViolationDTO;
import com.itech75.acp.entities.ViolationData;

public class ViolationDAL {
	public static Violation getViolation(int id) {
		Violation violation = null;
		String sql = "select v.id as id, v.title as title, v.description as description, " +
				            "v.timestamp as timestamp, v.latitude as latitude, v.longitude as longitude, " + 
				            "v.userid as userid, u.username as username, " +
				            "v.severity, v.public_severity, v.state " +
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

	public static List<Violation> getViolations(String query, int userid) {
		List<Violation> result = new ArrayList<Violation>();
		DbHelper dbHelper = new DbHelper();
		try(Connection connection = dbHelper.getConnection()){
			String sql = "select v.id as id, v.title as title, v.description as description, " +
		                         "v.timestamp as timestamp, v.latitude as latitude, v.longitude as longitude, " + 
		                         "v.userid as userid, u.username as username, "+
		                         "v.severity, v.public_severity, v.state " +
                                 "from acp.violations v left join acp.users u on v.userid = u.id "+
		                         createWhereCondition(query, userid) +
		                         createOrderByCondition(query, userid) +
                                 "limit 10";
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
	
	public static List<Violation> getViolations(String select, String where, String orderby) {
		List<Violation> result = new ArrayList<Violation>();
		DbHelper dbHelper = new DbHelper();
		try(Connection connection = dbHelper.getConnection()){
			String sql = "select v.id as id, v.title as title, v.description as description, " +
		                         "v.timestamp as timestamp, v.latitude as latitude, v.longitude as longitude, " + 
		                         "v.userid as userid, u.username as username, "+
		                         "v.severity, v.public_severity, v.state " +
		                         select +
                                 "from acp.violations v left join acp.users u on v.userid = u.id "+
		                         where +
		                         orderby +
                                 "limit 10";
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
	
	private static String createWhereCondition(String query, int userid) {
		switch (query.toLowerCase()) {
		case "|my":
			return String.format(" where userid=%d ", userid);
		case "|open10":
			return " where state='OPEN' ";
		case "|fixcandidate10":
			return " where state='FIX_CANDIDATE' ";
		case "|fixed10":
			return " where state='FIXED' ";
		case "|dangerous10":
			return " ";
		case "|dangerouspublic10":
			return " ";
		case "|all":
			return "";
		default:
			return String.format(" where title like '%%%s%%' or description like '%%%s%%' ", query, query);
		}
	}
	
	private static String createOrderByCondition(String query, int userid) {
		switch (query.toLowerCase()) {
		case "|dangerous10":
			return "order by severity desc ";
		case "|dangerouspublic10":
			return "order by public_severity desc ";
		default:
			return "order by id desc ";
		}
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
	
	public static boolean changeViolationImage(int id, byte[] image) {
		DbHelper dbHelper = new DbHelper();
		
		try(Connection connection = dbHelper.getConnection()){
			String sql = "update acp.violations set image=? where Id=?";
			try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
				statement.setBytes(1, image);
				statement.setInt(2, id);
				
				int count = statement.executeUpdate();
				return count == 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
		violation.setSeverity(resultSet.getDouble("severity"));
		violation.setPublicSeverity(resultSet.getDouble("public_severity"));
		violation.setState(Enum.valueOf(ViolationStates.class, resultSet.getString("state")));
		return violation;
	}
	
	public static void save(Violation violation){
		String sqlUpdateViolation = "Update acp.violations set description=?, title=?, latitude=?, longitude=?, severity=? where id=?";
		String sqlDeleteViolationData = "Delete from acp.violation_data where violation_id=?";
		String sqlInsertViolationData = "Insert into acp.violation_data (violation_id, violation_meta_id, value, unit) values (?,?,?,?)";
		
		DbHelper dbhelper = new DbHelper();
		try(Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sqlUpdateViolation)){
				statement.setString(1, violation.getDescription());
				statement.setString(2, violation.getTitle());
				statement.setDouble(3, violation.getLatitude());
				statement.setDouble(4, violation.getLongitude());
				statement.setDouble(5, violation.getSeverity());
				statement.setInt(6, violation.getId());
				
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
	
	public static boolean createViolation(ViolationDTO violation) {
		try {
			DbHelper dbHelper = new DbHelper();
			try(Connection connection = dbHelper.getConnection()){
				String sql = "insert into acp.violations (description, image, latitude, longitude, userid, title) values(?, ?, ?, ?, (select id from acp.users where email=?), ?)";
				try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
					statement.setString(1, violation.getDescription());
					byte[] buffer = Base64.getMimeDecoder().decode(violation.getImageBase64());
					statement.setBytes(2, buffer);
					statement.setDouble(3, violation.getLatitude());
					statement.setDouble(4, violation.getLongitude());
					statement.setString(5, violation.getUserName());
					statement.setString(6, violation.getTitle());
					int count = statement.executeUpdate();

					return (count == 1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean fixViolation(Violation violation, byte[] image, int userid) {
		String sqlUpdate = "update acp.violations set state='FIXED_CANDIDATE' where id=?";
		String sqlInsert = "insert into acp.violation_images (violation_id, content, type) values (?, ?, 1)";
		try {
			DbHelper dbHelper = new DbHelper();
			int count = 0;
			try(Connection connection = dbHelper.getConnection()){
				try(PreparedStatement statement = dbHelper.prepareStatement(connection, sqlUpdate)){
					statement.setInt(1, violation.getId());
					count = statement.executeUpdate();
				}
				try(PreparedStatement statement = dbHelper.prepareStatement(connection, sqlInsert)){
					statement.setInt(1, violation.getId());
					statement.setBytes(2, image);
					count += statement.executeUpdate();
				}
				
				BusinessEngine.addSystemLog(violation.getId(), "Violation fixed", userid);
				return (count == 2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean rejectFix(Violation violation, int userid) {
		String sqlUpdate = "update acp.violations set state='OPEN' where id=?";
		try {
			DbHelper dbHelper = new DbHelper();
			int count = 0;
			try(Connection connection = dbHelper.getConnection()){
				try(PreparedStatement statement = dbHelper.prepareStatement(connection, sqlUpdate)){
					statement.setInt(1, violation.getId());
					count = statement.executeUpdate();
				}
				BusinessEngine.addSystemLog(violation.getId(), "Violation fix REJECTED", userid);
				return (count == 1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static byte[] getViolationFixImage(int id) {
		DbHelper dbHelper = new DbHelper();
		
		try(Connection connection = dbHelper.getConnection()){
			String sql = "select content from acp.violation_images where violation_id=? order by id desc limit 1";
			try(PreparedStatement statement = dbHelper.prepareStatement(connection, sql)){
				statement.setInt(1, id);
				try(ResultSet resultSet = statement.executeQuery()){
					if (resultSet.next()) {
						return resultSet.getBytes("content");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean approveFix(Violation violation, int userid) {
		String sqlUpdate = "update acp.violations set state='FIXED' where id=?";
		try {
			DbHelper dbHelper = new DbHelper();
			int count = 0;
			try(Connection connection = dbHelper.getConnection()){
				try(PreparedStatement statement = dbHelper.prepareStatement(connection, sqlUpdate)){
					statement.setInt(1, violation.getId());
					count = statement.executeUpdate();
				}
				BusinessEngine.addSystemLog(violation.getId(), "Violation fix APPROVED", userid);
				return (count == 1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}