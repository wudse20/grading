package com.te3.main.objects;

import com.te3.main.exceptions.IllegalNameException;

import java.util.ArrayList;

public class Task {
	
	private String name;
	private boolean isGraded;
	private ArrayList<Criteria> criteria = new ArrayList<Criteria>();
	private ArrayList<Student> students = new ArrayList<Student>();
	
	public Task() {}
	
	public Task(String initName, ArrayList<Criteria> criteria) throws IllegalNameException {
		setName(initName);
		setGraded(false);
		setCriteria(criteria);
	}
	
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the assignment
	 *
	 * @param name the new name
	 * @throws IllegalNameException if the name is less then 3 characters and it's being trimmed in this test.
	 * */
	public void setName(String name) throws IllegalNameException {
		if (name.trim().length() < 3) {
			throw new IllegalNameException("För kort namn!");
		} else {
			this.name = name;
		}
	}

	public boolean isGraded() {
		return isGraded;
	}

	public void setGraded(boolean isGraded) {
		this.isGraded = isGraded;
	}

	public ArrayList<Criteria> getCriteria() {
		return criteria;
	}

	public void setCriteria(ArrayList<Criteria> taskGrades) {
		this.criteria = taskGrades;
	}

	public void addTaskGrade(Criteria newGrade) {
		criteria.add(newGrade);
	}
	
	public void removeTaskGrade(Criteria deleteGrade) {
		criteria.remove(deleteGrade);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		Task t = (Task) obj;
		return (t.name.equals(this.name));
	}
}
