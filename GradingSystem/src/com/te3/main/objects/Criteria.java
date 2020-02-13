package com.te3.main.objects;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.te3.main.exceptions.IllegalInputException;

public class Criteria {

	private String name;
	private char grade;

	private JButton[] gradeBtns = new JButton[] { new JButton("F"), new JButton("E"), new JButton("C"),
			new JButton("A") };

	private JLabel lblCriteria;
	private JLabel lblGrade;

	public Criteria() {
	}

	public Criteria(String name) throws IllegalInputException {
		this.setName(name);

		try {
			this.setGrade('F');
		} catch (IllegalInputException e) {
			throw new IllegalInputException(e.getMessage());
		}

		lblCriteria = new JLabel(name);
		lblGrade = new JLabel();

		this.updateGUI(grade);
	}

	private void updateGUI(char grade) {
		switch (grade) {
			case 'F':
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i == 0)
						gradeBtns[i].setBackground(Color.red);
					else
						gradeBtns[i].setBackground(Color.GRAY);
				}

				lblGrade.setBackground(Color.red);
				break;
			case 'E':
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i == 1)
						gradeBtns[i].setBackground(Color.green);
					else
						gradeBtns[i].setBackground(Color.GRAY);
				}

				lblGrade.setBackground(Color.green);
				break;
			case 'C':
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i == 1 || i == 2)
						gradeBtns[i].setBackground(Color.green);
					else
						gradeBtns[i].setBackground(Color.GRAY);
				}

				lblGrade.setBackground(Color.green);
				break;
			case 'A':
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i != 0)
						gradeBtns[i].setBackground(Color.green);
					else
						gradeBtns[i].setBackground(Color.GRAY);
				}

				lblGrade.setBackground(Color.green);
				break;
		}

		lblGrade.setText(Character.toString(grade));
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

	public JButton[] getGradeBtns() {
		return gradeBtns;
	}

	public JLabel getLblCriteria() {
		return lblCriteria;
	}

	public JLabel getLblGrade() {
		return lblGrade;
	}

}
