package com.te3.main.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

public class Course {

	private String name;
	private ArrayList<Criteria> courseCriteria;
	private ArrayList<Task> courseTasks;

	public Course() {}
	
	public Course(String name, ArrayList<Criteria> courseCriteria) throws IllegalNameException {
	    this.setName(name);
		this.setCourseCriteria(courseCriteria);
		this.setCourseTasks(new ArrayList<Task>());
	}
	
	public Course(String name, ArrayList<Criteria> courseCriteria, ArrayList<Task> courseTasks) throws IllegalNameException {
		try {
			setName(name);
		} catch (IllegalNameException e) {
			throw new IllegalNameException(e.getMessage());
		}
		setCourseCriteria(courseCriteria);
		setCourseTasks(courseTasks);
	}

	/**
	 * Adds a new course
	 *
	 * @param t the new task
	 * @return {@code true} -> if successful <br>
	 *     	   {@code false} -> if unsuccessful
	 */
	public boolean addTask(Task t) {
		if (t == null)
			return false;

		this.courseTasks.add(t);
		return true;
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

	/**
	 * Clones the course criteria.
	 * 
	 * @return a clone of the course criteria
	 */
	public ArrayList<Criteria> getCourseCriteria() {
		ArrayList<Criteria> clone = new ArrayList<Criteria>();
		try {
			clone.addAll(this.courseCriteria);
		} catch (NullPointerException ex) {
			return new ArrayList<Criteria>();
		}
		return clone;
	}

	public void setCourseCriteria(ArrayList<Criteria> courseCriteria) {
		this.courseCriteria = courseCriteria;
	}

	public ArrayList<Task> getCourseTasks() {
		try {
			ArrayList<Task> clone = new ArrayList<Task>();
			clone.addAll(this.courseTasks);
			return clone;
		} catch (NullPointerException e) {
			return new ArrayList<Task>();
		}
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
	
	@Override
	public String toString() {
		return this.name;
	}
}
