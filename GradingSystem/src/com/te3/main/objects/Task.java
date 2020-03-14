package com.te3.main.objects;

import com.te3.main.enums.Grade;
import com.te3.main.exceptions.IllegalNameException;

import java.util.ArrayList;

public class Task {
	
	private String name;
	private boolean isGraded;
	private ArrayList<Criteria> criteria = new ArrayList<Criteria>();
	/**
	 * ??? Vad skulle vi med denna till ???
	 */
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
		for (int i = 0; i < criteria.size(); i++) {
			if (!(criteria.get(i).getGrade().equals(Grade.F))) {
				this.isGraded = true;
				break;
			}
			else if (criteria.size() - 1 == i) {
				this.isGraded = false;
			}
		}
		
		return this.isGraded;
	}

	public void setGraded(boolean isGraded) {
		this.isGraded = isGraded;
	}

	public ArrayList<Criteria> getCriteria() {
		return criteria;
	}

	public void setCriteria(ArrayList<Criteria> criteria) {
		this.criteria = criteria;
	}

	public void addCriteria(Criteria criteria) {
		this.criteria.add(criteria);
	}
	
	public void removeCriteria(Criteria deleteGrade) {
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
