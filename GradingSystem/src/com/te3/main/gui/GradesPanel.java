package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.te3.main.enums.Grade;
import com.te3.main.enums.State;
import com.te3.main.objects.Course;
import com.te3.main.objects.Criteria;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Student;
import com.te3.main.objects.Task;

/**
 * The panel that holds all the <br>
 * different criteria and the text at <br>
 * the left of the GUI.
 */
public class GradesPanel extends JPanel implements KeyListener {

	/** Default */
	private static final long serialVersionUID = 1L;

	/** If {@code true} it's in list mode, else it's in normal mode */
	private boolean isListMode;

	/** If {@code true} it will open add Course GUI when done with update */
	private boolean shouldAddCourse = false;

	/** If {@code true} it will save the comment, else it won't. */
	private boolean shouldSaveComment = false;

	// Integers
	private int keyCount = 0;

	// State
	private State state;

	// ArrayLists
	private ArrayList<SchoolClass> classes;
	private ArrayList<Course> courses;
	private ArrayList<Student> students;
	private ArrayList<Task> tasks;
	private ArrayList<Criteria> criteria;

	// Panels
	JPanel panel = new JPanel();
	JPanel pComment = new JPanel();
	JPanel pCommentButtons = new JPanel();
	JPanel panelCriteria = new JPanel();
	JPanel panelInfo = new JPanel();
	JPanel panelInfo2 = new JPanel();

	GraphPanel gp;

	// Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pInfoLayout = new BorderLayout();
	BorderLayout pCommentLayout = new BorderLayout();

	BoxLayout pInfoLayout2 = new BoxLayout(panelInfo2, BoxLayout.Y_AXIS);
	BoxLayout pCriteriaLayout;

	FlowLayout pLayout = new FlowLayout(FlowLayout.LEFT);
	FlowLayout pCommentButtonsLayout = new FlowLayout(FlowLayout.CENTER);

	// TextAreas
	JTextArea txaComment = new JTextArea();

	// ScrollPanes
	JScrollPane scroll = new JScrollPane(panel);
	JScrollPane scrollComment = new JScrollPane(txaComment);

