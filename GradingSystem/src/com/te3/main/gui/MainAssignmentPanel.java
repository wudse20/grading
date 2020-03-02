package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.te3.main.objects.Criteria;

public class MainAssignmentPanel extends JPanel {

	/**
	 * Generated
	 */
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
	 *
	 * @param mf the instance of the MainFrame
	 */
	public MainAssignmentPanel(MainFrame mf) {
		this.mf = mf;
		this.setLayout(layout);
		
		this.courseCriteria = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getCourses()
				.get(mf.getCurrentlySelectedCourseIndex()).getCourseCriteria();
		this.addedCriteria = new ArrayList<Criteria>();
		
		this.refreshLists();
		this.setProperties();
		
		this.add(lblCriteria, BorderLayout.PAGE_START);
		this.add(scrCourseCriteria, BorderLayout.LINE_START);
		this.add(scrAddedCriteria, BorderLayout.LINE_END);
		this.add(lblSpacer, BorderLayout.PAGE_END);
	}

	private void setProperties() {
		listAddedCriteria.setPreferredSize(new Dimension(250, 100));
		listCourseCriteria.setPreferredSize(new Dimension(250, 100));
		
		listAddedCriteria.getSelectionModel().addListSelectionListener((e) -> {
			
		});
		
		listCourseCriteria.getSelectionModel().addListSelectionListener((e) -> {
			
		});
	}

	private void refreshLists() {
		Criteria[] addedCriteria = new Criteria[this.addedCriteria.size()];
		Criteria[] courseCriteria = new Criteria[this.courseCriteria.size()];

		listAddedCriteria.setListData(this.addedCriteria.toArray(addedCriteria));
		listCourseCriteria.setListData(this.courseCriteria.toArray(courseCriteria));
	}
}
