package com.te3.main.objects;

import java.time.LocalTime;
import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

/**
 * A task
 */
public class Task {

	/** The name of the task */
	private String name;

	/** The comment of the task */
	private String comment;

	/** The criteria */
	private ArrayList<Criteria> criteria = new ArrayList<Criteria>();

	/**
	 * Used for saving.
	 */
	public Task() {
	}

	/**
	 * Creates a Task with a name and <br>
	 * a list of criteria
	 *
	 * @param name     the name of the course
	 * @param criteria the criteria
	 * @throws IllegalNameException if the name isn't accepted
	 */
	public Task(String name, ArrayList<Criteria> criteria) throws IllegalNameException {
		this.setName(name);
		this.setCriteria(criteria);
		this.setComment("");
	}

	/**
	 * Adds a criteria
	 *
	 * @param criteria the new criteira
	 */
	public void addCriteria(Criteria criteria) {
		this.criteria.add(criteria);
	}

	/**
	 * Removes a criteria
	 *
	 * @param deleteGrade the criteria to be deleted
	 */
	public void removeCriteria(Criteria deleteGrade) {
		criteria.remove(deleteGrade);
	}

	/**
	 * A getter for the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * A setter for the name
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

	/**
	 * A getter for the criteria
	 *
	 * @return the criteria
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Criteria> getCriteria() {
		return (ArrayList<Criteria>) this.criteria.clone();
	}

	/**
	 * A getter for the course criteria. <br>
	 * This will create a new instance of criteria <br>
	 * So all the grades will be F, but have the same name.<br>
	 * {@link #getCriteria()} () Use this method for: normal getter for the criteria}
	 *
	 * @return the criteria (only names stay the same)
	 */
	public ArrayList<Criteria> getNewCriteria() {
		//Creates a new ArrayList for the created criteria
		ArrayList<Criteria> newCriteria = new ArrayList<Criteria>();

		//Loops through this course's criteria.
		for (int i = 0; i < this.criteria.size(); i++) {
			try {
				//Creates the new criteria with the same name.
				newCriteria.add(new Criteria(this.criteria.get(i).getName()));

				//A debug message
				System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] Task: Created a new criteria with the name: " + this.criteria.get(i).getName());
			} catch (IllegalNameException e) {
				//Prints stack trace to the console.
				e.printStackTrace();

				System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] Task: Exception thrown: " + e.getMessage());
			}
		}

		//Returns the newly created criteria
		return newCriteria;
	}

	/**
	 * A setter for the criteria
	 *
	 * @param criteria the new criteria
	 */
	public void setCriteria(ArrayList<Criteria> criteria) {
		this.criteria = criteria;
	}

	/**
	 * A getter for the comment
	 *
	 * @return the comment
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * A setter for the comment
	 *
	 * @param comment the comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
