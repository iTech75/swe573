package com.itech75.acp.Entities;

import Common.Units;

public class ViolationMeta {
	private int id;
	private int violationTypeId;
	private String description;
	private int type;
	private int comparison;
	private String expectedValue;
	private Units expectedValueUnit;
	private String selection;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getViolationTypeId() {
		return violationTypeId;
	}
	public void setViolationTypeId(int violationTypeId) {
		this.violationTypeId = violationTypeId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getComparison() {
		return comparison;
	}
	public void setComparison(int comparison) {
		this.comparison = comparison;
	}
	public String getExpectedValue() {
		return expectedValue;
	}
	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}
	public Units getExpectedValueUnit() {
		return expectedValueUnit;
	}
	public void setExpectedValueUnit(Units expectedValueUnit) {
		this.expectedValueUnit = expectedValueUnit;
	}

	public void setExpectedValueUnit(String expectedValueUnit) {
		try{
		  this.expectedValueUnit = Units.valueOf(expectedValueUnit);
		}
		catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}
	}

	public String getSelection() {
		return selection;
	}
	public void setSelection(String selection) {
		this.selection = selection;
	}
	public ViolationMeta(int id, int violationTypeId, String description, int type, int comparison,
			String expectedValue, String expectedValueUnit, String selection) {
		super();
		this.id = id;
		this.violationTypeId = violationTypeId;
		this.description = description;
		this.type = type;
		this.comparison = comparison;
		this.expectedValue = expectedValue;
		this.expectedValueUnit = Units.valueOf(expectedValueUnit);
		this.selection = selection;
	}
}
