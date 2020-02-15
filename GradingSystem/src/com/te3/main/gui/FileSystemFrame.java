package com.te3.main.gui;

import java.awt.Container;

import javax.swing.JFrame;

import com.te3.main.exceptions.IllegalInputException;

public class FileSystemFrame extends JFrame {

	/** Default */
	private static final long serialVersionUID = 1L;
	
	private String path;
	private String name;
	
	Container cp = this.getContentPane();
	
	public FileSystemFrame(String name) {
		super("Filsystem");
		
		this.path = "./";
		this.setName(name);
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) throws IllegalInputException {
		if (path.trim().equals("")) {
			throw new IllegalInputException("Path can't be empty!");
		} else {
			this.path = path;
		}
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name.trim().equals("")) {
			this.name = "a";
		} else {
			this.name = name;
		}
	}
}
