package com.itech75.acp.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HTTPErrorController {
    @RequestMapping(value = "/errors/400", method = RequestMethod.GET)
    public ModelAndView handle400(HttpServletRequest request, Exception e) {
  	  	ModelAndView model = new ModelAndView("/errors/generalerror");
  	  	model.addObject("statusCode", 400);
        return model;
    }
    
    @RequestMapping(value = "/errors/404", method = RequestMethod.GET)
    public ModelAndView handle404(HttpServletRequest request, Exception e) {
  	  	ModelAndView model = new ModelAndView("/errors/404");
        return model;
    }
}