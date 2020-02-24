package com.te3.main.objects;

import java.util.ArrayList;

import com.te3.main.exceptions.IllegalInputException;

public class Data {
	
	private ArrayList<SchoolClass> 	classes = new ArrayList<SchoolClass>();
	
	private String savePath;
	
	public Data() {}
	
	public Data(ArrayList<SchoolClass> classes, ArrayList<Course> courses) {
		this.classes = classes;
		this.savePath = "./saves.xml";
	}

	public ArrayList<SchoolClass> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<SchoolClass> classes) {
		this.classes = classes;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) throws IllegalInputException {
		if (savePath.trim().equals("")) {
			throw new IllegalInputException("Du måste skriva något i rutan.");
		} else {
			this.savePath = savePath;
		}
	}
}
