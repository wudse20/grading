package com.te3.main.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.te3.main.objects.Criteria;

public class MainAssignmentPanel extends JPanel {

	/** Generated */
	private static final long serialVersionUID = 3651008258198927088L;
	
	ArrayList<Criteria> courseCriteria;
	ArrayList<Criteria> addedCriteria;
	
	BorderLayout layout = new BorderLayout();
	
	MainFrame mf;
	
	JLabel lblCriteria = new JLabel("Välj Kunskapskrav: ");
	JLabel lblSpacer = new JLabel(" ");
	
	JList<Criteria> listCourseCriteria = new JList<Criteria>();
	JList<Criteria> listAddedCriteria = new JList<Criteria>();
	
	JScrollPane scrCourseCriteria = new JScrollPane(listCourseCriteria);
	JScrollPane scrAddedCriteria = new JScrollPane(listAddedCriteria);
	
	/**
	 * For adding
	 * @param mf the instance of the MainFrame
	 */
	public MainAssignmentPanel(MainFrame mf) {
		this.mf = mf;
		this.setLayout(layout);
		
		this.add(lblCriteria, BorderLayout.PAGE_START);
		this.add(scrCourseCriteria, BorderLayout.LINE_START);
		this.add(scrAddedCriteria, BorderLayout.LINE_END);
		this.add(lblSpacer, BorderLayout.PAGE_END);
	}

}
