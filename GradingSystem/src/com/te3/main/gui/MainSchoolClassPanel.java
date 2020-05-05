package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.te3.main.exceptions.IllegalNameException;
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

	// Instances
	SchoolClass sc;

	MainFrame mf;

	/** The timer that is responsible for the blinking JList. */
	Timer flashTimer;

	// TextFields
	JTextField txfName = new JTextField(24);

	// Buttons
	JButton btnAddName = new JButton("Lägg till");

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
		this.addComponents();
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
		this.addComponents();
	}

	/**
	 * Adds the components
	 */
	private void addComponents() {
		pInput.add(lblName);
		pInput.add(txfName);
		pInput.add(btnAddName);

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
				* */
				if (e.getKeyCode() == 10) {
					addStudents(txfName.getText());
				}
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

		//Stops the flashing
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
		//Initializes the timer
		flashTimer = new Timer((int)(interval * 1000), new ActionListener() {
			/** The count of flashes */
			private int count = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				* It switches between the colors every other time it runs. It will set c1 on even counts and c2 on odd counts.
				* */
				if (count % 2 == 0) {
					System.out.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"  + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond()) + "] Current color: c1");
					listStudents.setBackground(c1);
				} else {
					System.out.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"  + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond()) + "] Current color: c2");
					listStudents.setBackground(c2);
				}

				//Increments the count
				count++;
			}
		});

		flashTimer.start();

		System.out.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"  + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond()) + "] Started flashing the students list");
	}

	/**
	 * Stops the flashing of the students list, and sets the background to white.
	 */
	public void stopFlashing() {
		flashTimer.stop();
		listStudents.setBackground(Color.WHITE);
		System.out.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond()) + "] Stopped flashing the students list");
	}

	/**
	 * A getter for the students
	 */
	public ArrayList<Student> getStudents() {
		return this.students;
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
	public void changedUpdate(DocumentEvent e) { }
}
