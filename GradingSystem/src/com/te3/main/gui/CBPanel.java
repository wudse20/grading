package com.te3.main.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.te3.main.objects.Course;
import com.te3.main.objects.Data;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Task;

/**
 *	Du behöver fixa en metod i MainFrame för att uppdatera din panel.
 *	Du behöver även uppdatera GUI:t och sätta vilket stadie panelen är i
 *	m.h.a. updateGradeState(state), där state är enumet State. Samt kalla
 *	updateGUI i mainframe. Vid uppdatering.
 */
public class CBPanel extends JPanel {
	/** Default */
	private static final long serialVersionUID = 1L;

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
		
		cbClass.addActionListener((e) -> {
			System.out.println(cbClass.getSelectedItem().toString());
			
			int i = cbClass.getSelectedIndex();
			
			System.out.println(i);
			if (i != -1 && i < cbClass.getItemCount() - 2) {
				mf.setCurrentlySelectedClassIndex(i);
			} else {
				System.out.println("Selected new or change");
				mf.openAddEditGUI(SchoolClass.class, true);
			}
		});
		cbCourse.addActionListener((e) -> {
			System.out.println(cbCourse.getSelectedItem().toString());
			
			int i = cbCourse.getSelectedIndex();
			
			System.out.println(i);
			if (i != -1 && i < cbCourse.getItemCount() - 2) {
				mf.setCurrentlySelectedCourseIndex(i);
			} else {
				System.out.println("Selected new or change");
				mf.openAddEditGUI(Course.class, true);
			}
		});
		cbStudent.addActionListener((e) -> {
			System.out.println(cbStudent.getSelectedItem().toString());
			System.out.println(cbStudent.getSelectedIndex());
			mf.setCurrentlySelectedStudentIndex(cbStudent.getSelectedIndex());
		});
		cbTask.addActionListener((e) -> {
			System.out.println(cbTask.getSelectedItem().toString());
			
			int i = cbTask.getSelectedIndex();
			
			System.out.println(i);
			if (i != -1 && i < cbTask.getItemCount() - 2) {
				mf.setCurrentlySelectedAssingmentIndex(cbTask.getSelectedIndex());
			} else {
				System.out.println("Selected new or change");
				mf.openAddEditGUI(Task.class, true); //Måste ändras ifall det är ny eller ändra.
			}
		});
		
		this.add(cbClass);
		this.add(cbCourse);
		this.add(cbStudent);
		this.add(cbTask);
	}
	
	/**
	 * Completely updates the entire combobox panel with new information.
	 * @param newData the new information to be parsed and updated with.
	 */
	private void refreshData(Data newData) {
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
