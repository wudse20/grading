package com.te3.main.exceptions;

public class IllegalInputException extends Exception{
	
	private String message;
	
	public IllegalInputException() {
		this.message = "Illegal input";
	}
	
	public IllegalInputException(String message) {
		this.message = (message.trim().equals("")) ? "Illegal input" : message;
	}
	
	@Override
	public String getMessage() 
	{
		return this.message;
	}
}
