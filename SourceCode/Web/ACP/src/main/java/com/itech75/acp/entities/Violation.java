package com.itech75.acp.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itech75.acp.common.SeverityLevels;
import com.itech75.acp.common.ViolationStates;
import com.itech75.acp.dal.CommentDAL;
import com.itech75.acp.dal.ViolationDAL;
import com.itech75.acp.dal.ViolationDataDAL;
import com.itech75.acp.dal.VoteDAL;

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
	private double latitude;
	private double longitude;
	private int userId;
	private String userName;
	private double severity;
	private double publicSeverity;
	private ViolationStates state;

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
	
	public double getSeverity() {
		return severity;
	}
	public SeverityLevels getSeverityAsEnum(){
		return SeverityLevels.values()[(int)severity];
	}
	public int getSeverityAsPercent(){
		return (int)(severity / 4.0 * 100.0);
	}
	public int getSeverityForSection(int section){
		int result =   section * 25 - getSeverityAsPercent();
		result = result < 0 ? 25 : 25 - result;
		result = Math.max(result, 0);
		return result;
	}
	public void setSeverity(double severity) {
		this.severity = severity;
	}
	public double getPublicSeverity() {
		return publicSeverity;
	}
	public SeverityLevels getPublicSeverityAsEnum(){
		return SeverityLevels.values()[(int)publicSeverity];
	}
	public int getPublicSeverityAsPercent(){
		return (int)(publicSeverity / 4.0 * 100.0);
	}
	public int getPublicSeverityForSection(int section){
		int result =   section * 25 - getPublicSeverityAsPercent();
		result = result < 0 ? 25 : 25 - result;
		result = Math.max(result, 0);
		return result;
	}
	public void setPublicSeverity(double publicSeverity) {
		this.publicSeverity = publicSeverity;
	}
	public ViolationStates getState() {
		return state;
	}
	public void setState(ViolationStates state) {
		this.state = state;
	}
	public boolean removeViolationData(int id){
		List<ViolationData> input = getViolationData();
		return input.removeIf(p -> p.getId() == id);
	}
	
	private List<Comment> comments;
	public List<Comment> getComments(){
		if(comments == null){
			comments = CommentDAL.loadCommentsForViolation(id);
		}
		return comments;
	}
	
	public Violation(){

	}
	
//	public Violation(int id, String title, String description, byte[] image, Date timestamp, 
//			double latitude, double longitude, int userId) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.description = description;
//		this.image = image;
//		this.latitude = latitude;
//		this.longitude = longitude;
//		this.timeStamp = timestamp;
//		this.userId = userId;
//		
//		violationData = ViolationDataDAL.getViolationDataList(id);
//	}
	
	public String getSince(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		return formatter.format(getTimeStamp());
	}
	
	public String getLocation(){
		return String.format("%s, %s", getLatitude(), getLongitude());
	}
	
	public byte[] getFixImage(){
		return ViolationDAL.getViolationFixImage(id);
	}

	private int myVote = -99;
	public int getVoteForUser(int userid){
		if(myVote == -99){
			myVote = VoteDAL.getVoteForViolation(id, userid);
		}
		return myVote;
	}
	
	public void clearVoteForUser(){
		myVote = -99;
	}
}