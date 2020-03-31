package com.te3.main.gui;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import javax.swing.*;

import com.te3.main.enums.Grade;
import com.te3.main.enums.State;
import com.te3.main.objects.*;

/**
 * The panel that holds all the <br>
 * different criteria and the text at <br>
 * the left of the GUI.
 * */
public class GradesPanel extends JPanel implements KeyListener {

	/** Default */
	private static final long serialVersionUID = 1L;

	//Integers
	private int keyCount = 0;

	//State
	private State state;

	//ArrayLists
	private ArrayList<SchoolClass> classes;
	private ArrayList<Course> courses;
	private ArrayList<Student> students;
	private ArrayList<Task> tasks;
	private ArrayList<Criteria> criteria;

	//Panels
	JPanel panel = new JPanel();
	JPanel pComment = new JPanel();
	JPanel pCommentButtons = new JPanel();
	JPanel panelCriteria = new JPanel();
	JPanel panelInfo = new JPanel();
	JPanel panelInfo2 = new JPanel();

	GraphPanel gp;

	//Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pInfoLayout = new BorderLayout();
	BorderLayout pCommentLayout = new BorderLayout();

	BoxLayout pInfoLayout2 = new BoxLayout(panelInfo2, BoxLayout.Y_AXIS);
	BoxLayout pCriteriaLayout;

	FlowLayout pLayout = new FlowLayout(FlowLayout.LEFT);
	FlowLayout pCommentButtonsLayout = new FlowLayout(FlowLayout.CENTER);

	//TextAreas
	JTextArea txaComment = new JTextArea();

	//ScrollPanes
	JScrollPane scroll = new JScrollPane(panel);
	JScrollPane scrollComment = new JScrollPane(txaComment);

