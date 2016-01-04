package com.itech75.acp.entities;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7746121816635796997L;
	
	private int id;
	private int violationId;
	private String content;
	private int type;
	private int userid;
	private String username;
	private Date timestamp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getViolationId() {
		return violationId;
	}
	public void setViolationId(int violationId) {
		this.violationId = violationId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Comment() {
		super();
	}
}
