package com.te3.main.exceptions;

/**
 * When a folder was unsuccessful in its creation.
 */
public class UnsucessfulFolderCreationException extends Exception {

	/** Default */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public UnsucessfulFolderCreationException() {
		this.message = "Något gick fel";
	}
	
	public UnsucessfulFolderCreationException(String message) {
		this.message = (message.trim().equals("")) ? "Något gick fel" : message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
