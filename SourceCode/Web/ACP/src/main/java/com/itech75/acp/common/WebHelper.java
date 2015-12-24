package com.itech75.acp.common;

import org.springframework.web.servlet.ModelAndView;

public class WebHelper {
	public static void showMessage(ModelAndView model, String message){
		model.addObject("message", message);
		model.addObject("messageType", "info");
	}

	public static void showError(ModelAndView model, String message){
		model.addObject("message", message);
		model.addObject("messageType", "danger");
	}

	public static void showSuccess(ModelAndView model, String message){
		model.addObject("message", message);
		model.addObject("messageType", "success");
	}

	public static void showWarning(ModelAndView model, String message){
		model.addObject("message", message);
		model.addObject("messageType", "warning");
	}
}
