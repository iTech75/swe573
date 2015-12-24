package com.itech75.acp.Entities;

import java.io.Serializable;

import com.itech75.acp.common.Units;

public class ViolationData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6202291506915353146L;
	private int id;
	private int violationId;
	private int violationMetaId;
	private String violationMetaDescription;
	private String value;
	private Units unit;
	
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
	public int getViolationMetaId() {
		return violationMetaId;
	}
	public void setViolationMetaId(int violationMetaId) {
		this.violationMetaId = violationMetaId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Units getUnit() {
		return unit;
	}
	public void setUnit(Units unit) {
		this.unit = unit;
	}
	public void setUnit(String unit) {
		try{
		this.unit = Units.valueOf(unit);
		}
		catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}
	}
	
	public String getViolationMetaDescription() {
		return violationMetaDescription;
	}
	public void setViolationMetaDescription(String violationMetaDescription) {
		this.violationMetaDescription = violationMetaDescription;
	}

	public ViolationData(int id, int violationId, int violationMetaId, String value, Units unit) {
		super();
		this.id = id;
		this.violationId = violationId;
		this.violationMetaId = violationMetaId;
		this.value = value;
		this.unit = unit;
	}
}
