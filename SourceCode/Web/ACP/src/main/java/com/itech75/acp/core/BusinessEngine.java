package com.itech75.acp.core;

import java.util.List;
import java.util.Locale;

import com.itech75.acp.common.ResultBase;
import com.itech75.acp.common.ViolationStates;
import com.itech75.acp.dal.CommentDAL;
import com.itech75.acp.dal.ViolationDAL;
import com.itech75.acp.entities.Violation;
import com.itech75.acp.entities.ViolationData;
import com.itech75.acp.entities.ViolationMeta;

public class BusinessEngine {
	public static ResultBase<String> fixViolation(Violation violation, byte[] photo, int userid){
		if(violation == null){
			return ResultBase.sendError("Parameter is null, violation");
		}
		if(violation.getState() == ViolationStates.OPEN){
			if(ViolationDAL.fixViolation(violation, photo, userid)){
				return ResultBase.sendSuccess("Violation is now fix-candidate");
			}
		}
		return ResultBase.sendError("Unexpected state");
	}
	
	public static ResultBase<String> rejectFix(Violation violation, int userid){
		if(violation == null){
			return ResultBase.sendError("Parameter is null, violation");
		}
		if(violation.getState() == ViolationStates.FIXED_CANDIDATE){
			if(ViolationDAL.rejectFix(violation, userid)){
				return ResultBase.sendSuccess("Violation is now open again");
			}
		}
		return ResultBase.sendError("Unexpected state");
	}
	
	public static ResultBase<String> approveFix(Violation violation, int userid){
		if(violation == null){
			return ResultBase.sendError("Parameter is null, violation");
		}
		if(violation.getState() == ViolationStates.FIXED_CANDIDATE){
			if(ViolationDAL.approveFix(violation, userid)){
				return ResultBase.sendSuccess("Violation is now open again");
			}
		}
		return ResultBase.sendError("Unexpected state");
	}
	
	public static double calculateViolationControlSeverity(ViolationMeta meta, ViolationData data){
		double expected = 0.0;
		double actual = 0.0;
		
		switch (meta.getExpectedValueUnit()) {
		case MM:
		case CM:
		case M:
		case DEGREE:
			expected = Double.parseDouble(meta.getExpectedValue());
			actual = Double.parseDouble(data.getValue());
			break;
		case BOOLEAN:
			expected = 0.0;
			actual = data.getValue().toLowerCase().equals("none") ? 0.0 : 1.0;
			break;
		default:
			expected = 0.0;
			break;
		}
		
		switch (meta.getComparison()) {
		case 0: //Minimum
			if(actual >= expected)return 0.0;
			else{
				double scale = (expected - actual) / expected;
				if(scale < .1)return 1.0;
				else if(scale < .2)return 2.0;
				else if(scale < .3)return 3.0;
				else if(scale < .39)return 3.9;
				else return 4.0;
			}

		case 1: //Maximum
			if(actual <= expected)return 0.0;
			else{
				double scale = expected / actual;
				if(scale > .9)return 1.0;
				else if(scale > .8)return 2.0;
				else if(scale > .7)return 3.0;
				else if(scale > .6)return 3.9;
				else return 4.0;
			}
		case 2: //Boolean
			if(actual == 1.0)return 2.0;
			else return 0.0;

		default:
			break;
		}
		
		return 1.0;
	}

	public static double calculateViolationSeverity(Violation violation) {

		double severity = 0.0;
		int itemCount = violation.getViolationData().size();
		if(itemCount == 0)return 1.0;
		
		for (ViolationData item : violation.getViolationData()) {
			severity += item.getSeverity();
		}
		return severity / itemCount;
	}

	public static List<Violation> findNearbyViolations(String location) {
		String[] latlong = location.split(",");
		double lat = Double.parseDouble(latlong[0]);
		double log = Double.parseDouble(latlong[1]);
		
		String select = ", (6378.137 * SQRT(2 * (1-Cos(RADIANS(Latitude)) * Cos(radians(%f)) * (Sin(RADIANS(Longitude))*Sin(radians(%f)) + Cos(RADIANS(Longitude)) * Cos(radians(%f))) - Sin(RADIANS(Latitude)) * Sin(radians(%f))))) AS Distance ";
		select = String.format(Locale.US, select, lat, log, log, lat);
		String where = " Where (6378.137 * SQRT(2 * (1-Cos(RADIANS(Latitude)) * Cos(radians(%f)) * (Sin(RADIANS(Longitude))*Sin(radians(%f)) + Cos(RADIANS(Longitude)) * Cos(radians(%f))) - Sin(RADIANS(Latitude)) * Sin(radians(%f))))) <= 5 ";
		where = String.format(Locale.US, where, lat, log, log, lat);
		String orderby = " ORDER BY Distance ";
		return ViolationDAL.getViolations(select, where, orderby);
	}
	
	public static boolean addSystemLog(int violationid, String message, int userid){
		return CommentDAL.addSystemLogToComment(violationid, message, userid);
	}
}
