package com.te3.main.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The button panel att the bottom <br>
 * of the editing GUI:s.
 * */
public class EditControlPanel extends JPanel {

	/** Generated */
	private static final long serialVersionUID = -7606289199305316004L;

	//Layout
	FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);

	//Buttons
	JButton btnHelp = new JButton("?");
	JButton btnCancel = new JButton("Avbryt");
	JButton btnUpdate = new JButton("Uppdatera");
	JButton btnDelete = new JButton("Tabort");

	/**
	 * Sets everything up
	 * */
	public EditControlPanel() {
		this.setLayout(layout);
		this.add(btnHelp);
		this.add(btnDelete);
		this.add(btnCancel);
		this.add(btnUpdate);
	}

	/**
	 * The getter for the update button
	 *
	 * @return the update button
	 * */
	public JButton getBtnUpdate() {
		return this.btnUpdate;
	}

	/**
	 * The getter for the cancel button
	 *
	 * @return the cancel button
	 * */
	public JButton getBtnCancel() {
		return this.btnCancel;
	}

	/**
	 * The getter for the help button
	 *
	 * @return the help button
	 * */
	public JButton getBtnHelp() {
		return this.btnHelp;
	}


	/**
	 * The getter for the delete button
	 *
	 * @return the delete button
	 */
	public JButton getBtnDelete() {
		return this.btnDelete;
	}
}
