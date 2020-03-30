package com.te3.main.gui;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.te3.main.enums.State;
import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Course;
import com.te3.main.objects.Criteria;
import com.te3.main.objects.Data;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Settings;
import com.te3.main.objects.Student;
import com.te3.main.objects.Task;
import com.te3.main.objects.XML;

/**
 * The mainframe of the program.
 */
public class MainFrame extends JFrame implements ComponentListener {

	/** Default */
	private static final long serialVersionUID = 1L;

	/** The main data of the program */
	private Data mainData;

	/** The settings */
	private Settings settings;

	/**Timer in seconds*/
	private int saveTimer;
	private int currentlySelectedClassIndex = 0;
	private int currentlySelectedCourseIndex = 0;
	private int currentlySelectedAssignmentIndex = 0;
	private int currentlySelectedStudentIndex = 0;

	/** The currently selected yoda */
	private String currentYoda;
	private String saveFilePath;
	private String settingsFilePath;

	private boolean shouldShowBabyYoda;

	BoxLayout pLayout;

	CBPanel cbPanel;
	GradesPanel gradePanel;
	ButtonPanel btnPanel;

	Container cp = this.getContentPane();

	JPanel panel = new JPanel();

	JLabel lblBackground = new JLabel();

	private State s;

	Timer t;

