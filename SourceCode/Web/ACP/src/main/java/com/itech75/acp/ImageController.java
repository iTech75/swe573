package com.itech75.acp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itech75.acp.DAL.ViolationDAL;

@Controller
@RequestMapping(value = "/image")
public class ImageController {
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public void show(@PathVariable int id, HttpServletResponse response){
		byte[] image = null;
		
		try {
			image = ViolationDAL.getViolationImage(id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
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