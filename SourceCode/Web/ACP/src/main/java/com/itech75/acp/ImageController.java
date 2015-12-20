package com.itech75.acp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itech75.acp.DAL.DbHelper;
import com.itech75.acp.Entities.Violation;

@Controller
@RequestMapping(value = "/image")
public class ImageController {
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public void show(@PathVariable int id, HttpServletResponse response){
		Violation violation = getViolation(id); 
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    try {
			response.getOutputStream().write(violation.getImage());
		    response.getOutputStream().close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Violation getViolation(int id) {
		Violation violation = null;
		try {
			DbHelper dbHelper = new DbHelper();
			Connection connection = dbHelper.getConnection();
			String sql = "select image from acp.violations where Id=?";
			PreparedStatement statement = dbHelper.prepareStatement(connection, sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				violation = new Violation();
				violation.setImage(resultSet.getBytes("image"));
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