	/**
	 * Sets everything in the program up, <br>
	 * that's needed.
	 * */
	public MainFrame() {
		super("Betygssättning");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1200, 600));

		this.setContentPane(lblBackground);

		if (new File("./settings.xml").exists())
			settings = loadSettings();
		else
			settings = new Settings("./saves.xml", 300, false, "yoda1.jpg");

		this.saveFilePath = settings.getSavePath();
		this.saveTimer = settings.getSaveTimer();
		this.shouldShowBabyYoda = settings.isShouldShowYoda();
		this.currentYoda = settings.getCurrentYoda();

		mainData = getSavedData();

		t = new Timer(saveTimer * 1000, (e) -> {
			saveData(saveFilePath);
		});

		// Hooks in to the shutdown sequence and writes to the files and then exits.
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			saveSettings();
			saveData(saveFilePath);
		}));

		this.addComponentListener(this);

		t.start();

		initComponents();
	}

	/**
	 * Initializes the components
	 * */
	private void initComponents() {
		pLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

		cbPanel = new CBPanel(mainData, this);
		gradePanel = new GradesPanel(this);
		btnPanel = new ButtonPanel(this);

		panel.setLayout(pLayout);
		panel.setOpaque(false);
		panel.add(cbPanel);
		panel.add(gradePanel);
		panel.add(btnPanel);

        setBabyYoda(shouldShowBabyYoda);

        this.add(panel);
		panel.setBounds(0, 0, this.getWidth() - 15, this.getHeight());
	}


    /**
     * Set up for Baby Yoda
     *
     * @param shouldShowBabyYoda if {@code true} -> shows Baby yoda, else not
     */
    public void setBabyYoda(boolean shouldShowBabyYoda) {
        if (shouldShowBabyYoda) {
            cbPanel.setOpaque(false);
            gradePanel.setOpaque(false);
            btnPanel.setOpaque(false);
        } else {
            cbPanel.setOpaque(true);
            gradePanel.setOpaque(true);
            btnPanel.setOpaque(true);
        }

        gradePanel.yoda(shouldShowBabyYoda);
        cbPanel.yoda(shouldShowBabyYoda);

		/*
		 * Scales the background image to the size of
		 * the JFrame. To save on performance it only
		 * dose the calculations while the background
		 * is selected.
		 * */
		if (shouldShowBabyYoda) {
			BufferedImage img = null;

			try {
				img = ImageIO.read(new File("./src/images/" + currentYoda));
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			Image scaleImg = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
			lblBackground.setIcon(new ImageIcon(scaleImg));
		}

        cbPanel.repaint();
        gradePanel.repaint();
        this.repaint();
	}

	/**
	 * Loads the settings into memory
	 * */
	public Settings loadSettings() {
		this.settingsFilePath = "./settings.xml";
		XML<Settings> xml = new XML<Settings>();
		return xml.read(this.settingsFilePath);
	}

	/**
	 * Saves the settings
	 * */
	public void saveSettings() {
		this.settingsFilePath = "./settings.xml";
		XML<Settings> xml = new XML<Settings>();
		xml.write(this.settingsFilePath, this.settings);
	}

	/**
	 * Saves the data
	 * */
	public void saveData(String filePath) {
		XML<Data> xml = new XML<Data>();
		xml.write(filePath, this.mainData);
	}

	/**
	 * Loads the data in to memory
	 * */
	public Data loadData(String filePath) {
		XML<Data> xml = new XML<Data>();
		Data d;
		d = xml.read(filePath);

		if (d == null) {
			d = new Data(new ArrayList<SchoolClass>());
		}

		return d;
	}

	/**
	 * Get's the saved data <br>
	 * It will also create a debug student if needed.
	 * */
	private Data getSavedData() {
		// should be able to find local stored data, and import it. for now we just have
		// it create a new empty data object.
		// change this boolean to false if you do not want to run the program with
		// default predefined classes, courses, students and tasks.

		boolean debug = false;

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
				criteria.add(new Criteria("Test"));
				criteria.add(new Criteria("Test 2"));

				criteria2.add(new Criteria("Problemlösning"));
				criteria2.add(new Criteria("Fjäsk"));
				criteria2.add(new Criteria("Test"));
				criteria2.add(new Criteria("Test 2"));

				tasks.add(new Task("Lego robot", criteria2));

				students.get(0).addCourse(new Course("Teknik", criteria, tasks));

				classes.add(new SchoolClass("TE3B", students));
			} catch (IllegalNameException | IllegalInputException e) {
				e.printStackTrace();
			}

			return new Data(classes);
		} else {
			return loadData(saveFilePath);
		}
	}

	/**
	 * Updates the state of the GUI
	 *
	 * @param s the new state of the GUI
	 * */
	public void updateGradeState(State s) {
		this.s = s;
	}

	/**
	 * Updates the grade panel GUI and information
	 * */
	public void updateGradePanel() {
		gradePanel.update(s);
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
				new ListUpdateChooser<Course>(this,
						mainData.getClasses().get(currentlySelectedClassIndex).getStudents().get(0).getCourses(),
						(Class<Course>) clazz).setVisible(true);
			} else if (clazz.equals(Task.class)) {
				new ListUpdateChooser<Task>(this,
						mainData.getClasses().get(currentlySelectedClassIndex).getStudents().get(0).getCourses()
								.get(currentlySelectedCourseIndex).getCourseTasks(),
						(Class<Task>) clazz).setVisible(true);
			}
		}
	}

	/**
	 * Getter for the main data
	 * */
	public Data getMainData() {
		return mainData;
	}

	/**
	 * Setter for the main data
	 * */
	public void setMainData(Data mainData) {
		this.mainData = mainData;
	}

	/**
	 * Getter for the save timer
	 * */
	public int getSaveTimer() {
		this.saveTimer = settings.getSaveTimer();
		return this.saveTimer;
	}

	/**
	 * Sets the new value and restarts the timer.
	 * */
	public void setSaveTimer(int saveTimer) throws IllegalInputException {
		settings.setSaveTimer(saveTimer);
		this.saveTimer = saveTimer;
		t.stop();
		t.setDelay(saveTimer * 1000);
		t.start();
	}

	/**
	 * A setter for the settings save file path
	 * */
	public String getSettingsFilePath() {
		this.settingsFilePath = "./settings.xml";
		return settingsFilePath;
	}

	/**
	 * A getter for the save file path
	 * */
	public String getSaveFilePath() {
		return saveFilePath;
	}

	/**
	 * Sets the save file path
	 *
	 * @throws if the file path isn't accepted.
	 * */
	public void setSaveFilePath(String saveFilePath) throws IllegalInputException {
		try {
			settings.setSavePath(saveFilePath);
			this.saveFilePath = saveFilePath;
		} catch (IllegalInputException e) {
			throw e;
		}
	}

	/**
	 * A getter for the currently selected class index
	 * */
	public int getCurrentlySelectedClassIndex() {
		return currentlySelectedClassIndex;
	}

	/**
	 * A setter for the currently selected class index
	 * */
	public void setCurrentlySelectedClassIndex(int currentlySelectedClassIndex) {
		this.currentlySelectedClassIndex = currentlySelectedClassIndex;
	}

	/**
	 * A getter for the currently selected course index.
	 * */
	public int getCurrentlySelectedCourseIndex() {
		return currentlySelectedCourseIndex;
	}

	/**
	 * A setter for the currently selected course index.
	 * */
	public void setCurrentlySelectedCourseIndex(int currentlySelectedCourseIndex) {
		this.currentlySelectedCourseIndex = currentlySelectedCourseIndex;
	}

	/**
	 * A getter for the currently selected assignment index.
	 * */
	public int getCurrentlySelectedAssignmentIndex() {
		return currentlySelectedAssignmentIndex;
	}

	/**
	 * A setter for the currently selected assignment index
	 * */
	public void setCurrentlySelectedAssignmentIndex(int currentlySelectedAssignmentIndex) {
		this.currentlySelectedAssignmentIndex = currentlySelectedAssignmentIndex;
	}

	/**
	 * A getter for the currently selected student index.
	 * */
	public int getCurrentlySelectedStudentIndex() {
		return currentlySelectedStudentIndex;
	}

	/**
	 * A setter for the currently selected student index.
	 * */
	public void setCurrentlySelectedStudentIndex(int currentlySelectedStudentIndex) {
		this.currentlySelectedStudentIndex = currentlySelectedStudentIndex;
	}

	/**
	 * A getter for the grade panel
	 * */
	public GradesPanel getGradePanel() {
		return gradePanel;
	}

	/**
	 * A getter for the settings
	 * */
	public Settings getSettings() {
		return this.settings;
	}

	/**
	 * A setter for the settings.
	 * */
	public void setSettings(Settings s) {
		this.settings = s;
		this.saveFilePath = s.getSavePath();
		this.saveTimer = s.getSaveTimer();
		this.shouldShowBabyYoda = s.isShouldShowYoda();
	}

	/**
     * A getter for should show baby yoda
     * */
    public boolean shouldShowBabyYoda() {
	    return this.shouldShowBabyYoda;
    }

    public void setShouldShowBabyYoda(boolean shouldShowBabyYoda) {
        this.shouldShowBabyYoda = shouldShowBabyYoda;
	}

	/**
	 * A getter for current yoda.
	 *
	 * @return the current yoda.
	 * */
	public String getCurrentYoda() {
		return currentYoda;
	}

	/**
	 * A setter for current yoda.
	 *
	 * @param currentYoda the current yoda
	 * */
	public void setCurrentYoda(String currentYoda) {
		this.currentYoda = currentYoda;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		panel.setBounds(0, 0, this.getWidth() - 15, this.getHeight() - btnPanel.getHeight());

		/*
		 * Scales the background image to the size of
		 * the JFrame. To save on performance it only
		 * dose the calculations while the background
		 * is selected.
		 * */
		if (shouldShowBabyYoda) {
			BufferedImage img = null;

			try {
				img = ImageIO.read(new File("./src/images/" + currentYoda));
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			Image scaleImg = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
			lblBackground.setIcon(new ImageIcon(scaleImg));
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {

	}

	@Override
	public void componentShown(ComponentEvent e) {

	}

	@Override
	public void componentHidden(ComponentEvent e) {

	}
}
