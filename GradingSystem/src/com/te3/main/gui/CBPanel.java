package com.te3.main.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.te3.main.objects.*;

public class CBPanel extends JPanel {
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
	
	ActionListener cbUpdateListener;
	
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
		
		cbUpdateListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Borde bara ändras när användaren trycker på comboboxen. Går ej att använda actionCommand:et comboBoxChanged
				//eftersom att det actionCommand:et kallas när man lägger till nya items.
				JComboBox<String> item = (JComboBox<String>) e.getSource();
				String curSelected = item.getSelectedItem().toString();
				
				//Test
				System.out.println("Currently selected item: " + curSelected + ", with index: " + item.getSelectedIndex());
				mf.setCurrentlySelectedAssingmentIndex(item.getSelectedIndex());
			}
		};
		
		cbClass.addActionListener(cbUpdateListener);
		cbCourse.addActionListener(cbUpdateListener);
		cbStudent.addActionListener(cbUpdateListener);
		cbTask.addActionListener(cbUpdateListener);
		
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
