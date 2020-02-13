package com.te3.main.objects;

import java.util.ArrayList;

public class Task {
	
	private String name;
	private boolean isGraded;
	private ArrayList<Criteria> taskGrades = new ArrayList<Criteria>();
	
	public Task() {}
	
	public Task(String initName, ArrayList<Criteria> taskGrades) {
		setName(initName);
		setGraded(false);
		setTaskGrades(taskGrades);
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

	public ArrayList<Criteria> getTaskGrades() {
		return taskGrades;
	}

	public void setTaskGrades(ArrayList<Criteria> taskGrades) {
		this.taskGrades = taskGrades;
	}
	
	public void addTaskGrade(Criteria newGrade) {
		taskGrades.add(newGrade);
	}
	
	public void removeTaskGrade(Criteria deleteGrade) {
		taskGrades.remove(deleteGrade);
	}
}