	//Labels
	JLabel lblName = new JLabel();
	JLabel lblAssignment = new JLabel();
	JLabel lblGrades = new JLabel();
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("    ");
	JLabel lblSpacer3 = new JLabel("    ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer5 = new JLabel("    ");
	JLabel lblSpacer6 = new JLabel("    ");
	JLabel lblSpacer7 = new JLabel(" ");

	//Buttons
	JButton btnClearComment = new JButton("Rensa kommentar");
	JButton btnSaveComment = new JButton("Spara");

	//Instances
	MainFrame mf;

	/**
	 * @param mf the instance of the MainFrame.
	 */
	public GradesPanel(MainFrame mf) {
		//Sets the needed values
		this.state = State.CLASS_COURSE_STUDENT_TASK; //Get from cbPanel
		this.mf = mf;

		//Sets the layout
		this.setLayout(layout);

		//Updates the gui and information
		update(state);

		//Some properties
		scroll.setBorder(BorderFactory.createEmptyBorder());

		//Fixes stuff for yoda.
		yoda(mf.shouldShowBabyYoda());
	}

	/**
	 * Sets properties
	 */
	private void setProperties() {
		//As long as tasks aren't null.
		if (tasks != null) {
			//Stores the task:
			Task t = tasks.get(mf.getCurrentlySelectedAssignmentIndex());

			//Clears textBox
			txaComment.setText("");

			//Sets the comment
			txaComment.setText(t.getComment());

			//Adds actionListeners
			btnClearComment.addActionListener(e -> txaComment.setText(""));
			btnSaveComment.addActionListener(e -> saveComment(t, true));

			//Adds key listener
			txaComment.addKeyListener(this);

			//Sets the properties of the pCommentButtons panel
			pCommentButtons.setLayout(pCommentButtonsLayout);
			pCommentButtons.add(btnSaveComment);
			pCommentButtons.add(btnClearComment);

			//Sets the properties of the pComment panel
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
	 * @param t the current task
	 * @param isSaveBtnPressed if the save is requested by the user.
	 */
	private void saveComment(Task t, boolean isSaveBtnPressed) {
		t.setComment(txaComment.getText());
		mf.saveData(mf.getSaveFilePath());

		if (isSaveBtnPressed)
			JOptionPane.showMessageDialog(this, "Du har sparat din kommentar", "Sparat!", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method for handeling the background
	 *
	 * @parma shouldShowBabyYoda if {@code true} -> setup for baby yoda else set up for ordinary mode.
	 * */
	public void yoda(boolean shouldShowBabyYoda) {
		//Reacts to the parameter
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
	 * @param s the state of the program
	 */
	public void update(State s) {
		//Adds components
		this.addComponents(s);

		//Grabs the needed information.
		this.grabInfo();

		//Sets some properties
		this.setProperties();

		//Calculates which grades should be shown, based on the state.
		if (s.equals(State.CLASS_COURSE_STUDENT_TASK)) {
			displayCriteria(criteria);
		} else if (s.equals(State.CLASS_COURSE_STUDENT)) {
			//An ArrayList with the highest grade in each criteria.
			ArrayList<Criteria> displayedGrades = new ArrayList<Criteria>();

			//Loops through all the tasks.
			for (int i = 0; i < tasks.size(); i++) {
				//Loops through all the criteria in each task.
				for (var j = 0; j < tasks.get(i).getCriteria().size(); j++) {
					//Stores the criteria
					Criteria c = tasks.get(i).getCriteria().get(j);

					//Stores the index in the list
					int index = displayedGrades.indexOf(c);

					//If it's in the list, displayed grades
					if (index != -1) {
						//If it's higher
						if (!(displayedGrades.get(index).compare(c))) {
							//Removes the lower grade
							displayedGrades.remove(index);

							//Adds the new one
							displayedGrades.add(c);

							//Updates the gui of the criteria.
							displayedGrades.get(displayedGrades.indexOf(c)).updateGUI();
						}
					} else {
						//Adds it
						displayedGrades.add(c);
					}
				}
			}

			//Displays the criteria
			displayCriteria(displayedGrades);
		}

		// Updates the frame with the new components
		mf.revalidate();
		mf.repaint();
	}

	/**
	 * Grabs the needed information from the MainFrame's data object.
	 */
	private void grabInfo() {
		//Grabs the info if it needs it, else sets it to null to prevent a NullPointerException
		if (this.state.equals(State.CLASS_COURSE_STUDENT_TASK)) {
			classes = mf.getMainData().getClasses();
			students = classes.get(mf.getCurrentlySelectedClassIndex()).getStudents();
			courses = students.get(0).getCourses();
			tasks = courses.get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks();
			criteria = tasks.get(mf.getCurrentlySelectedAssignmentIndex()).getCriteria();
		}else if (this.state.equals(State.CLASS_COURSE_STUDENT)) {
			classes = mf.getMainData().getClasses();
			students = classes.get(mf.getCurrentlySelectedClassIndex()).getStudents();
			courses = students.get(0).getCourses();
			tasks = courses.get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks();
		} else {
			classes = null;
			students = null;
			courses = null;
			tasks = null;
			criteria = null;
		}
	}

	/**
	 * Adds the components, after removing them
	 *
	 * @param s The current state of the components
	 */
	private void addComponents(State s) {
		//Removes the components
		for (Component c : this.getComponents())
			this.remove(c);

		//Adds the components, when they are needed.
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
	 * @param s  the current student
	 * @param t  the current task
	 * @param criteria the current criteria
	 * @param st the current state of the panel.
	 */
	private void updateSidebar(Student s, Task t, ArrayList<Criteria> criteria, State st) {
		//The count of grades
		short f = 0, e = 0, c = 0, a = 0;

		//Adds one in each variable for each grade value
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

		//Loops through and removes old components
		for (Component comp : panelInfo2.getComponents()) {
			panelInfo2.remove(comp);
		}

		//Creates the graph panel
		gp = new GraphPanel(f, e, c, a);

		//Sets the text
		lblName.setText(s.getName());
		lblGrades.setText(countGrades(criteria));
		lblAssignment.setText(t.getName());

		//Sets the font
		lblName.setFont(new Font(lblName.getFont().getName(), Font.BOLD, 20));
		lblGrades.setFont(new Font(lblName.getFont().getName(), Font.PLAIN, 20));
		lblAssignment.setFont(new Font(lblAssignment.getFont().getName(), Font.PLAIN, 20));

		//Sets the layout
		panelInfo.setLayout(pInfoLayout);
		panelInfo2.setLayout(pInfoLayout2);

		//Adds the components, based on state
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

		//Adds everything.
		panelInfo.add(lblSpacer2, BorderLayout.LINE_START);
		panelInfo.add(panelInfo2, BorderLayout.CENTER);
		panelInfo.add(lblSpacer3, BorderLayout.LINE_END);
		panelInfo.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Counts the grades and returns a string representation.
	 * 
	 * @param criteria the current criteria
	 * @return a string representation of the number of grades at each level.
	 */
	private String countGrades(ArrayList<Criteria> criteria) {
		//The count of grades
		short f = 0, e = 0, c = 0, a = 0;

		//Adds one in each variable for each grade value

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

		//Returns a formatted string.
		return "F: " + f + ", E: " + e + ", C: " + c + ", A: " + a;
	}

	/**
	 * Updates the GUI with all the buttons for each criteria.
	 * 
	 * @param criteria the criteria to be displayed
	 */
	private void displayCriteria(ArrayList<Criteria> criteria) {
		// Removes all components
		for (Component c : panelCriteria.getComponents())
			panelCriteria.remove(c);

		//Sets the layout
		pCriteriaLayout = new BoxLayout(panelCriteria, BoxLayout.Y_AXIS);
		panelCriteria.setLayout(pCriteriaLayout);

		//Loops through the criteria.
		for (var i = 0; i < criteria.size(); i++) {
			// Adds the action listeners
			JButton[] gradeBtns = criteria.get(i).getGradeBtns();

			//Removes old action listeners
			for (var j = 0; j < gradeBtns.length; j++) {
				//If it's null print message to the console.
				try {
					gradeBtns[j].removeActionListener(gradeBtns[j].getActionListeners()[0]);
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Skipped gradebtn -> IndexOutOfBounds: no action listeners attached");
				}
			}

			// Since it needs to be final or effectively final in lambda
			final int i2 = i;

			//Adds the action listeners
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

		//Sets the layout
		panel.setLayout(pLayout);

		//Adds the components
		panel.add(panelCriteria);

		//Updates the GUI for every criteria.
		criteria.forEach(Criteria::updateGUI);

		//Updates the sidebar.
		updateSidebar(students.get(mf.getCurrentlySelectedStudentIndex()), tasks.get(mf.getCurrentlySelectedAssignmentIndex()), criteria, this.state);

		//Updates the panel.
		this.revalidate();
		this.repaint();
	}

	/**
	 * Called when a criteria button is pressed.
	 *
	 * @param g the new grade
	 * @param c the new criteria
	 * */
	private void btnClicked(Grade g, Criteria c) {
		//Sets the grade
		c.setGrade(g);

		//Updates the GUI
		c.updateGUI();

		//Repaints the graph
		gp.repaint();

		//Updates the state
		this.update(this.state);

		//Prints debug message
		System.out.println(c.toString());
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
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (++keyCount % 50 == 0)
			saveComment(tasks.get(mf.getCurrentlySelectedAssignmentIndex()), false);
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
