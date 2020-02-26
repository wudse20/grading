package com.te3.main.objects;

import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

public class SchoolClass {
	
	private String name;
	
	//in future read course list from data object
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<Student> students = new ArrayList<Student>();
	
	public SchoolClass() {}
	
	public SchoolClass(String name, ArrayList<Student> students) throws IllegalNameException {
		this.setName(name);
		this.courses = new ArrayList<Course>();
		this.students = students;
	}
	
	public SchoolClass(String name, ArrayList<Course> courses, ArrayList<Student> students) throws IllegalNameException {
		this.setName(name);
		this.courses = courses;
		this.students = students;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalNameException {
		if (name.length() < 3) {
			throw new IllegalNameException("För kort namn");
		} else {
			this.name = name;
		}
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
	
	@Override
	public boolean equals(Object obj) {
		SchoolClass s = (SchoolClass) obj;
		return (s.name.equals(this.name));
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
