package com.warehouse.model;

public class CommandResponse {
	private String message;
	
	public CommandResponse(final String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
