package com.te3.main.objects;

import java.util.ArrayList;

public class Course {

	private String name;
	private ArrayList<SchoolClass> linkedClasses;
	private ArrayList<Criteria> courseCriteria;
	private ArrayList<Task> courseTasks;

	public Course(String name, ArrayList<SchoolClass> classes, ArrayList<Criteria> courseCriteria) {
		setName(name);
		setLinkedClasses(classes);
		setCourseCriteria(courseCriteria);
	}
	
	public Course(String name, ArrayList<SchoolClass> classes, ArrayList<Criteria> courseCriteria, ArrayList<Task> courseTasks) {
		setName(name);
		setLinkedClasses(classes);
		setCourseCriteria(courseCriteria);
		setCourseTasks(courseTasks);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name.length() < 3) {
			this.name = "Kasta fel sen";
		} else {
			this.name = name;
		}
	}

	public ArrayList<SchoolClass> getLinkedClasses() {
		return linkedClasses;
	}

	public void setLinkedClasses(ArrayList<SchoolClass> linkedClasses) {
		this.linkedClasses = linkedClasses;
	}

	public ArrayList<Criteria> getCourseCriteria() {
		return courseCriteria;
	}

	public void setCourseCriteria(ArrayList<Criteria> courseCriteria) {
		this.courseCriteria = courseCriteria;
	}

	public ArrayList<Task> getCourseTasks() {
		return courseTasks;
	}

	public void setCourseTasks(ArrayList<Task> courseTasks) {
		this.courseTasks = courseTasks;
	}
	
	public void addCourseTask(Task newTask) {
		courseTasks.add(newTask);
	}
	
	public void removeCourseTask(Task deletedTask) {
		courseTasks.remove(deletedTask);
	}
}
