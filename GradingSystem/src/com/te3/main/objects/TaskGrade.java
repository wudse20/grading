package com.te3.main.objects;

public class TaskGrade {
	//variables
	private String name;
	private char grade;
	
	//constructor
	public TaskGrade(String initName) {
		this.name = initName;
		this.grade = 'F';
	}
	
	//getters
	public String getName() {
		return this.name;
	}
	
	public char getGrade() {
		return this.grade;
	}

	//setters
	public void setName(String name) {
		this.name = name;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}

}
