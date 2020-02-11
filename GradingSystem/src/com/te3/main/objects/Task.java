package com.te3.main.objects;

import java.util.ArrayList;

public class Task {
	ArrayList<TaskGrade> assignedTaskGrades = new ArrayList<TaskGrade>();
	
	public Task() {
		
	}
	
	private void addTask(TaskGrade newTask) {
		assignedTaskGrades.add(newTask);
	}
	
	private void removeTask(TaskGrade deletedTask) {
		assignedTaskGrades.remove(deletedTask);
	}
}
