package com.te3.main.gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.te3.main.enums.State;
import com.te3.main.objects.*;

public class CBPanel extends JPanel {
	/** Default */
	private static final long serialVersionUID = 1L;
	
	boolean isRefreshing = false;
	
	boolean hasInitialized = false;

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
		
		hasInitialized = true;
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
		
		//Add the ActionListener to the Class combobox.
		cbClass.addActionListener((e) -> {
			//Check if the combobox !!!DOES NOT!!! have focus or if the data is being refreshed.
			//If we don't have focus or the data is being refreshed, then return.
			if (!cbClass.isFocusOwner() || isRefreshing) return;
			
			System.out.println("Class combobox updating");
			
			//Get the currently selected index of the combobox
			int i = cbClass.getSelectedIndex();
			//Get the total amount of objects in the combobox
			int itmCount = cbClass.getItemCount();
			
			//The user has selected the default entry
			if (i == 0) {
				//Display nothing in the gradepanel
				mf.updateGradePanel(State.NOTHING_SELECTED);
			//The user has selected a class
			} else if (i != -1 && i < itmCount - 2) {
				//Update the gradepanel state, to have it display nothing (but the other comboboxes become available for selection)
				mf.updateGradePanel(State.CLASS);
				//Set the selected class index to the currently selected index in the combobox
				mf.setCurrentlySelectedClassIndex(i-1);
				//Reset the selected course, student and assignment index to 0, to completely reset the state of the display.
				mf.setCurrentlySelectedCourseIndex(0);
				mf.setCurrentlySelectedStudentIndex(0);
				mf.setCurrentlySelectedAssignmentIndex(0);
				
				//Update the Student combobox with eventual new items from the selected class
				cbStudent.removeAllItems();
				ArrayList<SchoolClass> dataClasses = mf.getMainData().getClasses();
				ArrayList<Student> dataStudents = dataClasses.get(i-1).getStudents();
				dataStudents.forEach((n) -> cbStudent.addItem(n.getName()));
				
				//Update the Course combobox with eventual new items from the selected class
				cbCourse.removeAllItems();
				cbCourse.addItem("Kurs");
				ArrayList<Course> dataCourses = dataStudents.get(mf.getCurrentlySelectedStudentIndex()).getCourses();
				dataCourses.forEach((n) -> cbCourse.addItem(n.getName()));
				cbCourse.addItem("Ny");
				cbCourse.addItem("Ändra");
				
				//update the Task combobox with eventual new items from the selected class.
				cbTask.removeAllItems();
				//If there is a course in the selected class:
				if (dataCourses.size() != 0) {
					cbTask.addItem("Samlad vy");
					//Add every object in the eventual tasks in the course to the task combobox.
					ArrayList<Task> dataTasks = dataCourses.get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks();
					dataTasks.forEach((n) -> cbTask.addItem(n.getName()));
					cbTask.addItem("Ny");
					cbTask.addItem("Ändra");
				//Otherwise do not display any selectable objects in the combobox.
				} else {
					cbTask.addItem("Saknas kurs");
				}
				
			//selected the "Change" option
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(SchoolClass.class, false);
				mf.setCurrentlySelectedClassIndex(itmCount-2);
				mf.updateGradePanel(State.NOTHING_SELECTED);
			//selected the "New" option
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(SchoolClass.class, true);
				mf.setCurrentlySelectedClassIndex(itmCount-2);
				mf.updateGradePanel(State.NOTHING_SELECTED);
			}
		});
		
		//Add the ActionListener to the Course combobox.
		cbCourse.addActionListener((e) -> {
			if (!cbCourse.isFocusOwner() || isRefreshing) return;
			
			System.out.println("Course combobox updating");
			
			int i = cbCourse.getSelectedIndex();
			int itmCount = cbCourse.getItemCount();
			
			if (i == 0) { 
				mf.updateGradePanel(State.CLASS);
			} else if (i != -1 && i < itmCount - 2) {
				mf.setCurrentlySelectedCourseIndex(i-1);
				mf.updateGradePanel(State.CLASS_COURSE_STUDENT);
				
				cbStudent.grabFocus();
				cbStudent.setSelectedIndex(0);
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(Course.class, false);
				mf.setCurrentlySelectedCourseIndex(0);
				mf.updateGradePanel(State.CLASS);
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(Course.class, true);
				mf.setCurrentlySelectedCourseIndex(0);
				mf.updateGradePanel(State.CLASS);
			}
		});
		
		//Add the ActionListener to the Student combobox.
		cbStudent.addActionListener((e) -> {
			if (!cbStudent.isFocusOwner() || isRefreshing) return;

			System.out.println("Student combobox updating");
			
			mf.setCurrentlySelectedStudentIndex(cbStudent.getSelectedIndex());
			
			mf.updateGradePanel(State.CLASS_COURSE_STUDENT);
			
			cbTask.grabFocus();
			cbTask.setSelectedIndex(0);
		});
		
