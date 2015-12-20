package com.itech75.acp.Entities;

import java.io.Serializable;

public class LoginResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6250128888631437324L;
	private boolean result;
	
	
	public boolean isResult() {
		return result;
	}


	public void setResult(boolean result) {
		this.result = result;
	}


	public LoginResponse(boolean result){
		this.result = result;
	}
	
}