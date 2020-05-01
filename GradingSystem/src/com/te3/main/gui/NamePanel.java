package com.te3.main.gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The panel used for getting a name input <br>
 * for the editing and adding frames.
 */
public class NamePanel extends JPanel {

	/** Default */
	private static final long serialVersionUID = 1L;

	/** The last input in the text field */
	private String lastInput;

	// Layout
	FlowLayout layout = new FlowLayout(FlowLayout.LEFT);

	// Label
	JLabel lblName = new JLabel("Namn:");

	// TextField
	JTextField txfName = new JTextField(12);

	/**
	 * Sets everything up
	 */
	public NamePanel() {
		// Sets layout
		this.setLayout(layout);

		// Adds components
		this.add(lblName);
		this.add(txfName);
	}

	/**
	 * Gets the last input
	 */
	public String getLastInput() {
		// Stores the text in the TextField
		this.lastInput = txfName.getText();

		// Returns the last input
		return lastInput;
	}

	/**
	 * Sets the last input
	 */
	public void setLastInput(String lastInput) {
		// Stores the last input
		this.lastInput = lastInput;

		// Sets the TextField's text to the parameter.
		txfName.setText(this.lastInput);
	}
}
