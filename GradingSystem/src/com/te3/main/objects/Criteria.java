package com.te3.main.objects;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.te3.main.exceptions.IllegalInputException;

public class Criteria {

	enum Grades {
		F, E, C, A;
	}
	
	private String name;
	private Grades grade;

	private JButton[] gradeBtns = new JButton[] { new JButton("F"), new JButton("E"), new JButton("C"),
			new JButton("A") };

	private JLabel lblCriteria;
	private JLabel lblGrade;

	public Criteria() {
	}

	public Criteria(String name) throws IllegalInputException {
		this.setName(name);

		this.setGrade(Grades.F);

		lblCriteria = new JLabel(name);
		lblGrade = new JLabel();

		this.updateGUI(grade);
	}

	private void updateGUI(Grades grade) {
		switch (grade) {
			case F:
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i == 0)
						gradeBtns[i].setBackground(Color.red);
					else
						gradeBtns[i].setBackground(Color.GRAY);
				}

				lblGrade.setBackground(Color.red);
				break;
			case E:
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i == 1)
						gradeBtns[i].setBackground(Color.green);
					else
						gradeBtns[i].setBackground(Color.GRAY);
				}

				lblGrade.setBackground(Color.green);
				break;
			case C:
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i == 1 || i == 2)
						gradeBtns[i].setBackground(Color.green);
					else
						gradeBtns[i].setBackground(Color.GRAY);
				}

				lblGrade.setBackground(Color.green);
				break;
			case A:
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i != 0)
						gradeBtns[i].setBackground(Color.green);
					else
						gradeBtns[i].setBackground(Color.GRAY);
				}

				lblGrade.setBackground(Color.green);
				break;
		}

		lblGrade.setText(grade.toString());
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Grades getGrade() {
		return this.grade;
	}

	public void setGrade(Grades grade) {
		this.grade = grade;
	}

	public JButton[] getGradeBtns() {
		return gradeBtns;
	}

	public JLabel getLblCriteria() {
		return lblCriteria;
	}

	public JLabel getLblGrade() {
		return lblGrade;
	}
	
	/**
	 * Compares two grades
	 * 
	 * @param c the other criteria
	 * @return the criteria with the highest grade
	 */
	public Criteria compare(Criteria c) {
		if (grade.ordinal() > c.grade.ordinal())
			return this;
		else
			return c;
	}
	
	/**
	 * Checks if two criteria are the same.
	 * 
	 * @param c the other criteria
	 * @return <b>true</b> if the same name, else <b>false</b>
	 */
	public boolean isSameCriteria(Criteria c) {
		return (this.name.equals(c.name));
	}

}
