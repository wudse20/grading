package com.te3.main.objects;

import java.util.ArrayList;

public class Data {
	
	private ArrayList<Group> 	groups 	= new ArrayList<Group>();
	private ArrayList<Course> 	courses = new ArrayList<Course>();
	
	public Data() {
		
	}
	
	public Data(ArrayList<Group> groups, ArrayList<Course> courses) {
		this.groups = groups;
		this.courses = courses;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
}
