package com.itech75.acp.common;

public class ResultBase<T> {
	private T result;
	
	private ReturnStatusCodes statusCode;
	private String message;
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public ReturnStatusCodes getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(ReturnStatusCodes statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResultBase(T result, ReturnStatusCodes statusCode, String message) {
		super();
		this.result = result;
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public ResultBase() {
		super();
	}
	
	public static <T> ResultBase<T> sendSuccess(T result) {
		return new ResultBase<T>(result, ReturnStatusCodes.SUCCESS, null);				
	}
	
	public static <T> ResultBase<T> sendSuccess(T result, String message) {
		return new ResultBase<T>(result, ReturnStatusCodes.SUCCESS, message);				
	}
	
	public static <T> ResultBase<T> sendError(String message) {
		return new ResultBase<T>(null, ReturnStatusCodes.ERROR, message);				
	}
}
