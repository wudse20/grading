package com.te3.main.objects;

public class Criteria {
	
	private String name;
	private char grade;
	
	public Criteria() {}
	
	public Criteria(String name) {
		setName(name);
		setGrade('F');
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public char getGrade() {
		return this.grade;
	}
	
	public void setGrade(char grade) {
		if (grade == 'F' || grade == 'E' || grade == 'C' || grade == 'A') {
			this.grade = grade;
		} else {
			//throw error? or set to default
		}
	}

}
