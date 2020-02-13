package com.te3.main.objects;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.te3.main.exceptions.IllegalInputException;

public class Criteria {
	
	private String name;
	private char grade;
	
	private JButton[] gradeBtns = 
			new JButton[] {new JButton("F"), new JButton("E"), new JButton("C"), new JButton("A")};
	
	private JLabel criteria;
	
	public Criteria() {}
	
	public Criteria(String name) throws IllegalInputException {
		this.setName(name);
		try {
			this.setGrade('F');
		} catch (IllegalInputException e) {
			throw new IllegalInputException(e.getMessage());
		}
		
		criteria = new JLabel(name);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public char getGrade() {
		return this.grade;
	}
	
	public void setGrade(char grade) throws IllegalInputException {
		if (grade == 'F' || grade == 'E' || grade == 'C' || grade == 'A') {
			this.grade = grade;
		} else {
			throw new IllegalInputException("Inget betyg");
		}
	}

}
