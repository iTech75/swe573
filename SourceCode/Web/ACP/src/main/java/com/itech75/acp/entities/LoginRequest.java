package com.itech75.acp.entities;

import java.io.Serializable;

public class LoginRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6823962485593210401L;
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public LoginRequest(){
		
	}
	
	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
}
