package com.itech75.acp.entities;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itech75.acp.dal.ViolationDataDAL;
import com.itech75.acp.dal.ViolationTypeDAL;

public class Violation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7358924605063025991L;
	private int id;
	private String type;
	private String title;
	private String description;
	private byte[] image;
	private String imageBase64;
    private Date timeStamp;
	private double latitude;
	private double longitude;
	private int userId;
	private String userName;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	private List<ViolationData> violationData;
	public List<ViolationData> getViolationData()
	{
		if(violationData == null){
			violationData = ViolationDataDAL.getViolationDataList(getId());
		}
		return violationData;
	}
	
	private ViolationType violationType;
	public ViolationType getViolationType() throws SQLException{
		if(violationType == null){
			violationType = ViolationTypeDAL.getViolationType(type);
		}
		return violationType;
	}
	
	public Violation(){

	}
	
	public Violation(int id, String type, String title, String description, byte[] image, Date timestamp, 
			double latitude, double longitude, int userId) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.description = description;
		this.image = image;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeStamp = timestamp;
		this.userId = userId;
		
		violationData = ViolationDataDAL.getViolationDataList(id);
	}
	
	public String getSince(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		return formatter.format(getTimeStamp());
	}
	
	public String getLocation(){
		return String.format("%s, %s", getLatitude(), getLongitude());
	}
}