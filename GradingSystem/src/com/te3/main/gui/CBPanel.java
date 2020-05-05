package com.te3.main.gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.te3.main.enums.State;
import com.te3.main.objects.*;

/**
 *	Du behöver fixa en metod i MainFrame för att uppdatera din panel.
 *	Du behöver även uppdatera GUI:t och sätta vilket stadie panelen är i
 *	m.h.a. mf.updateGradePanel(State), med rätt state. Sedan mf.updateGradePanel()
 */
public class CBPanel extends JPanel {
	/** Default */
	private static final long serialVersionUID = 1L;
	
	long initTime = System.currentTimeMillis();
	
	//Keeps track of what "level" you have selected in the comboboxes so it can properly update the visibility of them
	int selectionLevel = 0;

	GridLayout mainLayout;
	
	JLabel lblClass;
	JLabel lblCourse;
	JLabel lblStudent;
	JLabel lblTask;
	
	JComboBox<String> cbClass;
	JComboBox<String> cbCourse;
	JComboBox<String> cbStudent;
	JComboBox<String> cbTask;
	
	MainFrame mf;
	
	public CBPanel(Data importedData, MainFrame mf) {
		this.mf = mf;
		
		initComponents();
		refreshData(importedData);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(super.getMaximumSize().width, super.getPreferredSize().height);
	}
	
