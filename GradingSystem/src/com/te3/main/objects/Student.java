package com.te3.main.objects;

public class Student {
	private String name;
	private String comment;
	private short completedTasks;
	private short numOfTasks;
	
	public Student() {}
	
	public Student(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
