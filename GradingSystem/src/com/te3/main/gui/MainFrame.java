package com.te3.main.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.Timer;

import com.te3.main.enums.Grade;
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

	private State s;

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

	/**
	 * Might throw null pointers for now nothing to worry about.
	 * */
	public void save(String filePath) {
		XML<Data> xml = new XML<Data>();
		xml.write(filePath, this.mainData);
	}

	public Data load(String filePath) {
		XML<Data> xml = new XML<Data>();
		Data d;
		d = xml.read(filePath);

		if (d == null) {
			d = new Data(new ArrayList<SchoolClass>());
		}

		return d;
	}

	private Data getSavedData() {
		// should be able to find local stored data, and import it. for now we just have
		// it create a new empty data object.
		// change this boolean to false if you do not want to run the program with
		// default predefined classes, courses, students and tasks.
		// Kommenterat bort för att spar debug tid

		boolean debug = true;

		if (debug) {
			ArrayList<SchoolClass> classes = new ArrayList<SchoolClass>();
			ArrayList<Student> students = new ArrayList<Student>();
			ArrayList<Criteria> criteria = new ArrayList<Criteria>();
			ArrayList<Criteria> criteria2 = new ArrayList<Criteria>();
			ArrayList<Task> tasks = new ArrayList<Task>();

			try {
				students.add(new Student("Anton"));

				criteria.add(new Criteria("Problemlösning"));
				criteria.add(new Criteria("Fjäsk"));

				criteria2.add(new Criteria("Problemlösning"));
				criteria2.add(new Criteria("Fjäsk"));

				tasks.add(new Task("Lego robot", criteria2));

				students.get(0).addCourse(new Course("Teknik", criteria, tasks));

				classes.add(new SchoolClass("TE3B", students));
			} catch (IllegalNameException | IllegalInputException e) {
				e.printStackTrace();
			}

			return new Data(classes);
		} else {
			return load(saveFilePath);
		}
	}

	public void updateGradeState(State s) {
		this.s = s;
	}

	public void updateGUI() {
		gradePanel.updateGUI(s);
	}

	/**
	 * Used to show the frames that are handeling the updates.
	 *
	 * @param <E>       The class that's being edited/added
	 * @param clazz     The class that's being edited/added
	 * @param isAddMode <br>
	 *                  If true -> add mode<br>
	 *                  If false -> edit mode
	 */
	@SuppressWarnings("unchecked")
	public <E> void openAddEditGUI(Class<E> clazz, boolean isAddMode) {
		if (isAddMode) {
			if (clazz.equals(SchoolClass.class)) {
				new SchoolClassFrame(this).setVisible(true);
			} else if (clazz.equals(Course.class)) {
				new CourseFrame(this).setVisible(true);
			} else if (clazz.equals(Task.class)) {
				new AssignmentFrame(this).setVisible(true);
			}
		} else {
			if (clazz.equals(SchoolClass.class)) {
				new ListUpdateChooser<SchoolClass>(this, mainData.getClasses(), (Class<SchoolClass>) clazz)
						.setVisible(true);
			} else if (clazz.equals(Course.class)) {
				new ListUpdateChooser<Course>(this, mainData.getClasses().get(currentlySelectedClassIndex).getStudents().get(0).getCourses(),
						(Class<Course>) clazz).setVisible(true);
			} else if (clazz.equals(Task.class)) {
				new ListUpdateChooser<Task>(this,
						mainData.getClasses().get(currentlySelectedClassIndex).getStudents().get(0).getCourses().get(currentlySelectedCourseIndex).getCourseTasks(),
						(Class<Task>) clazz).setVisible(true);
			}
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
