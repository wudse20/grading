package com.te3.main.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The button panel att the bottom <br>
 * of the editing GUI:s.
 *
 * @Author Anton Skorup
 * */
public class EditControlPanel extends JPanel {

	/** Generated */
	private static final long serialVersionUID = -7606289199305316004L;

	FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);

	JButton btnHelp = new JButton("?");
	JButton btnCancel = new JButton("Avbryt");
	JButton btnUpdate = new JButton("Uppdatera");

	/**
	 * Sets everything up
	 * */
	public EditControlPanel() {
		this.setLayout(layout);
		this.add(btnHelp);
		this.add(btnCancel);
		this.add(btnUpdate);
	}

	/**
	 * The getter for the update button
	 * */
	public JButton getBtnUpdate() {
		return this.btnUpdate;
	}

	/**
	 * The getter for the cancel button
	 * */
	public JButton getBtnCancel() {
		return this.btnCancel;
	}

	/**
	 * The getter for the help button
	 * */
	public JButton getBtnHelp() {
		return this.btnHelp;
	}
}