package com.te3.main.objects;

import java.util.ArrayList;

public class Data {
	
	private ArrayList<SchoolClass> 	classes = new ArrayList<SchoolClass>();
	private ArrayList<Course> 		courses = new ArrayList<Course>();
	
	public Data() {
		
	}
	
	public Data(ArrayList<SchoolClass> classes, ArrayList<Course> courses) {
		this.classes = classes;
		this.courses = courses;
	}

	public ArrayList<SchoolClass> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<SchoolClass> classes) {
		this.classes = classes;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
}
