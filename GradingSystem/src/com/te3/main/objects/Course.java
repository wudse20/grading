package com.te3.main.objects;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

/**
 * A course
 */
public class Course {

	/** The name of the course */
	private String name;

	/** The list of course criteria */
	private ArrayList<Criteria> courseCriteria;

	/** The course assignments */
	private ArrayList<Task> courseTasks;

	/** The constructor used in saving. */
	public Course() {
	}

	/**
	 * Sets up the course with a name and criteria.
	 *
	 * @param name           The name of the course
	 * @param courseCriteria the course criteria
	 * @throws IllegalNameException If the name isn't accepted
	 */
	public Course(String name, ArrayList<Criteria> courseCriteria) throws IllegalNameException {
		// Sets the name
		this.setName(name);

		// Sets the criteria
		this.setCourseCriteria(courseCriteria);

		// Sets the tasks, creates an empty ArrayList
		this.setCourseTasks(new ArrayList<Task>());
	}

	/**
	 * Sets up a course with criteria, tasks and a name
	 *
	 * @param name           the name of the course
	 * @param courseCriteria the criteria
	 * @param courseTasks    the tasks
	 * @throws IllegalNameException If the name isn't accepted
	 */
	public Course(String name, ArrayList<Criteria> courseCriteria, ArrayList<Task> courseTasks)
			throws IllegalNameException {
		try {
			// Sets the name
			setName(name);
		} catch (IllegalNameException e) {
			// Throws the exception with the received message
			throw new IllegalNameException(e.getMessage());
		}

		// This sets the course criteria
		this.setCourseCriteria(courseCriteria);

		// Sets the course tasks
		this.setCourseTasks(courseTasks);
	}

	/**
	 * Adds a new course task
	 *
	 * @param t the new task
	 * @return {@code true} -> if successful <br>
	 *         {@code false} -> if unsuccessful
	 */
	public boolean addTask(Task t) {
		// If the task is null then it returns false
		if (t == null)
			return false;

		// Adds the task
		this.courseTasks.add(t);

		// Returns true
		return true;
	}

	/**
	 * Removes a task
	 *
	 * @param deletedTask the task to be removed
	 */
	public void removeTask(Task deletedTask) {
		// Removes the task.
		courseTasks.remove(deletedTask);
	}

	/**
	 * A getter for the name.
	 *
	 * @return The name of the course
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * A setter for the name.
	 *
	 * @param name the new name
	 * @throws IllegalNameException if the new name isn't accepted (less then 3
	 *                              characters trimmed)
	 */
	public void setName(String name) throws IllegalNameException {
		// Throws an exception with a message if the name is to short.
		if (name.trim().length() < 3) {
			throw new IllegalNameException("Name too short");
		} else {
			// Sets the name
			this.name = name;
		}
	}

	/**
	 * Clones the course criteria.
	 * 
	 * @return a clone of the course criteria
	 */
	public ArrayList<Criteria> getCourseCriteria() {
		// Creates the clone list
		ArrayList<Criteria> clone = new ArrayList<Criteria>();
		try {
			// Clones the list
			clone.addAll(this.courseCriteria);
		} catch (NullPointerException ex) {
			// Returns a empty list
			return new ArrayList<Criteria>();
		}

		// Returns the clone
		return clone;
	}

	/**
	 * A setter for the course criteria
	 *
	 * @param courseCriteria the criteria
	 */
	public void setCourseCriteria(ArrayList<Criteria> courseCriteria) {
		// Stores the course criteria
		this.courseCriteria = courseCriteria;
	}

	/**
	 * Clones the course tasks.
	 *
	 * @return the course tasks
	 */
	public ArrayList<Task> getCourseTasks() {
		try {
			// Defines a new list
			ArrayList<Task> clone = new ArrayList<Task>();

			// Clones the courses
			clone.addAll(this.courseTasks);

			// Returns the list
			return clone;
		} catch (NullPointerException e) {
			// Returns an empty list
			return new ArrayList<Task>();
		}
	}

	/**
	 * A getter for the course tasks. <br>
	 * This will create a new instance of task <br>
	 * So all the grades will be F, but have the same name.<br>
	 * {@link #getCourseTasks()} () Use this method for: normal getter for the tasks}
	 *
	 * @return the tasks (only names stay the same)
	 */
	public ArrayList<Task> getNewCourseTask() {
		//Creates a new ArrayList for the created criteria
		ArrayList<Task> newCriteria = new ArrayList<Task>();

		//Loops through this course's criteria.
		for (int i = 0; i < this.courseTasks.size(); i++) {
			try {
				//Creates the new criteria with the same name.
				newCriteria.add(new Task(courseTasks.get(i).getName(), courseTasks.get(i).getNewCriteria()));

				//A debug message
				System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] Course: Created a new task with the name: " + this.courseCriteria.get(i).getName());
			} catch (IllegalNameException e) {
				//Prints stack trace to the console.
				e.printStackTrace();

				System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] Course: Exception thrown: " + e.getMessage());
			}
		}

		//Returns the newly created criteria
		return newCriteria;
	}

	/**
	 * A setter for the tasks.
	 *
	 * @param courseTasks the tasks
	 */
	public void setCourseTasks(ArrayList<Task> courseTasks) {
		// Stores the tasks
		this.courseTasks = courseTasks;
	}

	@Override
	public boolean equals(Object obj) {
		Course c = (Course) obj;
		return (this.name.equals(c.name));
	}

	@Override
	public String toString() {
		return this.name;
	}
}
