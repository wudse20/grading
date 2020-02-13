package com.te3.main.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.te3.main.objects.*;

public class MainFrame extends JFrame {
	
	/** Default */
	private static final long serialVersionUID = 1L;
	
	Data 	mainData;
	
	BoxLayout mainLayout;
	
	CBPanel cbPanel;
	
	JPanel infoPanel;
	JPanel controlPanel;

	public MainFrame() 
	{
		super("Betygssättning");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(400, 300));
		
		mainData = getSavedData();
		
		initComponents();
	}
	
	private void initComponents() {
		mainLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		this.setLayout(mainLayout);
		
		cbPanel = new CBPanel(mainData);
		
		this.add(cbPanel);
		
		infoPanel = new JPanel();
		this.add(infoPanel);
		
		controlPanel = new JPanel();
		this.add(controlPanel);
	}
	
	private Data getSavedData() {
		//should be able to find local stored data, and import it. for now we just have it create a new empty data object.
		//change this boolean to false if you do not want to run the program with default predefined classes, courses, students and tasks.
		
		boolean debug = true;
		
		if (debug) {
			ArrayList<SchoolClass> classes = new ArrayList<SchoolClass>();
			ArrayList<Course> courses = new ArrayList<Course>();
			ArrayList<Student> studentsA = new ArrayList<Student>();
			ArrayList<Student> studentsB = new ArrayList<Student>();
			ArrayList<Criteria> courseCriteria = new ArrayList<Criteria>();
			
			studentsA.add(new Student("Jon W"));
			studentsA.add(new Student("Adam G"));
			
			studentsB.add(new Student("Anton S"));
			studentsB.add(new Student("Liza H"));
			
			classes.add(new SchoolClass("TE3A", courses, studentsA));
			classes.add(new SchoolClass("TE3B", courses, studentsB));
			
			courseCriteria.add(new Criteria("Mekanik"));
			courseCriteria.add(new Criteria("Genus"));
			courseCriteria.add(new Criteria("Teknikhistoria"));
			
			Course teknik = new Course("Teknik", classes, courseCriteria);
			
			teknik.addCourseTask(new Task("Vattenhallen", teknik.getCourseCriteria()));
			
			courses.add(teknik);
			
			return new Data(classes, courses);
		} else {
			return new Data();
		}
	}
}
