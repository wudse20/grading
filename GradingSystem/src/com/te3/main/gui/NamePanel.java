package com.te3.main.gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NamePanel extends JPanel {
	
	/** Default */
	private static final long serialVersionUID = 1L;

	private String lastInput;
	
	FlowLayout layout = new FlowLayout(FlowLayout.LEFT);

	JLabel lblName = new JLabel("Namn:");
	
	JTextField txfName = new JTextField(12);

	public NamePanel() {
		this.setLayout(layout);
		this.add(lblName);
		this.add(txfName);
	}
	
	public String getLastInput() {
		this.lastInput = txfName.getText();
		return lastInput;
	}

	public void setLastInput(String lastInput) {
		this.lastInput = lastInput;
		txfName.setName(this.lastInput);
	}
}
