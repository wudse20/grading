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
	
	private boolean checkInitTime(long compareTime) {
		//System.out.println(compareTime - (initTime + 3000));
		if (compareTime > (initTime + 3000)) {
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
			if (checkInitTime(e.getWhen()) || !e.getActionCommand().equals("comboBoxChanged")) return;
			
			int i = cbClass.getSelectedIndex();
			int itmCount = cbClass.getItemCount();
			
			System.out.println(i);
			System.out.println(itmCount);
			
			if (i == 0) {
				mf.updateGradePanel(State.NOTHING_SELECTED);
			} else if (i != -1 && i < itmCount - 2) {
				mf.updateGradePanel(State.CLASS);
				mf.setCurrentlySelectedClassIndex(i-1);
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(SchoolClass.class, false);
				
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(SchoolClass.class, true);
			}
		});
		
		//course combobox
		cbCourse.addActionListener((e) -> {
			System.out.println("Course ActionCommand: " + e.getActionCommand());
			if (checkInitTime(e.getWhen()) || !e.getActionCommand().equals("comboBoxChanged")) return;
			
			int i = cbCourse.getSelectedIndex();
			int itmCount = cbCourse.getItemCount();
			
			if (i == 0) { 
				mf.updateGradePanel(State.NOTHING_SELECTED);
			} else if (i != -1 && i < itmCount - 2) {
				mf.setCurrentlySelectedCourseIndex(i-1);
				mf.updateGradePanel(State.CLASS_COURSE);
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(Course.class, false);
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(Course.class, true);
			}
		});
		
		//student combobox
		cbStudent.addActionListener((e) -> {
			System.out.println("Student ActionCommand: " + e.getActionCommand());
			if (checkInitTime(e.getWhen()) || !e.getActionCommand().equals("comboBoxChanged")) return;
			
			mf.setCurrentlySelectedStudentIndex(cbStudent.getSelectedIndex());
		});
		
		//task combobox
		cbTask.addActionListener((e) -> {
			System.out.println("Task ActionCommand: " + e.getActionCommand());
			if (checkInitTime(e.getWhen()) || !e.getActionCommand().equals("comboBoxChanged")) return;
			
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
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(Task.class, true);
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
	 * Completely updates the entire combobox panel with new information.
	 * @param newData the new information to be parsed and updated with.
	 */
	public void refreshData(Data newData) {
		initTime = System.currentTimeMillis();
		
		System.out.println("Refreshing Data combobox data");
		cbClass.removeAllItems();
		cbCourse.removeAllItems();
		cbStudent.removeAllItems();
		cbTask.removeAllItems();
		
		Data localData = newData;
		
		ArrayList<SchoolClass> dataClasses = localData.getClasses();
		ArrayList<Student> dataStudents = dataClasses.get(0).getStudents();
		ArrayList<Course> dataCourses = dataStudents.get(0).getCourses();
		ArrayList<Task> dataTasks = dataCourses.get(0).getCourseTasks();
		
		System.out.println("Adding default combobox items...");
		
		cbClass.addItem("Klass");
		cbCourse.addItem("Kurs");
		
		cbTask.addItem("Samlad vy");
		
		dataClasses.forEach((n) -> cbClass.addItem(n.getName()));
		dataCourses.forEach((n) -> cbCourse.addItem(n.getName()));
		dataStudents.forEach((n) -> cbStudent.addItem(n.getName()));
		dataTasks.forEach((n) -> cbTask.addItem(n.getName()));
		
		System.out.println("Adding change items...");
		
		cbClass.addItem("Ny");
		cbClass.addItem("Ändra");
		cbCourse.addItem("Ny");
		cbCourse.addItem("Ändra");
		cbTask.addItem("Ny");
		cbTask.addItem("Ändra");
	}
}
