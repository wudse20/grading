package com.te3.main.gui;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The panel used for getting a name input <br>
 * for the editing and adding frames.
 */
public class NamePanel extends JPanel implements DocumentListener {

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

		//Adds listener to the txfName
		this.txfName.getDocument().addDocumentListener(this);

		// Adds components
		this.add(lblName);
		this.add(txfName);
	}

	/**
	 * Sets a color to the TextField.
	 *
	 * @param c the new colour of the TextField
	 */
	public void setTextFieldColour(Color c) {
		this.txfName.setBackground(c);
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

	@Override
	public void insertUpdate(DocumentEvent e) {
		txfName.setBackground(Color.white);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		txfName.setBackground(Color.white);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}
}
