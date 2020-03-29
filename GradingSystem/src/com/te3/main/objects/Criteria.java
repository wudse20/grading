package com.te3.main.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.Transient;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.te3.main.enums.Grade;
import com.te3.main.exceptions.IllegalNameException;

/**
 * A criteria
 * */
public class Criteria {

	private String name;
	private Grade grade;

	private JButton[] gradeBtns = new JButton[] { new JButton("F"), new JButton("E"), new JButton("C"),
			new JButton("A") };

	private JLabel lblCriteria;
	private JLabel lblGrade;

	private JPanel pButtons = new JPanel();
	private JPanel pCriteria = new JPanel();

	private FlowLayout pButtonsLayout = new FlowLayout(FlowLayout.LEFT);

	private GridLayout pCriteriaLayout = new GridLayout(2, 2);

	public Criteria() {
		lblCriteria = new JLabel();
		this.setProperties();
	}

	public Criteria(String name) throws IllegalNameException {
		try {
			this.setName(name);
		} catch (IllegalNameException e) {
			throw new IllegalNameException(e.getMessage());
		}

		// Ville inte vara i setproperties av någon anledning.
		lblCriteria = new JLabel(name);
		this.setProperties();
		this.updateGUI(grade);
	}

	private void setProperties() {
		this.setGrade(Grade.F);

		lblGrade = new JLabel();

		gradeBtns[0].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[1].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[2].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[3].setFont(new Font("Dialog", Font.PLAIN, 18));

		lblGrade.setFont(new Font("Dialog", Font.BOLD, 18));
		lblCriteria.setFont(new Font("Dialog", Font.BOLD, 18));

		// Buttons
		pButtons.setLayout(pButtonsLayout);

		for (int i = 0; i < gradeBtns.length; i++) {
			pButtons.add(gradeBtns[i]);
		}

		// The rest
		pCriteria.setLayout(pCriteriaLayout);
		pButtons.setOpaque(false);
		pCriteria.setOpaque(false);
		pCriteria.setMaximumSize(new Dimension(640, 78));

		pCriteria.add(lblCriteria);
		pCriteria.add(lblGrade);
		pCriteria.add(pButtons);
	}

	private void updateGUI(Grade grade) {
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

		lblCriteria.setText(name);
		lblGrade.setText(grade.toString());
	}

	public void updateGUI() {
		this.updateGUI(grade);
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

	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@Transient
	public void setPanelCriteria(JPanel panelCriteria) {
		this.pCriteria = panelCriteria;
	}

	@Transient
	public JPanel getPanelCriteria() {
		return pCriteria;
	}

	@Transient
	public void setGradeBtns(JButton[] gradeBtns) {
		this.gradeBtns = gradeBtns;
	}

	@Transient
	public JButton[] getGradeBtns() {
		return this.gradeBtns;
	}

	/**
	 * Compares two grades
	 * 
	 * @param c the other criteria
	 * @return <b>true</b> -> if this criteria is higher <br>
	 *         <b>false</b> -> if this criteria is lower.
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

	@Override
	public String toString() {
		return this.name;
	}
}
