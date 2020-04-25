package com.te3.main.objects;

import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

/**
 * A Student
 * */
public class Student {
    /** The name of the student */
	private String name;

	/** The courses the student is a part of */
	private ArrayList<Course> courses;

	/**
     * Used for saving
     * */
	public Student() {
	}

    /**
     * Creates a student with a name and an empty list of courses.
     *
     * @param name the name of the student
     * @throws IllegalNameException If the name isn't accepted
     */
	public Student(String name) throws IllegalNameException {
		this.setName(name);
		this.setCourses(new ArrayList<Course>());
	}

    /**
     * Creates a student with a name and a list of courses.
     *
     * @param name the name of the student
     * @param courses the courses that the student is a part of
     * @throws IllegalNameException if the name isn't accepted
     */
	public Student(String name, ArrayList<Course> courses) throws IllegalNameException {
		this.setName(name);
		this.setCourses(courses);
	}

	/**
	 * Adds a new course to the student<br>
	 * <br>
	 * if c = {@code null} then it will return without doing anything.
	 *
	 * @param c the new course
	 */
	public void addCourse(Course c) {
		if (c == null)
			return;
		else if (this.courses == null)
			this.courses = new ArrayList<Course>();

		this.courses.add(c);
	}

    /**
     * A getter for the name
     *
     * @return the name of the student
     */
	public String getName() {
		return this.name;
	}

    /**
     * A setter for the name
     *
     * @param name the new name
     * @throws IllegalNameException if the trimmed name is too short (> 3 characters)
     */
	public void setName(String name) throws IllegalNameException {
	    /*
	     * Checks if the name is ok,
	     * if it is then it will set
	     * the new name else if will
	     * throw an error with a msg
	     * */
		if (name.trim().length() < 3) {
			throw new IllegalNameException("För kort namn");
		} else {
			this.name = name;
		}
	}

    /**
     * A setter for the courses
     *
     * @param courses the list of courses, if {@code null} then it will set an empty list
     */
	public void setCourses(ArrayList<Course> courses) {
		//Checks if the courses are null
	    if (courses == null) {
            this.courses = new ArrayList<Course>();
            return;
        }

	    //Sets the courses.
		this.courses = courses;
	}

    /**
     * A getter for the courses
     *
     * @return the cloned list
     */
	public ArrayList<Course> getCourses() {
		try {
		    //Creates a clone list
			ArrayList<Course> clone = new ArrayList<Course>();

			//Clones the list
			clone.addAll(this.courses);

			//Returns the list
			return clone;
		} catch (NullPointerException e) {
		    //Returns a empty list
			return new ArrayList<Course>();
		}
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
