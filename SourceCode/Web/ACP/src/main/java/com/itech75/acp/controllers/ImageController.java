package com.itech75.acp.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itech75.acp.dal.ViolationDAL;

@Controller
@RequestMapping(value = "/image")
@EnableWebSecurity
public class ImageController {
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public void show(@PathVariable int id, HttpServletResponse response){
		byte[] image = null;
		
		image = ViolationDAL.getViolationImage(id); 
		
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    try {
			response.getOutputStream().write(image);
		    response.getOutputStream().close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}