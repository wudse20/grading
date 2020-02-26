package com.te3.main.objects;

import com.te3.main.exceptions.IllegalNameException;

public class Student {
	private String name;
	private String comment;
	private short completedTasks;
	private short numOfTasks;
	
	public Student() {}
	
	public Student(String name) throws IllegalNameException {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalNameException{
		if (name.length() < 3) {
			throw new IllegalNameException("För kort namn");
		} else {
			this.name = name;
		}
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public short getCompletedTasks() {
		return completedTasks;
	}

	/**
	 * Calculates the students amount of completed tasks
	 */
	public void setCompletedTasks() {
		this.completedTasks = 0;
	}
	
	/**
	 * Overrides the students amount of completed tasks
	 * @param completedTasks override
	 */
	public void setCompletedTasks(short completedTasks) {
		this.completedTasks = completedTasks;
	}

	public short getNumOfTasks() {
		return numOfTasks;
	}

	public void setNumOfTasks(short numOfTasks) {
		this.numOfTasks = numOfTasks;
	}
	
	@Override
	public boolean equals(Object obj) {
		Student s = (Student) obj;
		return (s.name.equals(this.name));
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
