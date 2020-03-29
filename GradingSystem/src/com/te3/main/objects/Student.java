package com.te3.main.objects;

import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

/**
 * A Student
 * */
public class Student {
	private String name;

	private ArrayList<Course> courses;

	public Student() {
	}

	public Student(String name) throws IllegalNameException {
		this.setName(name);
		this.setCourses(new ArrayList<Course>());
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalNameException {
		if (name.length() < 3) {
			throw new IllegalNameException("För kort namn");
		} else {
			this.name = name;
		}
	}

	public void setCourses(ArrayList<Course> courses) {
		if (courses == null)
			this.courses = new ArrayList<Course>();

		this.courses = courses;
	}

	public ArrayList<Course> getCourses() {
		try {
			ArrayList<Course> clone = new ArrayList<Course>();
			clone.addAll(this.courses);
			return clone;
		} catch (NullPointerException e) {
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
