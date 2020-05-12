package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Course;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Student;

/**
 * The class for managing the classes.
 */
public class MainSchoolClassPanel extends JPanel implements DocumentListener {

	/** Generated */
	private static final long serialVersionUID = -6445745910257748976L;

	/** The students */
	private ArrayList<Student> students = new ArrayList<Student>();

	/** If the timer is running or not */
	private boolean isFlashingTimerRunning = false;

	/** If the course linker window is opened */
	private boolean isCourseLinkerOpened = false;

	/** The ArrayList with linked courses */
	private ArrayList<Course> linkedCourses;

	// Instances
	SchoolClass sc;

	MainFrame mf;

	NamePanel np;

	/** The timer that is responsible for the blinking JList. */
	Timer flashTimer;

	// TextFields
	JTextField txfName = new JTextField(24);

	// Buttons
	JButton btnAddName = new JButton("Lägg till");
	JButton btnLinkToCourses = new JButton("Länka till kurser");

	// Lables
	JLabel lblStudents = new JLabel("Elever:");
	JLabel lblName = new JLabel("Elev:");
	JLabel lblSpacer = new JLabel(" ");

	// JList
	JList<Student> listStudents = new JList<Student>();

	// ScrollPane
	JScrollPane scrStudents = new JScrollPane(listStudents);

	// Panels
	JPanel pInput = new JPanel();
	JPanel pStudents = new JPanel();

	// Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pStudentsLayout = new BorderLayout();

	FlowLayout pInputLayout = new FlowLayout(FlowLayout.LEFT);

	// Frames
	LinkCoursesFrame lcf;

	/**
	 * For new class
	 * 
	 * @param mf the instance of the mainframe
	 */
	public MainSchoolClassPanel(MainFrame mf) {
		// Stores the instance
		this.mf = mf;

		// Sets the properties
		this.setProperties();

		// Adds the components
		this.addComponents(false);
	}

	/**
	 * For editing
	 * 
	 * @param mf the instance of the mainframe
	 * @param sc the school class that's being updated
	 */
	public MainSchoolClassPanel(MainFrame mf, SchoolClass sc) {
		// Stores the instances
		this.mf = mf;
		this.sc = sc;
		this.students = this.sc.getStudents();

		// Updates the JList
		this.updateJList();

		// Sets the properties
		this.setProperties();

		// Adds the components
		this.addComponents(true);
	}

	/**
	 * Adds the components
	 *
	 * @param isUpdateMode if {@code true} updating a course, else adding a course
	 */
	private void addComponents(boolean isUpdateMode) {
		pInput.add(lblName);
		pInput.add(txfName);
		pInput.add(btnAddName);

		if (!isUpdateMode)
			pInput.add(btnLinkToCourses);

		pStudents.add(lblStudents, BorderLayout.PAGE_START);
		pStudents.add(scrStudents, BorderLayout.CENTER);
		pStudents.add(lblSpacer, BorderLayout.PAGE_END);

		this.add(pInput, BorderLayout.PAGE_START);
		this.add(pStudents, BorderLayout.CENTER);
	}

