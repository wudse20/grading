package com.te3.main.exceptions;

/**
 * Used when a name isn't accepted.
 */
public class IllegalNameException extends Exception 
{
	/** Default value*/
	private static final long serialVersionUID = 1L;
	private String message;
	
	public IllegalNameException() {
		this.message = "Name not allowed";
	}
	
	public IllegalNameException(String message) {
		this.message = (message.trim().length() != 0) ? message : "Name not allowed";
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
