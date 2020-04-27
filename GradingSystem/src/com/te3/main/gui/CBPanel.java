package com.te3.main.gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.te3.main.enums.State;
import com.te3.main.objects.Course;
import com.te3.main.objects.Data;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Task;

/**
 *	Du behöver fixa en metod i MainFrame för att uppdatera din panel.
 *	Du behöver även uppdatera GUI:t och sätta vilket stadie panelen är i
 *	m.h.a. mf.updateGradeState(State), med rätt state. Sedan mf.updateGradePanel()
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
		System.out.println(compareTime - (initTime + 3000));
		if (compareTime > (initTime + 3000)) {
			return false;
		} else {
			return true;
		}
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
			if (checkInitTime(e.getWhen())) return;
			
			int i = cbClass.getSelectedIndex();
			int itmCount = cbClass.getItemCount();
			
			if (i != -1 && i < itmCount - 2) {
				mf.setCurrentlySelectedClassIndex(i);
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(SchoolClass.class, false);
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(SchoolClass.class, true);
			}
			
			this.refreshData(mf.getMainData());
			mf.updateGradePanel();
		});
		
		//course combobox
		cbCourse.addActionListener((e) -> {
			if (checkInitTime(e.getWhen())) return;
			
			int i = cbCourse.getSelectedIndex();
			int itmCount = cbCourse.getItemCount();
			
			if (i != -1 && i < itmCount - 2) {
				mf.setCurrentlySelectedCourseIndex(i);
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(Course.class, false);
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(Course.class, true);
			}
			
			this.refreshData(mf.getMainData());
			mf.updateGradePanel();
		});
		
		//student combobox
		cbStudent.addActionListener((e) -> {
			if (checkInitTime(e.getWhen())) return;
			
			mf.setCurrentlySelectedStudentIndex(cbStudent.getSelectedIndex());
		});
		
		//task combobox
		cbTask.addActionListener((e) -> {
			if (checkInitTime(e.getWhen())) return;
			
			int i = cbTask.getSelectedIndex();
			int itmCount = cbTask.getItemCount();
			
			if (i != -1 && i < itmCount - 2) {
				mf.updateGradeState(State.CLASS_COURSE_STUDENT_TASK);
				mf.setCurrentlySelectedAssignmentIndex(cbTask.getSelectedIndex());
				mf.updateGradePanel();
			} else if (i == 0) {
				mf.updateGradeState(State.CLASS_COURSE_STUDENT);
				mf.setCurrentlySelectedAssignmentIndex(cbTask.getSelectedIndex());
				mf.updateGradePanel();
			} else if (i == itmCount - 1) {
				mf.openAddEditGUI(Task.class, false);
			} else if (i == itmCount - 2) {
				mf.openAddEditGUI(Task.class, true);
			}
			
			this.refreshData(mf.getMainData());
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
	 * Completely updates the entire combobox panel with new information.
	 * @param newData the new information to be parsed and updated with.
	 */
	public void refreshData(Data newData) {
		cbClass.removeAllItems();
		cbCourse.removeAllItems();
		cbStudent.removeAllItems();
		cbTask.removeAllItems();
		
		Data localData = newData;
		
		ArrayList<SchoolClass> dataClasses = localData.getClasses();
//		Sorry att jag pajjade detta, du får gå på klassen som är vald.
//		ArrayList<Course> dataCourses = localData.getCourses();
		
		cbStudent.addItem("Samlad vy");
		cbTask.addItem("Samlad vy");
		
		dataClasses.forEach((n) -> cbClass.addItem(n.getName()));
//		dataCourses.forEach((n) -> cbCourse.addItem(n.getName()));
		
		cbClass.addItem("Ny");
		cbClass.addItem("Ändra");
		cbCourse.addItem("Ny");
		cbCourse.addItem("Ändra");
		cbTask.addItem("Ny");
		cbTask.addItem("Ändra");
	}
}
