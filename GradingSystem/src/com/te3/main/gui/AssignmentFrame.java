package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AssignmentFrame extends JFrame {

	/** Generated */
	private static final long serialVersionUID = 1797348437376328717L;

	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");
	
	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();
	
	JPanel panel = new JPanel();
	
	NamePanel np = new NamePanel();
	MainAssignmentPanel map;
	AddControllPanel acp = new AddControllPanel();
	EditControllPanel ecp = new EditControllPanel();
	
	MainFrame mf;
	
	/**
	 * For adding
	 * 
	 * @param mf the instance of the MainFrae
	 */
	public AssignmentFrame(MainFrame mf) {
		super("Lägg till en uppgift");
		this.mf = mf;
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));
		
		map = new MainAssignmentPanel(mf);
		
		panel.setLayout(pLayout);
		
		panel.add(np, BorderLayout.PAGE_START);
		panel.add(map, BorderLayout.CENTER);
		panel.add(acp, BorderLayout.PAGE_END);
		
		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}
}
