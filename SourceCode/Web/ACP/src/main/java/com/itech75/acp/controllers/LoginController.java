package com.itech75.acp.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itech75.acp.dal.DbHelper;
import com.itech75.acp.entities.LoginRequest;
import com.itech75.acp.entities.LoginResponse;

@Controller
public class LoginController {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/login/", method=RequestMethod.POST)
	public String login(@ModelAttribute("username") String username, @ModelAttribute("password") String password, Model model, BindingResult result)
	{
		if(result.hasErrors()){
			return null;
		}
		if(checkLogin(username, password)){
			return "home";
		}
		return "login";
	}
	
	@RequestMapping(value="/logins/", method=RequestMethod.POST)
	public @ResponseBody LoginResponse logins(@RequestBody final LoginRequest loginRequest)
	{
		boolean loginresult = checkLogin(loginRequest.getUsername(), loginRequest.getPassword());
		return new LoginResponse(loginresult);
	}
	
	private boolean checkLogin(String username, String password){
		String sql = "select username from acp.users where email=? and password=?";
		try {
			DbHelper dbhelper = new DbHelper();
			Connection connection = dbhelper.getConnection();
			PreparedStatement statement = dbhelper.prepareStatement(connection, sql);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				return true;
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}