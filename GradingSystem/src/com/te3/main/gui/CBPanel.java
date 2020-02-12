package com.te3.main.gui;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class CBPanel extends JPanel {
	
	
	BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
	FlowLayout cbLayout = new FlowLayout();
	JComboBox<String> cbGroup = new JComboBox<String>();
	
	String[] boxGroups = {"Grupp", "Kurs", "Elev", "Uppgift"};
	
	public CBPanel() {
		initComponents();
	}
	
	private void initComponents() {
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		
		cbLayout = new FlowLayout();
		
		cbGroup = new JComboBox<String>();
	}
}
