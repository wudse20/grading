package com.te3.main.objects;

import java.util.ArrayList;

public class Group {
	
	private String name;
	
	//in future read course list from data object
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<Student> students = new ArrayList<Student>();
	

	public Group(String name, ArrayList<Course> courses) {
		this.name = name;
		this.courses = courses;
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
