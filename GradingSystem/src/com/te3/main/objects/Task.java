package com.te3.main.objects;

import java.util.ArrayList;

public class Task {
	
	private String name;
	private boolean isGraded;
	private ArrayList<TaskGrade> taskGrades = new ArrayList<TaskGrade>();
	
	public Task(String initName) {
		this.setName(initName);
		this.setGraded(false);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGraded() {
		return isGraded;
	}

	public void setGraded(boolean isGraded) {
		this.isGraded = isGraded;
	}

	public ArrayList<TaskGrade> getTaskGrades() {
		return taskGrades;
	}

	public void setTaskGrades(ArrayList<TaskGrade> taskGrades) {
		this.taskGrades = taskGrades;
	}
}
