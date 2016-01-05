package com.itech75.acp.entities;

import java.io.Serializable;

public class ViolationResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8359713833927104772L;
	private boolean result;
	private String message;
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String isMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ViolationResult(boolean result, String message) {
		super();
		this.result = result;
		this.message = message;
	}
	
}
