package com.te3.main.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	
	/** Default */
	private static final long serialVersionUID = 1L;
	
	private String helpTitle = "Huvudvy!";
	private String helpInfo = "<html>Detta är en mening. <br> Detta är en till mening. <br> Detta är en placeholder!</html>";

	MainFrame mf;
	
	FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);
	
	JButton btnSave = new JButton("Spara");
	JButton btnPrint = new JButton("Skriv ut");
	JButton btnHelp = new JButton("?");
	
	JLabel lblSpacer1 = new JLabel("  ");
	
	public ButtonPanel(MainFrame mf) {
		this.mf = mf;
		this.setProperites();
		this.addComponents();
		
		btnHelp.addActionListener((e) -> {
			new HelpFrame(helpTitle, helpInfo, 500).setVisible(true);;
		});
		
		btnSave.addActionListener((e) -> {
			mf.save(mf.getSaveFilePath());
			JOptionPane.showMessageDialog(mf, "Du har sparat!", "Sprat", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	private void addComponents() {
		this.add(lblSpacer1);
		this.add(btnHelp);
		this.add(btnPrint);
		this.add(btnSave);
	}

	private void setProperites() {
		this.setLayout(layout);
	}
}
