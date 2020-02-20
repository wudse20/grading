package com.te3.main.objects;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.te3.main.enums.Grades;
import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.gui.MainFrame;

public class Criteria {

	private String name;
	private Grades grade;

	private JButton[] gradeBtns = new JButton[] { new JButton("F"), new JButton("E"), new JButton("C"),
			new JButton("A") };

	private JLabel lblCriteria;
	private JLabel lblGrade;
	
	private JPanel pButtons = new JPanel();
	private JPanel pCriteria = new JPanel();
	
	private FlowLayout pButtonsLayout = new FlowLayout(FlowLayout.LEFT);
	
	private GridLayout pCriteriaLayout = new GridLayout(2, 2);
	
	//Försök att hitta en ny lösning senare
	MainFrame mf;	

	public Criteria() {}

	public Criteria(String name, MainFrame mf) throws IllegalNameException {
		try {
			this.setName(name);
		} catch (IllegalNameException e) {
			throw new IllegalNameException(e.getMessage());
		}
				
		this.mf = mf;
		
		//Ville inte vara i setproperties av någon anledning.
		lblCriteria = new JLabel(name);
		this.setProperties(name);
		this.addActionListeneners();
		this.updateGUI(grade);
	}

	private void setProperties(String name) {
		this.setGrade(Grades.F);

		lblGrade = new JLabel();
		
		gradeBtns[0].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[1].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[2].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[3].setFont(new Font("Dialog", Font.PLAIN, 18));
		
		lblGrade.setFont(new Font("Dialog", Font.BOLD, 18));
		lblCriteria.setFont(new Font("Dialog", Font.BOLD, 18));
		
		//Buttons
		pButtons.setLayout(pButtonsLayout);
		
		for (int i = 0; i < gradeBtns.length; i++) {
			pButtons.add(gradeBtns[i]);
		}
		
		//The rest
		pCriteria.setLayout(pCriteriaLayout);
		
		pCriteria.add(lblCriteria);
		pCriteria.add(lblGrade);
		pCriteria.add(pButtons);
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
		var gp = mf.getGradePanel();
		this.updateGUI(g);
		gp.updateGUI(gp.getState());
	}

	public void updateGUI(Grades grade) {
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
		if (name.length() >= 3)
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

	public JPanel getPanelCriteria() {
		return pCriteria;
	}

	/**
	 * Compares two grades
	 * 
	 * @param c the other criteria
	 * @return <b>true</b> -> if this criteria is higher <br><b>false</b> -> if this criteria is lower.
	 */
	public boolean compare(Criteria c) {
		if (grade.ordinal() > c.grade.ordinal())
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if two criteria are the same.
	 * 
	 * @param c the other criteria
	 * @return <b>true</b> if the same name, else <b>false</b>
	 */
	@Override
	public boolean equals(Object obj) {
		Criteria c = (Criteria) obj;
		return (this.name.equals(c.name));
	}

}
