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
	
	//Sparar en instans för att kunna använda getters och setters och slippa static
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
		//main panel
		//Vet inget annat sätt att få tag på dig
		//Kör inte GridLayout, Einar skälde ut mig
		//när jag gjorde det. Det är för att GUI:s
		//blir typ jättefula, då allt måste vara lika stort.
		
		/*
		 * Har fixat problemet med gridlayout
		 * Det är en positiv grej att allting måste ha samma storlek i det här användningsområdet
		 * därför att labels och comboboxes kommer ha samma plats horisontellt utan större problem.
		 */
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
		
		/*
		 * Använd lambda det är mycket mer effektivt
		 * när det kommmer till prestanda, enligt Einar iallafall.
		 * 
		 * cbClass.addActionListener((e) -> {
		 * 		//Fräsig kod
		 * });
		 * 
		 * Dessa behöver väl även kalla updateGUI i gradespanel på ngt sätt också.
		 * */
		cbClass.addActionListener((e) -> {
			System.out.println(cbClass.getSelectedItem().toString());
			mf.setCurrentlySelectedClassIndex(cbClass.getSelectedIndex());
		});
		cbCourse.addActionListener((e) -> {
			System.out.println(cbCourse.getSelectedItem().toString());
			mf.setCurrentlySelectedCourseIndex(cbCourse.getSelectedIndex());
		});
		cbStudent.addActionListener((e) -> {
			System.out.println(cbStudent.getSelectedItem().toString());
			mf.setCurrentlySelectedStudentIndex(cbStudent.getSelectedIndex());
		});
		cbTask.addActionListener((e) -> {
			System.out.println(cbTask.getSelectedItem().toString());
			mf.setCurrentlySelectedAssingmentIndex(cbTask.getSelectedIndex());
		});
		
		this.add(cbClass);
		this.add(cbCourse);
		this.add(cbStudent);
		this.add(cbTask);
	}
	
	/**
	 * Adds the default items to the comboboxes (eg. new entry, edit entries)
	 */
	private void addDefaultItems() {
		cbClass.addItem("Ny");
		cbClass.addItem("Ändra");
		cbCourse.addItem("Ny");
		cbCourse.addItem("Ändra");
		//shouldnt be able to add new students through the combobox
		cbTask.addItem("Ny");
		cbTask.addItem("Ändra");
	}
	
	/**
	 * Removes all items from the comboboxes
	 */
	private void resetComboboxes() {
		cbClass.removeAllItems();
		cbCourse.removeAllItems();
		cbStudent.removeAllItems();
		cbTask.removeAllItems();
	}
	
	/**
	 * Completely updates the entire combobox panel with new information.
	 * @param newData the new information to be parsed and updated with.
	 */
	private void refreshData(Data newData) {
		resetComboboxes();
		
		Data localData = newData;
		ArrayList<SchoolClass> dataClasses = localData.getClasses();
		ArrayList<Course> dataCourses = localData.getCourses();
		
		dataClasses.forEach((n) -> cbClass.addItem(n.getName()));
		dataCourses.forEach((n) -> cbCourse.addItem(n.getName()));
		
		addDefaultItems();
	}
}
