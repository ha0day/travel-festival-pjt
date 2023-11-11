package com.ssafy.util;

public class ResultDto {
	
	private String state;
	private String message;
	
	public ResultDto(String state, String message) {
		super();
		this.state = state;
		this.message = message;
	}
	
	public ResultDto() {
		super();
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ResultDto [state=" + state + ", message=" + message + "]";
	}
}
