package com.itech75.acp.Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itech75.acp.DAL.ViolationDataDAL;

public class Violation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7358924605063025991L;
	private int id;
	private String title;
	private String description;
	private byte[] image;
	private String imageBase64;
    private Date timeStamp;
	private String userName;
	private double latitude;
	private double longitude;
	private int type;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getImageBase64() {
		return imageBase64;
	}
	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	private List<ViolationData> violationData;
	public List<ViolationData> getViolationData()
	{
		return violationData;
	}
	
	public Violation(){
		violationData = new ArrayList<ViolationData>();
	}
	
	public Violation(int id, String title, String description, byte[] image, String imageBase64, String userName, double latitude,
			double longitude, Date timestamp) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.image = image;
		this.imageBase64 = imageBase64;
		this.userName = userName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeStamp = timestamp;
		this.type = 0;
		violationData = ViolationDataDAL.getViolationDataList(id);
	}
	
	public String getSince(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		return formatter.format(getTimeStamp());
	}
}