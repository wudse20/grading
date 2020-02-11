package com.te3.main.objects;

import java.util.ArrayList;

import com.te3.main.exceptions.IllegalNameException;

public class Course {

	String name;
	ArrayList<Student> students;
	ArrayList<Task> tasks;

	public Course(String name, ArrayList<Student> students, ArrayList<Task> tasks) {
		this.name = name;
		this.students = students;
		this.tasks = tasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalNameException {
		if (name.length() < 3) {
			throw new IllegalNameException("Name to short");
		} else {
			this.name = name;
		}
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

}