	/**
	 * Sets the properties
	 */
	private void setProperties() {
		// Sets layouts
		this.setLayout(layout);
		pInput.setLayout(pInputLayout);
		pStudents.setLayout(pStudentsLayout);

		// Adds listeners
		btnAddName.addActionListener((e) -> {
			addStudents(txfName.getText());
		});

		listStudents.getSelectionModel().addListSelectionListener((e) -> {
			// Since it's called twice
			try {
				students.remove(listStudents.getSelectedIndex());
				stopFlashing();
				updateJList();
			} catch (IndexOutOfBoundsException ex) {
				return;
			}
		});

		txfName.getDocument().addDocumentListener(this);
		txfName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				/*
				 * If enter key, key code 10, is pressed, the add students.
				 */
				if (e.getKeyCode() == 10) {
					addStudents(txfName.getText());
				}
			}
		});

		btnLinkToCourses.addActionListener(e -> {
			// Checks if the window is opened.
			if (this.isCourseLinkerOpened) {
				// Sends message to user
				JOptionPane.showMessageDialog(this, "Du har redan öppnat denna ruta!", "Fel!",
						JOptionPane.ERROR_MESSAGE);

				// Sends debug message:
				System.out.println("["
						+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
								: LocalTime.now().getHour())
						+ ":"
						+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
								: LocalTime.now().getMinute())
						+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
								: LocalTime.now().getSecond())
						+ "] MainSchoolClassPanel: Link window already opened.");
			} else {
				// Opens window
				lcf = new LinkCoursesFrame(this, linkedCourses, mf);
				lcf.setVisible(true);

				// Tells the program that the window is opened.
				this.isCourseLinkerOpened = true;

				// Debug message:
				System.out.println("["
						+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
								: LocalTime.now().getHour())
						+ ":"
						+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
								: LocalTime.now().getMinute())
						+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
								: LocalTime.now().getSecond())
						+ "] MainSchoolClassPanel: Opened link window");
			}
		});
	}

	/**
	 * Adds the Students or student.
	 * 
	 * @param name takes a single name or names in the format of csv
	 */
	private void addStudents(String name) {
		// If the name is to short then it throws an error
		if (name.trim().length() == 0) {
			txfName.setBackground(Color.pink);
			JOptionPane.showMessageDialog(this, "Du måste skriva något i rutan", "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// If it's in format of csv or not
		if (name.indexOf(',') != -1) {
			// Splits the string by the comma
			String[] names = name.split(",");

			// Loops through the names and adds the students to the class
			// Also detects if something is wrong with a name then it will
			// tell the user that and remove all the added students.
			for (int i = 0; i < names.length; i++) {
				try {
					students.add(new Student(names[i].trim()));
				} catch (IllegalNameException e) {
					// removes the added students to avoid duplicates
					for (int j = 0; j < i; j++)
						students.remove((students.size() - 1));

					JOptionPane.showMessageDialog(this,
							"Fel vid namn nr: " + (i + 1) + ", Medelande: " + e.getMessage(), "Fel",
							JOptionPane.ERROR_MESSAGE);

					// Returns
					return;
				}
			}
		} else {
			try {
				// Adds a student
				students.add(new Student(name));
			} catch (IllegalNameException e) {
				// Shows the error message to the user
				JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);

				// Returns
				return;
			}
		}

		// Stops the flashing
		this.stopFlashing();

		// Clears the text box
		txfName.setText("");

		// Updates the JList
		updateJList();
	}

	/**
	 * Updates the JList
	 */
	private void updateJList() {
		// Gets the students and adds them
		Student[] students = new Student[this.students.size()];
		listStudents.setListData(this.students.toArray(students));
	}

	/**
	 * Makes the students list flash
	 *
	 * @param c1 The first color
	 * @param c2 The second color
	 * @parma interval The flashing speed in seconds
	 */
	public void startFlashing(final Color c1, final Color c2, double interval) {
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] Flashing timer running: " + isFlashingTimerRunning);

		// It will only start if it isn't running
		if (isFlashingTimerRunning)
			return;

		// Initializes the timer
		flashTimer = new Timer((int) (interval * 1000), new ActionListener() {
			/** The count of flashes */
			private int count = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * It switches between the colors every other time it runs. It will set c1 on
				 * even counts and c2 on odd counts.
				 */
				if (count % 2 == 0) {
					System.out.println("["
							+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
									: LocalTime.now().getHour())
							+ ":"
							+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
									: LocalTime.now().getMinute())
							+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
									: LocalTime.now().getSecond())
							+ "] Current color: c1");
					listStudents.setBackground(c1);
				} else {
					System.out.println("["
							+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
									: LocalTime.now().getHour())
							+ ":"
							+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
									: LocalTime.now().getMinute())
							+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
									: LocalTime.now().getSecond())
							+ "] Current color: c2");
					listStudents.setBackground(c2);
				}

				// Increments the count
				count++;
			}
		});

		// Starts the timer
		flashTimer.start();

		// Tells the program that the timer is running.
		this.isFlashingTimerRunning = true;

		// Debug message
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] Started flashing the students list");
	}

	/**
	 * Stops the flashing of the students list, and sets the background to white.
	 */
	public void stopFlashing() {
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] Flashing timer running: " + isFlashingTimerRunning);

		// If the timer isn't running then don't stop it.
		if (!isFlashingTimerRunning)
			return;

		// Stops the timer
		flashTimer.stop();

		// Sets the background color
		listStudents.setBackground(Color.WHITE);

		// Tells the program that the timer isn't running.
		this.isFlashingTimerRunning = false;

		// Debug message
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] Stopped flashing the students list");
	}

	/**
	 * A getter for the students
	 */
	public ArrayList<Student> getStudents() {
		return this.students;
	}

	/**
	 * A getter for lcf
	 *
	 * @return the instance of the LinkedCoursesFrame
	 */
	public LinkCoursesFrame getLinkedCoursesFrame() {
		return this.lcf;
	}

	/**
	 * A setter for the linked courses
	 *
	 * @param linkedCourses the linked courses
	 */
	public void setLinkedCourses(ArrayList<Course> linkedCourses) {
		this.linkedCourses = linkedCourses;
	}

	/**
	 * A getter for the linked courses, clones the list.
	 *
	 * @return a clone of the linked courses
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Course> getLinkedCourses() {
		return (ArrayList<Course>) this.linkedCourses.clone();
	}

	/**
	 * @param isCourseLinkerOpened if {@code true} if the course linker
	 */
	public void setCourseLinkerOpened(boolean isCourseLinkerOpened) {
		this.isCourseLinkerOpened = isCourseLinkerOpened;
	}

	/**
	 * A getter for isCourseLinkerOpened.
	 *
	 * @return if course linker is oppened or not
	 */
	public boolean isCourseLinkerOpened() {
		return this.isCourseLinkerOpened;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		txfName.setBackground(Color.white);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		txfName.setBackground(Color.white);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}
}
