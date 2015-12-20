package com.itech75.acp.Entities;

public class ViolationResult {
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
