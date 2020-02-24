package com.te3.main.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EditControllPanel extends JPanel {

	/** Generated */
	private static final long serialVersionUID = -7606289199305316004L;

	FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);

	JButton btnHelp = new JButton("?");
	JButton btnCancel = new JButton("Avbryt");
	JButton btnUpdate = new JButton("Uppdatera");

	public EditControllPanel() {
		this.setLayout(layout);
		this.add(btnHelp);
		this.add(btnCancel);
		this.add(btnUpdate);
	}

	public JButton getBtnUpdate() {
		return this.btnUpdate;
	}

	public JButton getBtnCancel() {
		return this.btnCancel;
	}

	public JButton getBtnHelp() {
		return this.btnHelp;
	}
}
