package com.itech75.acp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itech75.acp.common.WebHelper;
import com.itech75.acp.dal.LoginDAL;
import com.itech75.acp.entities.LoginRequest;
import com.itech75.acp.entities.LoginResponse;

@Controller
@EnableWebSecurity
public class LoginController {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute("username") String username, @ModelAttribute("password") String password, BindingResult result, HttpSession session)
	{
		if(result.hasErrors()){
			return null;
		}
		int userid = LoginDAL.checkLogin(username, password);
		if(userid > 0){
			session.setAttribute("userid", userid);
			return "home";
		}
		return "login";
	}
	
	@RequestMapping(value="/logins/", method=RequestMethod.POST)
	public @ResponseBody LoginResponse logins(@RequestBody final LoginRequest loginRequest)
	{
		boolean loginresult = LoginDAL.checkLogin(loginRequest.getUsername(), loginRequest.getPassword()) > 0;
		return new LoginResponse(loginresult);
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register()
	{
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registerPost(HttpServletRequest request, HttpServletResponse response)
	{
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String passwordRepeat = request.getParameter("passwordRepeat");
		
		ModelAndView model = new ModelAndView("register");
		if(password.equals(passwordRepeat)){
			String result = LoginDAL.registerUser(username, email, password);
			if(result == null){
				model = new ModelAndView("home");
				WebHelper.showSuccess(model, "User created!");
			}
			else{
				WebHelper.showError(model, result);
			}
		}
		else{
			WebHelper.showError(model, "Passwords do not match!");
		}
		return model;
	}
}