package com.itech75.acp.controllers;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/exceptionhandler")
public class ExceptionHandlerController {
    @ExceptionHandler(Throwable.class)
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView handleError(Throwable e, HttpServletRequest request)
    {
        ModelAndView model = new ModelAndView("/errors/500");
        model.addObject("datetime", new Date());
        model.addObject("exception", e);
        model.addObject("url", request.getRequestURL());        
        e.printStackTrace();
        return model;
    }    
}