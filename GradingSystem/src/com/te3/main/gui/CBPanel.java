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
	
	boolean isRefreshing = false;
	
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
			if (/*!cbClass.isFocusOwner() || */isRefreshing) return;
			
			System.out.println("Class combobox updating");
			
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
			if (!cbCourse.isFocusOwner() || isRefreshing) return;
			
			System.out.println("Course combobox updating");
			
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
			if (!cbStudent.isFocusOwner() || isRefreshing) return;

			System.out.println("Student combobox updating");
			
			mf.setCurrentlySelectedStudentIndex(cbStudent.getSelectedIndex());
			
			mf.updateGradePanel(State.CLASS_COURSE_STUDENT);
			
			cbTask.grabFocus();
			cbTask.setSelectedIndex(0);
		});
		
		//task combobox
		cbTask.addActionListener((e) -> {
			if (!cbTask.isFocusOwner() || isRefreshing) return;
			
			System.out.println("Task combobox updating");
			
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
	 * Completely updates the entire combobox panel with new information.
	 * @param newData the new information to be parsed and updated with.
	 */
	public void refreshData(Data newData) {
		isRefreshing = true;
		
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
				
				ArrayList<Course> dataCourses = null;
				
				try {
					dataCourses = dataStudents.get(mf.getCurrentlySelectedClassIndex()).getCourses();
				} catch (java.lang.IndexOutOfBoundsException e) {
					System.out.println("Selected class index is out of bounds. Reverting to default index 0.");
					
					if (dataStudents.size() != 0) {
						mf.setCurrentlySelectedClassIndex(0);
						dataCourses = dataStudents.get(0).getCourses();
					}
				}
				
				if (dataCourses.size() != 0) {
					dataCourses.forEach((n) -> cbCourse.addItem(n.getName()));
				}
				
				ArrayList<Task> dataTasks = null;
				
				try {
					dataTasks = dataCourses.get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks();
				} catch (java.lang.IndexOutOfBoundsException e) {
					System.out.println("Selected task is out of bounds. Reverting to default index 0.");
					
					if (dataCourses.size() != 0) {
						mf.setCurrentlySelectedCourseIndex(0);
						dataTasks = dataCourses.get(0).getCourseTasks();
					}
				}
				
				if (dataTasks != null && dataTasks.size() != 0) {
					dataTasks.forEach((n) -> cbTask.addItem(n.getName()));
				}
			}
			isRefreshing = false;
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
