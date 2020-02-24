package com.te3.main.objects;

import java.util.ArrayList;

public class Task {
	
	private String name;
	private boolean isGraded;
	private ArrayList<Criteria> criteria = new ArrayList<Criteria>();
	private ArrayList<Student> students = new ArrayList<Student>();
	
	public Task() {}
	
	public Task(String initName, ArrayList<Criteria> criteria, ArrayList<Student> students) {
		setName(initName);
		setGraded(false);
		setCriteria(criteria);
		setStudents(students);
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

	public ArrayList<Criteria> getCriteria() {
		return criteria;
	}

	public void setCriteria(ArrayList<Criteria> taskGrades) {
		this.criteria = taskGrades;
	}
	
	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public void addTaskGrade(Criteria newGrade) {
		criteria.add(newGrade);
	}
	
	public void removeTaskGrade(Criteria deleteGrade) {
		criteria.remove(deleteGrade);
	}
	
	@Override
	public boolean equals(Object obj) {
		Task t = (Task) obj;
		return (t.name.equals(this.name));
	}
}
