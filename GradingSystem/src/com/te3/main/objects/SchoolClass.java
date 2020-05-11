package com.te3.main.objects;

import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

/**
 * A school class
 */
public class SchoolClass {

	/** The name of the class */
	private String name;

	/** The students */
	private ArrayList<Student> students = new ArrayList<Student>();

	/**
	 * Used in saving
	 */
	public SchoolClass() {
	}

	/**
	 * Sets up a school class with a name and students.
	 *
	 * @param name     the name of the class
	 * @param students the students of the class
	 * @throws IllegalNameException if the name isn't accepted
	 */
	public SchoolClass(String name, ArrayList<Student> students) throws IllegalNameException {
		this.setName(name);
		this.students = students;
	}

	/**
	 * @return the name of the class
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * A setter for name.
	 *
	 * @param name the new name for the class
	 * @throws IllegalNameException if the name isn't accepted (trimmed min 3
	 *                              chars.)
	 */
	public void setName(String name) throws IllegalNameException {
		/*
		 * Checks the length (trimmed) of the input.
		 *
		 * if > 3 then sets the name else throws an exception
		 */
		if (name.trim().length() < 3) {
			throw new IllegalNameException("För kort namn");
		} else {
			this.name = name;
		}
	}

	/**
	 * USE THIS METHOD, NOT {@link #getStudentsNotCloned() getStundentsNotCloned}!<br><br>
	 *
	 * A getter for the students. <br>
	 * Returns a clone of the object.
	 *
	 * @return the students (Cloned)
	 * @see #getStudentsNotCloned()
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Student> getStudents() {
		return (ArrayList<Student>) this.students.clone();
	}

	/**
	 * DO NOT USE THIS METHOD, IF NOT NECESSARY. USE: {@link #getStudents() getStudents} <br><br>
	 *
	 * A getter for the students. <br>
  	 * Does not return a clone of the object
	 *
	 * @return the students (Not Cloned)
	 * @see #getStudents()
	 */
	public ArrayList<Student> getStudentsNotCloned() {
		return this.students;
	}

	/**
	 * A setter for the students.
	 *
	 * @param students the new students
	 */
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	@Override
	public boolean equals(Object obj) {
		SchoolClass s = (SchoolClass) obj;
		return (s.name.equals(this.name));
	}

	@Override
	public String toString() {
		return this.name;
	}
}
