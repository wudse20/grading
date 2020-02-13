package com.te3.main.objects;

import java.util.ArrayList;

public class SchoolClass {
	
	private String name;
	
	//in future read course list from data object
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<Student> students = new ArrayList<Student>();
	
	public SchoolClass() {}
	
	public SchoolClass(String name, ArrayList<Course> courses, ArrayList<Student> students) {
		this.setName(name);
		this.courses = courses;
		this.students = students;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	public ArrayList<Student> getStudents() {
		return students;
	}
	
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
}
