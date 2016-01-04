package com.itech75.acp.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itech75.acp.entities.Comment;

public class CommentDAL {
	public static List<Comment> loadCommentsForViolation(int violationId) {
		List<Comment> result = new ArrayList<Comment>();
		String sql = "select c.id, c.content, c.type, c.userid, u.username as username, c.timestamp from acp.comments c inner join acp.users u on c.userid=u.id where c.violation_id=? order by c.timestamp desc";
		
		DbHelper dbhelper = new DbHelper();
		try (Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sql)){
				statement.setInt(1, violationId);
				try(ResultSet resultSet = statement.executeQuery()){
					while(resultSet.next()){
						Comment comment = new Comment();
						comment.setId(resultSet.getInt("id"));
						comment.setViolationId(violationId);
						comment.setContent(resultSet.getString("content"));
						comment.setType(resultSet.getInt("type"));
						comment.setUserid(resultSet.getInt("userid"));
						comment.setTimestamp(resultSet.getDate("timestamp"));
						comment.setUsername(resultSet.getString("username"));
						result.add(comment);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean addComment(int violationId, String comment, int userId) {
		String sql = "insert into acp.comments (violation_id, content, type, userid) values(?,?,?,?)";
		int type = 0; //User comment
		
		DbHelper dbhelper = new DbHelper();
		try (Connection connection = dbhelper.getConnection()){
			try(PreparedStatement statement = dbhelper.prepareStatement(connection, sql)){
				statement.setInt(1, violationId);
				statement.setString(2, comment);
				statement.setInt(3, type);
				statement.setInt(4, userId);
				
				int count = statement.executeUpdate();
				return count==1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
}