	// Labels
	JLabel lblName = new JLabel();
	JLabel lblAssignment = new JLabel();
	JLabel lblGrades = new JLabel();
	JLabel lblNoTaskSelected = new JLabel();
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("    ");
	JLabel lblSpacer3 = new JLabel("    ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer5 = new JLabel("    ");
	JLabel lblSpacer6 = new JLabel("    ");
	JLabel lblSpacer7 = new JLabel(" ");

	// Buttons
	JButton btnClearComment = new JButton("Rensa kommentar");
	JButton btnSaveComment = new JButton("Spara");

	// Instances
	MainFrame mf;
	Task t;
	Student s;

	/**
	 * For normal mode
	 *
	 * @param mf         the instance of the MainFrame.
	 * @param isListMode if is accessed by the main list -> {@code true}, else
	 *                   {@code false}
	 */
	public GradesPanel(MainFrame mf, boolean isListMode) {
		// Sets the needed values
		this.state = State.NOTHING_SELECTED;
		this.mf = mf;
		this.isListMode = isListMode;

		// Sets the layout
		this.setLayout(layout);

		// Updates the gui and information
		update(state, this.isListMode);

		// Some properties
		scroll.setBorder(BorderFactory.createEmptyBorder());

		// Fixes stuff for yoda.
		yoda(mf.shouldShowBabyYoda());
	}

	/**
	 * For the ListMode
	 *
	 * @param mf         the instance of the MainFrame.
	 * @param isListMode if is accessed by the main list -> {@code true}, else
	 *                   {@code false}
	 * @param tasks      the tasks to be displayed
	 * @param t          the current task ({@code null} if the state of the
	 *                   SearchFrame = {@code CLASS_COURSE_STUDENT})
	 * @param s          the current student
	 * @param criteria   the current task ({@code null} if the state of the
	 *                   SearchFrame = {@code CLASS_COURSE_STUDENT})
	 */
	public GradesPanel(MainFrame mf, boolean isListMode, ArrayList<Task> tasks, Task t, Student s,
			ArrayList<Criteria> criteria) {
		// Sets the needed values
		this.state = State.NOTHING_SELECTED;
		this.mf = mf;
		this.isListMode = isListMode;
		this.tasks = tasks;
		this.t = t;
		this.s = s;
		this.criteria = criteria;

		// Sets the layout
		this.setLayout(layout);

		// Updates the gui and information
		update(state, this.isListMode);

		// Some properties
		scroll.setBorder(BorderFactory.createEmptyBorder());

		// Fixes stuff for yoda.
		yoda(mf.shouldShowBabyYoda());
	}

	/**
	 * Sets properties
	 */
	private void setProperties() {
		// As long as tasks aren't null.
		if (tasks != null) {
			// Gets the task.
			Task t = tasks.get(mf.getCurrentlySelectedAssignmentIndex());

			// Clears textBox
			txaComment.setText("");

			// Sets the comment
			txaComment.setText(t.getComment());

			// Adds actionListeners
			btnClearComment.addActionListener(e -> {
				txaComment.setText("");
				this.shouldSaveComment = false;
				btnSaveComment.setEnabled(false);
			});

			// To make it final
			final Task t2 = t;

			btnSaveComment.addActionListener(e -> {
				if (shouldSaveComment) {
					saveComment(t2, true);
					this.shouldSaveComment = false;
					btnSaveComment.setEnabled(false);
				}
			});

			// Disables the save button
			btnSaveComment.setEnabled(false);

			// Adds key listener
			txaComment.addKeyListener(this);

			// Makes it so it will create a new line when it runs out of space.
			txaComment.setLineWrap(true);
			txaComment.setWrapStyleWord(true);

			// Sets the properties of the pCommentButtons panel
			pCommentButtons.setLayout(pCommentButtonsLayout);
			pCommentButtons.add(btnSaveComment);
			pCommentButtons.add(btnClearComment);

			// Sets the properties of the pComment panel
			pComment.setLayout(pCommentLayout);
			pComment.add(lblSpacer5, BorderLayout.LINE_START);
			pComment.add(scrollComment, BorderLayout.CENTER);
			pComment.add(lblSpacer6, BorderLayout.LINE_END);
			pComment.add(pCommentButtons, BorderLayout.PAGE_END);
		}
	}

	/**
	 * Saves the comment
	 *
	 * @param t                the current task
	 * @param isSaveBtnPressed if the save is requested by the user.
	 */
	private void saveComment(Task t, boolean isSaveBtnPressed) {
		t.setComment(txaComment.getText());

		if (isSaveBtnPressed)
			JOptionPane.showMessageDialog(this, "Du har sparat din kommentar", "Sparat!",
					JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method for handeling the background
	 *
	 * @parma shouldShowBabyYoda if {@code true} -> setup for baby yoda else set up
	 *        for ordinary mode.
	 */
	public void yoda(boolean shouldShowBabyYoda) {
		// Reacts to the parameter
		if (shouldShowBabyYoda) {
			panelInfo.setOpaque(false);
			panel.setOpaque(false);
			scroll.setOpaque(false);
		} else {
			panelInfo.setOpaque(true);
			panel.setOpaque(true);
			scroll.setOpaque(true);
		}
	}

	/**
	 * Updates the gui based on the state <br>
	 * and the information.
	 *
	 * @param s          the state of the program
	 * @param isListMode if is accessed by the main list -> {@code true}, else
	 *                   {@code false}
	 */
	public void update(State s, boolean isListMode) {
		// If list mode or not
		if (!isListMode) {
			// Grabs the needed information and updates the state.
			this.state = this.grabInfo(s);

			// Stores the state
			s = this.state;

			// Handles the new state
			mf.cbPanel.handleNewState();
		} else {
			this.state = s;
		}

		// Adds components
		this.addComponents(s);

		// Sets some properties
		this.setProperties();

		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] GradesPanel: Current State: " + this.state.toString());
		// Calculates which grades should be shown, based on the state.
		if (s.equals(State.CLASS_COURSE_STUDENT_TASK)) {
			displayCriteria(criteria, s);
		} else if (s.equals(State.CLASS_COURSE_STUDENT)) {
			// An ArrayList with the highest grade in each criteria.
			ArrayList<Criteria> displayedGrades = new ArrayList<Criteria>();

			// Loops through all the tasks.
			for (int i = 0; i < tasks.size(); i++) {
				// Loops through all the criteria in each task.
				for (var j = 0; j < tasks.get(i).getCriteria().size(); j++) {
					// Stores the criteria
					Criteria c = tasks.get(i).getCriteria().get(j);

					// Stores the index in the list
					int index = displayedGrades.indexOf(c);

					// If it's in the list, displayed grades
					if (index != -1) {
						// If it's higher
						if (!(displayedGrades.get(index).compare(c))) {
							// Removes the lower grade
							displayedGrades.remove(index);

							// Adds the new one
							displayedGrades.add(c);

							// Updates the gui of the criteria.
							displayedGrades.get(displayedGrades.indexOf(c)).updateGUI();
						}
					} else {
						// Adds it
						displayedGrades.add(c);
					}
				}
			}

			// Displays the criteria
			displayCriteria(displayedGrades, s);
		}

		// Updates the frame with the new components
		mf.revalidate();
		mf.repaint();

		// Checks if it should add a new course or not
		if (this.shouldAddCourse) {
			// Opens the add GUI for the course
			mf.openAddEditGUI(Task.class, true);

			// Tells the program to not add a course.
			this.shouldAddCourse = false;
		}
	}

	/**
	 * Grabs the needed information from the MainFrame's data object.
	 *
	 * @param s The state of the GUI
	 * @return the correct state for continues operation
	 */
	private State grabInfo(State s) {
		// Grabs the info if it needs it, else sets it to null to prevent a
		// NullPointerException
		if (s.equals(State.CLASS_COURSE_STUDENT_TASK)) {
			classes = mf.getMainData().getClasses();
			students = classes.get(mf.getCurrentlySelectedClassIndex()).getStudents();
			courses = students.get(mf.getCurrentlySelectedStudentIndex()).getCourses();
			tasks = courses.get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks();
			criteria = tasks.get(mf.getCurrentlySelectedAssignmentIndex()).getCriteria();
		} else if (s.equals(State.CLASS_COURSE_STUDENT)) {
			classes = mf.getMainData().getClasses();
			students = classes.get(mf.getCurrentlySelectedClassIndex()).getStudents();
			courses = students.get(mf.getCurrentlySelectedStudentIndex()).getCourses();
			tasks = courses.get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks();

			// To prevent IndexOutOfBoundsException with no created tasks.
			if (tasks.size() == 0) {
				// Sends dialog to the user
				int ans = JOptionPane.showConfirmDialog(this,
						"Du måste ha minst en uppgift för denna vyn; vill du skapa en?", "Inga Uppgfiter",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

				// Debug message:
				System.out.println("["
						+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
								: LocalTime.now().getHour())
						+ ":"
						+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
								: LocalTime.now().getMinute())
						+ ":"
						+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
								: LocalTime.now().getSecond())
						+ "] GradesPanel: Interrupting GUI update, no tasks created.");

				// If answer = yes, then open GUI to
				if (ans == JOptionPane.YES_OPTION) {
					// Debug message:
					System.out.println("["
							+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
									: LocalTime.now().getHour())
							+ ":"
							+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
									: LocalTime.now().getMinute())
							+ ":"
							+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
									: LocalTime.now().getSecond())
							+ "] GradesPanel: Opening task creator when done updating");

					// Tells the program to open the GUI when done
					this.shouldAddCourse = true;

					// Sets the tasks to null the tasks
					tasks = null;

					return State.CLASS_COURSE;
				}

