package com.itech75.acp.entities;

public class ViolationType {
	private String id;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public ViolationType(){
		
	}
	public ViolationType(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}
}