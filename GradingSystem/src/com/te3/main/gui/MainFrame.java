package com.te3.main.gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.*;

public class MainFrame extends JFrame {
	
	/** Default */
	private static final long serialVersionUID = 1L;
	
	private Data mainData;

	public MainFrame() 
	{
		super("Betygssättning");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(400, 300));
		
		mainData = getSavedData();
	}
	
	public void save(String filePath) {
		XML<Data> xml = new XML<Data>();
		xml.write(filePath, this.mainData);
	}
	
	public void load(String filePath) {
		XML<Data> xml = new XML<Data>();
		Data d;
		d = xml.read(filePath);
		
		if (d == null) {
			mainData = new Data(new ArrayList<SchoolClass>(), new ArrayList<Course>());
		} else {
			mainData = d;
		}
	}
	
	private Data getSavedData() {
		//should be able to find local stored data, and import it. for now we just have it create a new empty data object.
		//change this boolean if you do not want to run the program with default predefined classes, courses, students and tasks.
		
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
			
			Course teknik = null;
			try {
				teknik = new Course("Teknik", classes, courseCriteria);
			} catch (IllegalNameException e) {
				// TODO handle illegal name, sätt ruta röd typ
				e.printStackTrace();
			}
			
			teknik.addCourseTask(new Task("Vattenhallen", teknik.getCourseCriteria()));
			
			courses.add(teknik);
			
			return new Data(classes, courses);
		} else {
			return new Data();
		}
	}

}
