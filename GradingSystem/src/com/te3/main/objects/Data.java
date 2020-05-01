package com.te3.main.objects;

import java.util.ArrayList;

/**
 * The object that holds all the data in the program
 */
public class Data {

	/** The ArrayList with all the classes */
	private ArrayList<SchoolClass> classes = new ArrayList<SchoolClass>();

	/**
	 * Used in saving
	 */
	public Data() {
	}

	/**
	 * Sets up the data object with a list of classes
	 *
	 * @param classes the list with the classes
	 */
	public Data(ArrayList<SchoolClass> classes) {
		this.classes = classes;
	}

	/**
	 * Removes a SchoolClass from the list.
	 *
	 * @param sc the SchoolClass to be removed
	 */
	public void removeClass(SchoolClass sc) {
		this.classes.remove(sc);
	}

	/**
	 * A getter for the classes
	 *
	 * @return the classes
	 */
	public ArrayList<SchoolClass> getClasses() {
		return this.classes;
	}

	/**
	 * A setter for the classes
	 *
	 * @param classes the new classes
	 */
	public void setClasses(ArrayList<SchoolClass> classes) {
		this.classes = classes;
	}
}