	/**
	 * Deprecated method of checking wether or not comboboxes were changed by the program
	 * @return
	 */
	private boolean checkInitTime() {
		int deltaTime = 2000;
		long compareTime = System.currentTimeMillis();
		
		System.out.println(compareTime - (initTime + deltaTime));
		if (compareTime > (initTime + deltaTime)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Method to override the init time when we set the index using a function, as to not open a bunch of unnecessary windows
	 * @param overrideCombobox The combobox which we set the index of
	 * @param theIndex The index to set the combobox to
	 */
	private void overrideInitTime(JComboBox<String> overrideCombobox, int theIndex) {
		initTime = System.currentTimeMillis();
		overrideCombobox.setSelectedIndex(theIndex);
	}
	
	private void initComponents() {
		mainLayout 	= new GridLayout(2, 4);
		this.setLayout(mainLayout);
		
		lblClass 	= new JLabel("Klass");
		lblCourse 	= new JLabel("Kurs");
		lblStudent 	= new JLabel("Elev");
		lblTask 	= new JLabel("Uppgift");
		
		this.add(lblClass);
		this.add(lblCourse);
		this.add(lblStudent);
		this.add(lblTask);
		
		cbClass 	= new JComboBox<String>();
		cbCourse 	= new JComboBox<String>();
		cbStudent 	= new JComboBox<String>();
		cbTask 		= new JComboBox<String>();

		yoda(mf.shouldShowBabyYoda());
		
		//class combobox
		cbClass.addActionListener((e) -> {
			System.out.println("Class ActionCommand: " + e.getActionCommand());
			if (!cbClass.isFocusOwner()) return;
			
			int i = cbClass.getSelectedIndex();
			int itmCount = cbClass.getItemCount();
			
			System.out.println(i);
			System.out.println(itmCount);
			
			if (i == 0) {
				mf.updateGradePanel(State.NOTHING_SELECTED);
			} else if (i != -1 && i < itmCount - 2) {
				mf.updateGradePanel(State.CLASS);
				mf.setCurrentlySelectedClassIndex(i-1);
				
				//update student combobox if the class changed
				cbStudent.removeAllItems();
				ArrayList<SchoolClass> dataClasses = mf.getMainData().getClasses();
				ArrayList<Student> dataStudents = dataClasses.get(i-1).getStudents();
				dataStudents.forEach((n) -> cbStudent.addItem(n.getName()));
				
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(SchoolClass.class, false);
				mf.setCurrentlySelectedClassIndex(itmCount-3);
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(SchoolClass.class, true);
				mf.setCurrentlySelectedClassIndex(itmCount-3);
			}
			
			//handleNewState();
		});
		
		//course combobox
		cbCourse.addActionListener((e) -> {
			System.out.println("Course ActionCommand: " + e.getActionCommand());
			if (!cbCourse.isFocusOwner()) return;
			
			int i = cbCourse.getSelectedIndex();
			int itmCount = cbCourse.getItemCount();
			
			if (i == 0) { 
				mf.updateGradePanel(State.NOTHING_SELECTED);
			} else if (i != -1 && i < itmCount - 2) {
				mf.setCurrentlySelectedCourseIndex(i-1);
				mf.updateGradePanel(State.CLASS_COURSE);
				
				cbStudent.grabFocus();
				cbStudent.setSelectedIndex(0);
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(Course.class, false);
				mf.setCurrentlySelectedCourseIndex(itmCount-3);
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(Course.class, true);
				mf.setCurrentlySelectedCourseIndex(itmCount-3);
			}
		});
		
		//student combobox
		cbStudent.addActionListener((e) -> {
			System.out.println("Student ActionCommand: " + e.getActionCommand());
			if (!cbStudent.isFocusOwner()) return;
			
			mf.setCurrentlySelectedStudentIndex(cbStudent.getSelectedIndex());
			
			mf.updateGradePanel(State.CLASS_COURSE_STUDENT);
			
			cbTask.grabFocus();
			cbTask.setSelectedIndex(0);
		});
		
		//task combobox
		cbTask.addActionListener((e) -> {
			System.out.println("Task ActionCommand: " + e.getActionCommand());
			if (!cbTask.isFocusOwner()) return;
			
			int i = cbTask.getSelectedIndex();
			int itmCount = cbTask.getItemCount();
			
			if (i == 0) {
				mf.setCurrentlySelectedAssignmentIndex(i);
				mf.updateGradePanel(State.CLASS_COURSE_STUDENT);
			} else if (i != -1 && i < itmCount - 2) {
				mf.setCurrentlySelectedAssignmentIndex(i-1);
				mf.updateGradePanel(State.CLASS_COURSE_STUDENT_TASK);
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(Task.class, false);
				mf.setCurrentlySelectedAssignmentIndex(itmCount-3);
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(Task.class, true);
				mf.setCurrentlySelectedAssignmentIndex(itmCount-3);
			}
		});
		
		this.add(cbClass);
		this.add(cbCourse);
		this.add(cbStudent);
		this.add(cbTask);
	}


	/**
	 * @param shouldShowBabyYoda if {@code true} -> set up for baby yoda, else setup for default.
	 */
	public void yoda(boolean shouldShowBabyYoda) {
		if (shouldShowBabyYoda) {
			lblClass.setForeground(Color.white);
			lblCourse.setForeground(Color.white);
			lblStudent.setForeground(Color.white);
			lblTask.setForeground(Color.white);
		} else {
			lblClass.setForeground(Color.black);
			lblCourse.setForeground(Color.black);
			lblStudent.setForeground(Color.black);
			lblTask.setForeground(Color.black);
		}
	}

	/**
	 * Method to handle the comboboxes disabling when items are not selected
	 */
	public void handleNewState() {
		State curState = mf.getGradeState();
		System.out.println(curState.toString());
		
		if (curState == State.NOTHING_SELECTED) {
			cbCourse.setEnabled(false);
			cbStudent.setEnabled(false);
			cbTask.setEnabled(false);
		} else if (curState == State.CLASS) {
			cbCourse.setEnabled(true);
			cbStudent.setEnabled(false);
			cbTask.setEnabled(false);
		} else {
			cbCourse.setEnabled(true);
			cbStudent.setEnabled(true);
			cbTask.setEnabled(true);
		}
	}
	
	/**
	 * OBS DU MÅSTE FIXA SÅ ATT DEN TÅLER ATT DET INTE FINNS NÅGOT I LISTAN.
	 *
	 * Completely updates the entire combobox panel with new information.
	 * @param newData the new information to be parsed and updated with.
	 */
	public void refreshData(Data newData) {
		initTime = System.currentTimeMillis();
		
		System.out.println("Refreshing Data combobox data");
		System.out.println("Deleting old items...");
		cbClass.removeAllItems();
		cbCourse.removeAllItems();
		cbStudent.removeAllItems();
		cbTask.removeAllItems();
		
		Data localData = newData;

		System.out.println("Adding default combobox items...");
		cbClass.addItem("Klass");
		cbCourse.addItem("Kurs");
		cbStudent.addItem("-");
		cbTask.addItem("Samlad vy");
		
		System.out.println("Getting new data...");
		ArrayList<SchoolClass> dataClasses = localData.getClasses();
		if (dataClasses.size() != 0) {
			
			dataClasses.forEach((n) -> cbClass.addItem(n.getName()));
			
			ArrayList<Student> dataStudents = dataClasses.get(mf.getCurrentlySelectedClassIndex()).getStudents();
			
			if (dataStudents.size() != 0) {
				
				dataStudents.forEach((n) -> cbStudent.addItem(n.getName()));
				
				ArrayList<Course> dataCourses = dataStudents.get(mf.getCurrentlySelectedClassIndex()).getCourses();
				
				dataCourses.forEach((n) -> cbCourse.addItem(n.getName()));
				
				ArrayList<Task> dataTasks = dataCourses.get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks();
				
				dataTasks.forEach((n) -> cbTask.addItem(n.getName()));
			}
		}
		
		System.out.println("Adding change items...");
		
		cbClass.addItem("Ny");
		cbClass.addItem("Ändra");
		cbCourse.addItem("Ny");
		cbCourse.addItem("Ändra");
		cbTask.addItem("Ny");
		cbTask.addItem("Ändra");
	}
}
