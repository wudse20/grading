package com.te3.main.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.Timer;

import com.te3.main.enums.Grades;
import com.te3.main.enums.State;
import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Course;
import com.te3.main.objects.Criteria;
import com.te3.main.objects.Data;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Student;
import com.te3.main.objects.Task;
import com.te3.main.objects.XML;

/**
 * The mainframe of the program.
 * 
 * @author Anton Skorup, Jon Westring
 */
public class MainFrame extends JFrame {

	/** Default */
	private static final long serialVersionUID = 1L;

	private Data mainData;

	// in seconds
	private int saveTimer = 300;
	private int currentlySelectedClassIndex = 0;
	private int currentlySelectedCourseIndex = 0;
	private int currentlySelectedAssingmentIndex = 0;
	private int currentlySelectedStudentIndex = 0;

	private String saveFilePath;

	BoxLayout mainLayout;

	CBPanel cbPanel;
	GradesPanel gradePanel;
	ButtonPanel btnPanel;

	Container cp = this.getContentPane();

	public MainFrame() {
		super("Betygssättning");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1200, 600));

		Timer t = new Timer(saveTimer * 1000, (e) -> {
			save(saveFilePath);
		});

		// Hooks in to the shutdown sequence and writes to the file and then exits.
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			save(saveFilePath);
		}));

		mainData = getSavedData();
		this.saveFilePath = mainData.getSavePath();

		t.start();

		initComponents();
	}

	private void initComponents() {
		mainLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		this.setLayout(mainLayout);

		cbPanel = new CBPanel(mainData, this);
		gradePanel = new GradesPanel(this);
		btnPanel = new ButtonPanel(this);

		cp.add(cbPanel);
		cp.add(gradePanel);
		cp.add(btnPanel);
	}

	public void save(String filePath) {
		XML<Data> xml = new XML<Data>();
		xml.write(filePath, this.mainData);
	}

	public Data load(String filePath) {
		XML<Data> xml = new XML<Data>();
		Data d;
		d = xml.read(filePath);

		if (d == null) {
			d = new Data(new ArrayList<SchoolClass>(), new ArrayList<Course>());
		}

		return d;
	}

	private Data getSavedData() {
		// should be able to find local stored data, and import it. for now we just have
		// it create a new empty data object.
		// change this boolean to false if you do not want to run the program with
		// default predefined classes, courses, students and tasks.

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

			try {
				courseCriteria.add(new Criteria("Mekanik", this));
				courseCriteria.add(new Criteria("Genus", this));
				courseCriteria.add(new Criteria("Teknikhistoria", this));
				courseCriteria.add(new Criteria("CAD", this));
				courseCriteria.add(new Criteria("Programmering", this));
				courseCriteria.add(new Criteria("Fjäsk", this));
				courseCriteria.add(new Criteria("Glass", this));
			} catch (IllegalNameException e) {
				e.printStackTrace();
			}

			Course teknik = null;
			try {
				teknik = new Course("Teknik", courseCriteria);
			} catch (IllegalNameException e) {
				// TODO handle illegal name, sätt ruta röd typ
				e.printStackTrace();
			}

			ArrayList<Criteria> c = new ArrayList<Criteria>();

			try {
				c.add(new Criteria("Mekanik", this));
				c.add(new Criteria("CAD", this));
				c.add(new Criteria("Fjäsk", this));
			} catch (IllegalNameException e) {
				e.printStackTrace();
			}

			c.get(0).setGrade(Grades.C);
			c.get(1).setGrade(Grades.E);
			c.get(2).setGrade(Grades.A);

			teknik.addCourseTask(new Task("Vattenhallen", teknik.getCourseCriteria(), studentsA));
			teknik.addCourseTask(new Task("Teknikhistoria", c, studentsA));

			courses.add(teknik);

			return new Data(classes, courses);
		} else {
			return load(saveFilePath);
		}
	}

	public void updateGradeGUI() {
		gradePanel.updateGUI(gradePanel.getState());
	}
	
	public void updateState(State s) {
		gradePanel.setState(s);
	}
	
	public Data getMainData() {
		return mainData;
	}

	public void setMainData(Data mainData) {
		this.mainData = mainData;
	}

	public int getSaveTimer() {
		return saveTimer;
	}

	public void setSaveTimer(int saveTimer) throws IllegalInputException {
		if (saveTimer > 60) {
			this.saveTimer = saveTimer;
		} else {
			// Gör text ruta röd sen eller ngt.
			throw new IllegalInputException("Intervallet måste vara längre");
		}
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) throws IllegalInputException {
		try {
			mainData.setSavePath(saveFilePath);
			this.saveFilePath = saveFilePath;
		} catch (IllegalInputException e) {
			throw e;
		}
	}

	public int getCurrentlySelectedClassIndex() {
		return currentlySelectedClassIndex;
	}

	public void setCurrentlySelectedClassIndex(int currentlySelectedClassIndex) {
		this.currentlySelectedClassIndex = currentlySelectedClassIndex;
	}

	public int getCurrentlySelectedCourseIndex() {
		return currentlySelectedCourseIndex;
	}

	public void setCurrentlySelectedCourseIndex(int currentlySelectedCourseIndex) {
		this.currentlySelectedCourseIndex = currentlySelectedCourseIndex;
	}

	public int getCurrentlySelectedAssingmentIndex() {
		return currentlySelectedAssingmentIndex;
	}

	public void setCurrentlySelectedAssingmentIndex(int currentlySelectedAssingmentIndex) {
		this.currentlySelectedAssingmentIndex = currentlySelectedAssingmentIndex;
	}

	public int getCurrentlySelectedStudentIndex() {
		return currentlySelectedStudentIndex;
	}

	public void setCurrentlySelectedStudentIndex(int currentlySelectedStudentIndex) {
		this.currentlySelectedStudentIndex = currentlySelectedStudentIndex;
	}

	public GradesPanel getGradePanel() {
		return gradePanel;
	}
}
