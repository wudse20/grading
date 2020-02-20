package com.te3.main.exceptions;

public class IllegalInputException extends Exception{
	
	/** Default */
	private static final long serialVersionUID = 1L;
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
