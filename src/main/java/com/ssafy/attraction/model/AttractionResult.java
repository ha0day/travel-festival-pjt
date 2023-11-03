package com.ssafy.attraction.model;

public class AttractionResult {
	
	private String state;
	private String message;
	
	public AttractionResult(String state, String message) {
		super();
		this.state = state;
		this.message = message;
	}
	
	public AttractionResult() {
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
		return "AttractionResult [state=" + state + ", message=" + message + "]";
	}
}
