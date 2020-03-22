package com.te3.main.objects;

import java.util.ArrayList;

public class Data {

	private ArrayList<SchoolClass> classes = new ArrayList<SchoolClass>();

	public Data() {
	}

	public Data(ArrayList<SchoolClass> classes) {
		this.classes = classes;
	}

	public ArrayList<SchoolClass> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<SchoolClass> classes) {
		this.classes = classes;
	}
}
