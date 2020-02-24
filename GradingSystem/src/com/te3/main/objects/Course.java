package com.te3.main.objects;

import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

public class Course {

	private String name;
	private ArrayList<SchoolClass> linkedClasses;
	private ArrayList<Criteria> courseCriteria;
	private ArrayList<Task> courseTasks;

	public Course() {}
	
	public Course(String name, ArrayList<SchoolClass> classes, ArrayList<Criteria> courseCriteria) throws IllegalNameException {
		try {
			setName(name);
		} catch (IllegalNameException e) {
			throw new IllegalNameException(e.getMessage());
		}
		setLinkedClasses(classes);
		setCourseCriteria(courseCriteria);
		setCourseTasks(new ArrayList<Task>());
	}
	
	public Course(String name, ArrayList<SchoolClass> classes, ArrayList<Criteria> courseCriteria, ArrayList<Task> courseTasks) throws IllegalNameException {
		try {
			setName(name);
		} catch (IllegalNameException e) {
			throw new IllegalNameException(e.getMessage());
		}
		setLinkedClasses(classes);
		setCourseCriteria(courseCriteria);
		setCourseTasks(courseTasks);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalNameException {
		if (name.length() < 3) {
			throw new IllegalNameException("Name too short");
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
	
	@Override
	public boolean equals(Object obj) {
		Course c = (Course) obj;
		return (this.name.equals(c.name));
	}
}
