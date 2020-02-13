package com.te3.main.objects;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.te3.main.exceptions.IllegalNameException;

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

	public Criteria() {}

	public Criteria(String name) throws IllegalNameException {
		//Ville inte vara i setproperties av någon anledning.
		lblCriteria = new JLabel(name);
		
		try {
			this.setProperties();
		} catch (IllegalNameException e) {
			throw new IllegalNameException(e.getMessage());
		}
		
		this.addActionListeneners();
		this.updateGUI(grade);
	}

	private void setProperties() throws IllegalNameException {
		try {
			this.setName(name);
		} catch (IllegalNameException e) {
			throw new IllegalNameException(e.getMessage());
		}
		
		this.setGrade(Grades.F);

		lblGrade = new JLabel();
		
		gradeBtns[0].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[1].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[2].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[3].setFont(new Font("Dialog", Font.PLAIN, 18));
		
		lblGrade.setFont(new Font("Dialog", Font.BOLD, 18));
		lblCriteria.setFont(new Font("Dialog", Font.BOLD, 18));
	}

	private void addActionListeneners() 
	{
		gradeBtns[0].addActionListener((e) -> {
			btnClicked(Grades.F);
		});
		
		gradeBtns[1].addActionListener((e) -> {
			btnClicked(Grades.E);
		});

		gradeBtns[2].addActionListener((e) -> {
			btnClicked(Grades.C);
		});
		
		gradeBtns[3].addActionListener((e) -> {
			btnClicked(Grades.A);
		});
	}
	
	private void btnClicked(Grades g) {
		this.grade = g;
		this.updateGUI(g);
	}

	private void updateGUI(Grades grade) {
		switch (grade) {
			case F:
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i == 0)
						gradeBtns[i].setForeground(Color.red);
					else
						gradeBtns[i].setForeground(Color.GRAY);
				}

				lblGrade.setForeground(Color.red);
				break;
			case E:
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i == 1)
						gradeBtns[i].setForeground(Color.green);
					else
						gradeBtns[i].setForeground(Color.GRAY);
				}

				lblGrade.setForeground(Color.green);
				break;
			case C:
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i == 1 || i == 2)
						gradeBtns[i].setForeground(Color.green);
					else
						gradeBtns[i].setForeground(Color.GRAY);
				}

				lblGrade.setForeground(Color.green);
				break;
			case A:
				for (int i = 0; i < gradeBtns.length; i++) {
					if (i != 0)
						gradeBtns[i].setForeground(Color.green);
					else
						gradeBtns[i].setForeground(Color.GRAY);
				}

				lblGrade.setForeground(Color.green);
				break;
		}

		lblGrade.setText(grade.toString());
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) throws IllegalNameException {
		if (name.length() > 3)
			this.name = name;
		else
			throw new IllegalNameException("För kort meddelande");
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
