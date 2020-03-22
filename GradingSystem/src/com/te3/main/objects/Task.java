package com.te3.main.objects;

import java.util.ArrayList;

import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.exceptions.IllegalNameException;

public class Task {

	private String name;
	private String comment;
	private ArrayList<Criteria> criteria = new ArrayList<Criteria>();

	public Task() {
	}

	public Task(String name, ArrayList<Criteria> criteria) throws IllegalNameException, IllegalInputException {
		this.setName(name);
		this.setCriteria(criteria);
		this.setComment("");
	}

	public void addCriteria(Criteria criteria) {
		this.criteria.add(criteria);
	}

	public void removeCriteria(Criteria deleteGrade) {
		criteria.remove(deleteGrade);
	}

	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the assignment
	 *
	 * @param name the new name
	 * @throws IllegalNameException if the name is less then 3 characters and it's
	 *                              being trimmed in this test.
	 */
	public void setName(String name) throws IllegalNameException {
		if (name.trim().length() < 3) {
			throw new IllegalNameException("För kort namn!");
		} else {
			this.name = name;
		}
	}

	public ArrayList<Criteria> getCriteria() {
		return criteria;
	}

	public void setCriteria(ArrayList<Criteria> criteria) {
		this.criteria = criteria;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) throws IllegalInputException {
		if (comment.length() < 0 || comment.length() > 50) {
			throw new IllegalInputException("Kommentaren måste vara mellan noll och femtio tecken lång");
		} else {
			this.comment = comment;
		}
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
