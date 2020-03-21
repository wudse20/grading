package com.te3.main.objects;

import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

public class Student {
	private String name;
    private String comment;

	private ArrayList<Task> tasks;
	
	private short completedTasks;
	private short numOfTasks;
	
	public Student() {}
	
	public Student(String name) throws IllegalNameException {
		this.setName(name);
		this.setTasks(new ArrayList<Task>());
		this.setComment("");
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
	
	/**
	 * Adds a task to the student's tasks.
	 * 
	 * @param t the task if {@code null} then it dosen't do anything.
	 */
	public void addTask(Task t) {
		if (t != null) {
			this.tasks.add(t);
		}
	}

	public String getComment() {
	    if (this.comment == null)
	        return "";

		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public short getCompletedTasks() {
		this.completedTasks = 0;
		
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isGraded()) {
				this.completedTasks++;
			}
		}
		
		return this.completedTasks;
	}

    public ArrayList<Task> getTasks() {
        if (this.tasks == null)
            return new ArrayList<Task>();
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

	/**
	 * Overrides the students amount of completed tasks
	 * @param completedTasks override
	 */
	public void setCompletedTasks(short completedTasks) {
		this.completedTasks = completedTasks;
	}

	public short getNumOfTasks() {
		if (tasks == null)
			return 0;

		this.numOfTasks = (short) this.tasks.size();
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