		//Add the ActionListener to the Task combobox.
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
				mf.updateGradePanel(State.CLASS_COURSE_STUDENT);
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(Task.class, true);
				mf.setCurrentlySelectedAssignmentIndex(itmCount-3);
				mf.updateGradePanel(State.CLASS_COURSE_STUDENT);
			}
		});
		
		//Add the combobox items to the panel.
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
		//if mf.getGradeState is not initialized (nullptr) then default to the "NOTHING_SELECTED" state.
		State curState = (mf.getGradeState() == null) ? State.NOTHING_SELECTED : mf.getGradeState();
		System.out.println(curState.toString());
		
		switch(curState) {
		case NOTHING_SELECTED:
			cbCourse.setEnabled(false);
			cbStudent.setEnabled(false);
			cbTask.setEnabled(false);
			break;
		case CLASS:
			cbCourse.setEnabled(true);
			cbStudent.setEnabled(false);
			cbTask.setEnabled(false);
			break;
		default:
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
		//Set the refreshing boolean (so the actionlisteners do not get run)
		isRefreshing = true;
		
		//--------//
		System.out.println("Storing previously selected items...");
		ArrayList<String> prevSelectedItems = new ArrayList<>();
		try {
			prevSelectedItems.add(cbClass.getSelectedItem().toString());
			prevSelectedItems.add(cbCourse.getSelectedItem().toString());
			prevSelectedItems.add(cbStudent.getSelectedItem().toString());
			prevSelectedItems.add(cbTask.getSelectedItem().toString());
		} catch (java.lang.NullPointerException e) {
			for (int i = 0; i<4; i++) {
				prevSelectedItems.add(null);
			}
		}
		
		//--------//
		System.out.println("Refreshing Data combobox data");
		System.out.println("Deleting old items...");
		cbClass.removeAllItems();
		cbCourse.removeAllItems();
		cbStudent.removeAllItems();
		cbTask.removeAllItems();
		
		//--------//
		System.out.println("Adding default combobox items...");
		cbClass.addItem("Klass");
		cbCourse.addItem("Kurs");
		cbTask.addItem("Samlad vy");
		
		System.out.println("Getting new data...");
		
		Data localData = newData;
		
		ArrayList<SchoolClass> dataClasses = localData.getClasses();
		
		if (dataClasses.size() != 0) {
			
			dataClasses.forEach((n) -> cbClass.addItem(n.getName()));
			
			ArrayList<Student> dataStudents = null;
			
			try {
				dataStudents = dataClasses.get(mf.getCurrentlySelectedClassIndex()).getStudents();
			} catch (java.lang.IndexOutOfBoundsException e) {
				dataStudents = dataClasses.get(0).getStudents();
			}
			
			if (dataStudents.size() != 0) {
				
				dataStudents.forEach((n) -> cbStudent.addItem(n.getName()));
				
				ArrayList<Course> dataCourses = null;
				
				try {
					dataCourses = dataStudents.get(mf.getCurrentlySelectedStudentIndex()).getCourses();
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
		}
		
		System.out.println("Adding change items...");
		
		cbClass.addItem("Ny");
		cbClass.addItem("Ändra");
		cbCourse.addItem("Ny");
		cbCourse.addItem("Ändra");
		cbTask.addItem("Ny");
		cbTask.addItem("Ändra");
		
		//Set the comboboxes to their default items whenever we selected to create a new or to change an item.
		if (hasInitialized) {
			State tempState = State.NOTHING_SELECTED;
			{
				String ch = prevSelectedItems.get(0);
				if (ch != null) {
					switch(ch) {
					case "Ny":
						cbClass.setSelectedIndex(0);
						mf.setCurrentlySelectedClassIndex(0);
						/*
						int newItmIndx = cbClass.getItemCount()-3;
						
						cbClass.setSelectedIndex(newItmIndx);
						mf.setCurrentlySelectedClassIndex(newItmIndx);
						
						
						//Update the Course combobox with eventual linked items from the selected class
						cbCourse.removeAllItems();
						cbCourse.addItem("Kurs");
						dataCourses = mf.getMainData().getClasses().get(newItmIndx-1).getStudents().get(0).getCourses();
						dataCourses.forEach((n) -> cbCourse.addItem(n.getName()));
						cbCourse.addItem("Ny");
						cbCourse.addItem("Ändra");
						*/
						
						cbCourse.setSelectedIndex(0);
						break;
					case "Ändra":
						cbClass.setSelectedIndex(0);
						break;
					default:
						cbClass.setSelectedItem(ch);
					}
				}
			}
			
			{
				String ch = prevSelectedItems.get(1);
				if (ch != null) {
					switch(ch) {
					case "Ny":
						cbCourse.setSelectedIndex(0);
						mf.setCurrentlySelectedCourseIndex(0);
						
						tempState = State.CLASS;
						break;
					case "Ändra":
						cbCourse.setSelectedIndex(0);
						break;
					default:
						cbCourse.setSelectedItem(ch);
					}
				}
			}
			
			cbStudent.setSelectedItem(prevSelectedItems.get(2));
			
			{
				String ch = prevSelectedItems.get(3);
				if (ch != null) {
					switch(ch) {
					case "Ny":
						cbTask.setSelectedIndex(0);
						mf.setCurrentlySelectedAssignmentIndex(0);
						/*
						cbTask.setSelectedIndex(cbTask.getItemCount()-3);
						mf.setCurrentlySelectedAssignmentIndex(cbTask.getSelectedIndex()-1);
						*/
						tempState = State.CLASS_COURSE_STUDENT_TASK;
						break;
					case "Ändra":
						cbTask.setSelectedIndex(0);
						break;
					default:
						cbTask.setSelectedItem(ch);
					}
				}
			}
			
			System.out.println(tempState.toString());
			if (tempState != State.NOTHING_SELECTED) mf.updateGradePanel(tempState);
			this.handleNewState();
		}
		
		
		
		//Reset the refreshing boolean (so the actionlisteners get run again)
		isRefreshing = false;
	}
}
