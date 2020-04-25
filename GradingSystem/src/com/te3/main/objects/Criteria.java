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

	/** The name of the criteria */
	private String name;

	/** The current grade */
	private Grade grade;

	/** The buttons used to choose the different grades */
	private JButton[] gradeBtns = new JButton[] { new JButton("F"), new JButton("E"), new JButton("C"),
			new JButton("A") };

	//Labels
	private JLabel lblCriteria;
	private JLabel lblGrade;

	//Panels
	private JPanel pButtons = new JPanel();
	private JPanel pCriteria = new JPanel();

	//Layouts
	private FlowLayout pButtonsLayout = new FlowLayout(FlowLayout.LEFT);

	private GridLayout pCriteriaLayout = new GridLayout(2, 2);


	/**
	 * Used for the saving of the criteria
	 */
	public Criteria() {
		//Initializes the lblCriteria
		lblCriteria = new JLabel();

		//Sets the properties
		this.setProperties();
	}

	/**
	 * Sets up a new criteria
	 *
	 * @param name the name of the criteria
	 * @throws IllegalNameException if the name isn't accepted
	 */
	public Criteria(String name) throws IllegalNameException {
		try {
			//Sets the name
			this.setName(name);
		} catch (IllegalNameException e) {
			//Throws the exception
			throw new IllegalNameException(e.getMessage());
		}

		//Initializes the lblCriteria
		lblCriteria = new JLabel(name);

		//Sets the properties
		this.setProperties();

		//Updates the gui
		this.updateGUI(grade);
	}

	/**
	 * Sets properties
	 */
	private void setProperties() {
		//Sets the default grade
		this.setGrade(Grade.F);

		//Initializes the lblGrade
		lblGrade = new JLabel();

		//Sets the font of the buttons
		gradeBtns[0].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[1].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[2].setFont(new Font("Dialog", Font.PLAIN, 18));
		gradeBtns[3].setFont(new Font("Dialog", Font.PLAIN, 18));

		//Sets the font of the labels
		lblGrade.setFont(new Font("Dialog", Font.BOLD, 18));
		lblCriteria.setFont(new Font("Dialog", Font.BOLD, 18));

		//Sets the layout
		pButtons.setLayout(pButtonsLayout);

		//Adds the buttons
		for (int i = 0; i < gradeBtns.length; i++) {
			pButtons.add(gradeBtns[i]);
		}

		//Sets the layout
		pCriteria.setLayout(pCriteriaLayout);

		//Sets the buttons to be transparent
		pButtons.setOpaque(false);
		pCriteria.setOpaque(false);

		//Sets max size
		pCriteria.setMaximumSize(new Dimension(640, 78));

		//Adds components
		pCriteria.add(lblCriteria);
		pCriteria.add(lblGrade);
		pCriteria.add(pButtons);
	}

	/**
	 * Updates the GUI part with the new grade
	 *
	 * @param grade The current grade
	 */
	private void updateGUI(Grade grade) {
		//Sets the color of the button based on the state the the correct colour.
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

		//Sets the text of the lables
		lblCriteria.setText(name);
		lblGrade.setText(grade.toString());
	}

	/**
	 * Updates the GUI
	 */
	public void updateGUI() {
		this.updateGUI(grade);
	}


	/**
	 * A getter for the name
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * A setter for the name
	 *
	 * @param name The new name of the criteria
	 * @throws IllegalNameException if the name is shorter than 3 chars (trimmed)
	 */
	public void setName(String name) throws IllegalNameException {
		/*
		 * Checks the trimmed length
		 *
		 * if accepted sets the name
		 * else throws an exception with a message.
		 * */
		if (name.trim().length() >= 3)
			this.name = name;
		else
			throw new IllegalNameException("För kort meddelande");
	}

	/**
	 * A getter for the grade
	 *
	 * @return The grade
	 */
	public Grade getGrade() {
		return this.grade;
	}

	/**
	 * A setter for the grade
	 *
	 * @param grade the new grade
	 */
	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	/**
	 * A setter for the panelCriteria. <br>
	 * Transient annotation so it won't saved.
	 *
	 * @param panelCriteria the new panel
	 */
	@Transient
	public void setPanelCriteria(JPanel panelCriteria) {
		this.pCriteria = panelCriteria;
	}


	/**
	 * A getter for the panel Criteria. <br>
	 * Transient annotation so it won't be saved.
	 *
	 * @return the panel that displays the criteria
	 */
	@Transient
	public JPanel getPanelCriteria() {
		return pCriteria;
	}

	/**
	 * A setter for the grade buttons.<br>
	 * Transient annotation so it won't be saved.
	 *
	 * @param gradeBtns the grade buttons
	 */
	@Transient
	public void setGradeBtns(JButton[] gradeBtns) {
		this.gradeBtns = gradeBtns;
	}

	/**
	 * A getter for the grade buttons
	 * Transient annotation so it won't be saved.
	 *
	 * @return the grade buttons
	 */
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
		/*
		 * Using ordinal since the enum is set up
		 * so the hedger grade the higher ordinal
		 * and that makes for easy comparisons.
		 * */
		if (grade.ordinal() > c.grade.ordinal())
			return true;
		else
			return false;
	}

	/**
	 * Checks if two criteria are the same.
	 * 
	 * @param obj the other criteria
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
