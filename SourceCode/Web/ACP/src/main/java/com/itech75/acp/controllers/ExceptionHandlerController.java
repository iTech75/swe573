package com.itech75.acp.controllers;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(Throwable.class)
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