				// Debug message:
				System.out.println("["
						+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
								: LocalTime.now().getHour())
						+ ":"
						+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
								: LocalTime.now().getMinute())
						+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
								: LocalTime.now().getSecond())
						+ "] GradesPanel: No new task created.");

				// Sets the tasks to null
				tasks = null;

				// Returns
				return State.CLASS_COURSE;
			}
		} else {
			classes = null;
			students = null;
			courses = null;
			tasks = null;
			criteria = null;
		}

		// Debug message:
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] GradesPanel: Interrupting GUI update, no tasks created.");

		// Returns the state, that was put in.
		return s;
	}

	/**
	 * Adds the components, after removing them
	 *
	 * @param s The current state of the components
	 */
	private void addComponents(State s) {
		// Removes the components
		for (Component c : this.getComponents())
			this.remove(c);

		// Adds the components, when they are needed.
		if (s.equals(State.CLASS_COURSE_STUDENT_TASK)) {
			this.add(lblSpacer1, BorderLayout.PAGE_START);
			this.add(panelInfo, BorderLayout.LINE_START);
			this.add(scroll, BorderLayout.CENTER);
			this.add(pComment, BorderLayout.LINE_END);
			this.add(lblSpacer7, BorderLayout.PAGE_END);
		} else if (s.equals(State.CLASS_COURSE_STUDENT)) {
			this.add(lblSpacer1, BorderLayout.PAGE_START);
			this.add(panelInfo, BorderLayout.LINE_START);
			this.add(scroll, BorderLayout.CENTER);
			this.add(lblSpacer5, BorderLayout.LINE_END);
		}
	}

	/**
	 * Updates the info in the top hand left corner.
	 * 
	 * @param s        the current student
	 * @param t        the current task
	 * @param criteria the current criteria
	 * @param st       the current state of the panel.
	 */
	private void updateSidebar(Student s, Task t, ArrayList<Criteria> criteria, State st) {
		// The count of grades
		short f = 0, e = 0, c = 0, a = 0;

		// Adds one in each variable for each grade value
		for (Criteria c1 : criteria) {
			switch (c1.getGrade()) {
				case F:
					f++;
					break;
				case E:
					e++;
					break;
				case C:
					c++;
					break;
				case A:
					a++;
					break;
			}
		}

		// Loops through and removes old components
		for (Component comp : panelInfo2.getComponents()) {
			panelInfo2.remove(comp);
		}

		// Creates the graph panel
		gp = new GraphPanel(f, e, c, a);

		// Sets the text
		lblName.setText(s.getName());
		lblGrades.setText(countGrades(criteria));

		// Since t, can be null in the case of list mode
		if (t != null)
			lblAssignment.setText(t.getName());

		// Sets the font
		lblName.setFont(new Font(lblName.getFont().getName(), Font.BOLD, 20));
		lblGrades.setFont(new Font(lblName.getFont().getName(), Font.PLAIN, 20));
		lblAssignment.setFont(new Font(lblAssignment.getFont().getName(), Font.PLAIN, 20));

		// Sets the layout
		panelInfo.setLayout(pInfoLayout);
		panelInfo2.setLayout(pInfoLayout2);

		// Adds the components, based on state
		if (st.equals(State.CLASS_COURSE_STUDENT_TASK)) {
			panelInfo2.add(lblName);
			panelInfo2.add(lblAssignment);
			panelInfo2.add(lblGrades);
			panelInfo2.add(gp);
		} else if (st.equals(State.CLASS_COURSE_STUDENT)) {
			panelInfo2.add(lblName);
			panelInfo2.add(lblGrades);
			panelInfo2.add(gp);
		}

		// Adds everything.
		panelInfo.add(lblSpacer2, BorderLayout.LINE_START);
		panelInfo.add(panelInfo2, BorderLayout.CENTER);
		panelInfo.add(lblSpacer3, BorderLayout.LINE_END);
		panelInfo.add(lblSpacer4, BorderLayout.PAGE_END);

		// Adds the components
		this.addComponents(st);
	}

	/**
	 * Counts the grades and returns a string representation.
	 * 
	 * @param criteria the current criteria
	 * @return a string representation of the number of grades at each level.
	 */
	private String countGrades(ArrayList<Criteria> criteria) {
		// The count of grades
		short f = 0, e = 0, c = 0, a = 0;

		// Adds one in each variable for each grade value

		for (Criteria c1 : criteria) {
			switch (c1.getGrade()) {
				case F:
					f++;
					break;
				case E:
					e++;
					break;
				case C:
					c++;
					break;
				case A:
					a++;
					break;
			}
		}

		// Returns a formatted string.
		return "F: " + f + ", E: " + e + ", C: " + c + ", A: " + a;
	}

	/**
	 * Updates the GUI with all the buttons for each criteria.
	 * 
	 * @param criteria the criteria to be displayed
	 * @param s        the state of the panel.
	 */
	private void displayCriteria(ArrayList<Criteria> criteria, State s) {
		// Removes all components
		for (Component c : panelCriteria.getComponents())
			panelCriteria.remove(c);

		// Sets the layout
		pCriteriaLayout = new BoxLayout(panelCriteria, BoxLayout.Y_AXIS);
		panelCriteria.setLayout(pCriteriaLayout);

		/*
		 * If the state is CLASS_COURSE_STUDENT_TASK it will show all the GUI buttons
		 * and you can update the different grades in the different criteria, but if
		 * it's the view with no assignment selected (s = CLASS_COURSE_STUDENT) then it
		 * will just list the grades with out being able to change the grades.
		 */
		if (s.equals(State.CLASS_COURSE_STUDENT_TASK)) {
			// Loops through the criteria.
			for (var i = 0; i < criteria.size(); i++) {
				// Adds the action listeners
				JButton[] gradeBtns = criteria.get(i).getGradeBtns();

				// Removes old action listeners
				for (var j = 0; j < gradeBtns.length; j++) {
					// If it's null print message to the console.
					try {
						gradeBtns[j].removeActionListener(gradeBtns[j].getActionListeners()[0]);
					} catch (IndexOutOfBoundsException e) {
						System.out.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
								+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
										: LocalTime.now().getSecond())
								+ "] Skipped gradebtn -> IndexOutOfBounds: no action listeners attached");
					}
				}

				// Since it needs to be final or effectively final in lambda
				final int i2 = i;

				// Adds the action listeners
				gradeBtns[0].addActionListener((e) -> {
					btnClicked(Grade.F, criteria.get(i2));
				});

				gradeBtns[1].addActionListener((e) -> {
					btnClicked(Grade.E, criteria.get(i2));
				});

				gradeBtns[2].addActionListener((e) -> {
					btnClicked(Grade.C, criteria.get(i2));
				});

				gradeBtns[3].addActionListener((e) -> {
					btnClicked(Grade.A, criteria.get(i2));
				});

				// Adds the panel
				panelCriteria.add(criteria.get(i).getPanelCriteria());
			}
		} else {
			// Adds the label
			panelCriteria.add(lblNoTaskSelected);

			// Sets the font
			lblNoTaskSelected.setFont(new Font("DIALOG", Font.BOLD, 30));

			// Clears the text of the label
			// And setup for the HTML formatting
			lblNoTaskSelected.setText("<html><table>");

			// Loops through the list and appends the information.
			for (int i = 0; i < criteria.size(); i++) {
				// Defines a table row
				String thisGrade = "<tr>";

				// Adds a new item to the table, which is the name of the criteria.
				// &emsp; adds some spacing (4 whitespaces)
				thisGrade += "<td>&emsp;&emsp;" + criteria.get(i).toString() + ":&emsp;</td>";

				/*
				 * Adds the grade in the specific criteria to the string, as a new item in the
				 * table.
				 *
				 * <td> defines a new table item
				 * 
				 * <span> makes a part of the text which should have special properties.
				 * 
				 * <font color=aColor> sets the text color of this segement of code.
				 *
				 * ((criteria.get(i).getGrade().ordinal() == 0) ? "red" : "lime") decides which
				 * color it is. If the ordinal of the grade is = 0 (Grade.F) has got that
				 * ordinal, then it will append red to the string else it will append lime to
				 * the string and therefore the letter will be lime when the grade is above F
				 * else it will be red.
				 *
				 */
				thisGrade += "<td><span><font color=" + ((criteria.get(i).getGrade().ordinal() == 0) ? "red" : "lime")
						+ ">" + criteria.get(i).getGrade().toString() + "</font></span></td>";

				/*
				 * Adds some spacing between the lines.
				 * 
				 * <br> is a line break
				 */
				thisGrade += "<br><br></tr>";

				/*
				 * It appends the information for the current grade to the string, by getting
				 * the old text and setting the new text to the old text + the new text.
				 */
				lblNoTaskSelected.setText(lblNoTaskSelected.getText() + thisGrade);
			}

			// Closes the HTML-tags
			lblNoTaskSelected.setText(lblNoTaskSelected.getText() + "</table></html>");
		}

		// Sets the layout
		panel.setLayout(pLayout);

		// Adds the components
		panel.add(panelCriteria);

		// Updates the GUI for every criteria.
		criteria.forEach(Criteria::updateGUI);

		if (isListMode) {
			// Updates the sidebar
			updateSidebar(this.s, this.t, criteria, this.state);
		} else {
			// Updates the sidebar.
			updateSidebar(students.get(mf.getCurrentlySelectedStudentIndex()),
					tasks.get(mf.getCurrentlySelectedAssignmentIndex()), criteria, this.state);
		}

		// Updates the panel.
		this.revalidate();
		this.repaint();
	}

	/**
	 * Called when a criteria button is pressed.
	 *
	 * @param g the new grade
	 * @param c the new criteria
	 */
	private void btnClicked(Grade g, Criteria c) {
		// Sets the grade
		c.setGrade(g);

		// Updates the GUI
		c.updateGUI();

		// Repaints the graph
		gp.repaint();

		// Updates the state
		this.update(this.state, this.isListMode);

		// Prints debug message
		System.out.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] Criteria updated: " + c.toString() + ", New Grade: " + c.getGrade().toString());
	}

	/**
	 * A getter for the state.
	 *
	 * @return the current state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the new state.
	 * 
	 * @param state the new state
	 */
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Checks for autosave
		if (++keyCount % 50 == 0)
			saveComment(tasks.get(mf.getCurrentlySelectedAssignmentIndex()), false);

		// If an edit has occurred then it will be allowed to save.
		this.shouldSaveComment = true;

		btnSaveComment.setEnabled(true);
	}
}
