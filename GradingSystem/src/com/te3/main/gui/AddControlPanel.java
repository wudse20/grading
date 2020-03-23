package com.te3.main.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A class used for the buttons in the <br>
 * different GUI:s for adding new <br>
 * Classes, Courses, Tasks
 *
 * @Author Anton Skorup
 * */
public class AddControlPanel extends JPanel {

	/** Generated */
	private static final long serialVersionUID = -7606289199305316004L;

	FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);

	JButton btnHelp = new JButton("?");
	JButton btnCancel = new JButton("Avbryt");
	JButton btnAdd = new JButton("Lägg till");

	/**
	 * Constructor adds the components <br>
	 * and sets the properties.
	 * */
	public AddControlPanel() {
		this.setLayout(layout);
		this.add(btnHelp);
		this.add(btnCancel);
		this.add(btnAdd);
	}

	/**
	 * A getter for the add button
	 * */
	public JButton getBtnAdd() {
		return this.btnAdd;
	}

	/**
	 * A getter for the cancel button
	 * */
	public JButton getBtnCancel() {
		return this.btnCancel;
	}

	/**
	 * A getter for the help button
	 * */
	public JButton getBtnHelp() {
		return this.btnHelp;
	}
}
