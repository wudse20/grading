package com.te3.main.gui;

import java.awt.*;

import java.util.ArrayList;

import javax.swing.*;

import com.te3.main.enums.Grade;
import com.te3.main.enums.State;
import com.te3.main.objects.Criteria;
import com.te3.main.objects.Student;
import com.te3.main.objects.Task;

/**
 * The panel that holds all the <br>
 * different criteria and the text at <br>
 * the left of the GUI.
 *
 * @Author Anton Skorup
 * */
public class GradesPanel extends JPanel {

	/** Default */
	private static final long serialVersionUID = 1L;

	private State state;
	private ArrayList<Criteria> criteria;
	private ArrayList<Task> tasks;

	JPanel panel = new JPanel();
	JPanel panelCriteria = new JPanel();
	JPanel panelInfo = new JPanel();
	JPanel panelInfo2 = new JPanel();

	BorderLayout layout = new BorderLayout();
	BorderLayout pInfoLayout = new BorderLayout();

	BoxLayout pInfoLayout2 = new BoxLayout(panelInfo2, BoxLayout.Y_AXIS);
	BoxLayout pCriteriaLayout;

	FlowLayout pLayout = new FlowLayout(FlowLayout.LEFT);

	JScrollPane scroll = new JScrollPane(panel);

	JLabel lblName = new JLabel();
	JLabel lblAssignment = new JLabel();
	JLabel lblGrades = new JLabel();
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("    ");
	JLabel lblSpacer3 = new JLabel("    ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer5 = new JLabel("    ");

	MainFrame mf;

	/**
	 * @param mf the instance of the MainFrame.
	 */
	public GradesPanel(MainFrame mf) {
		this.state = State.SINGLE_STUDENT_ASSIGNMENT;
		this.mf = mf;
		this.setLayout(layout);

		criteria = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(0)
				.getCourses().get(mf.getCurrentlySelectedCourseIndex()).getCourseCriteria();

		tasks = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(0).getCourses()
				.get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks();

		updateGUI(state);

		scroll.setBorder(BorderFactory.createEmptyBorder());

		yoda(mf.shouldShowBabyYoda());

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(panelInfo, BorderLayout.LINE_START);
		this.add(scroll, BorderLayout.CENTER);
		this.add(lblSpacer5, BorderLayout.LINE_END);
	}

	/**
	 * Method for handeling the background
	 *
	 * @parma shouldShowBabyYoda if {@code true} -> setup for baby yoda else set up for ordinary mode.
	 * */
	public void yoda(boolean shouldShowBabyYoda) {
		panel.setBackground(new JPanel().getBackground());
		
		if (mf.shouldShowBabyYoda()) {
			panelInfo.setOpaque(false);;
			panel.setOpaque(false);
			scroll.setOpaque(false);
		} else {
			panelInfo.setOpaque(true);
			panel.setOpaque(true);
			scroll.setOpaque(true);
		}
	}

	/**
	 * Updates the gui based on the state.
	 * 
	 * @param s the state of the program
	 */
	public void updateGUI(State s) {
		if (s.equals(State.SINGLE_STUDENT_ASSIGNMENT)) {
			displayCriteria(criteria);
		} else {
			ArrayList<Criteria> displayedGrades = new ArrayList<Criteria>();

			for (int i = 0; i < tasks.size(); i++) {
				for (var j = 0; j < tasks.get(i).getCriteria().size(); j++) {
					Criteria c = tasks.get(i).getCriteria().get(j);
					int index = displayedGrades.indexOf(c);

					if (index != -1) {
						if (!(displayedGrades.get(index).compare(c))) {
							displayedGrades.remove(index);
							displayedGrades.add(c);
							displayedGrades.get(displayedGrades.indexOf(c)).updateGUI();
						}
					} else {
						displayedGrades.add(c);
					}
				}
			}

			displayCriteria(displayedGrades);
		}

		// Updates the frame with the new components
		mf.revalidate();
		mf.repaint();
	}

	/**
	 * Updates the info in the top hand left corner.
	 * 
	 * @param s  the current student
	 * @param t  the current task
	 * @param al the current criteria
	 * @param st the current state of the panel.
	 */
	private void updateInfo(Student s, Task t, ArrayList<Criteria> al, State st) {
		lblName.setText(s.getName());
		lblGrades.setText(countGrades(al));
		lblAssignment.setText(t.getName());

		lblName.setFont(new Font(lblName.getFont().getName(), Font.BOLD, 20));
		lblGrades.setFont(new Font(lblName.getFont().getName(), Font.PLAIN, 20));
		lblAssignment.setFont(new Font(lblAssignment.getFont().getName(), Font.PLAIN, 20));

		panelInfo.setLayout(pInfoLayout);
		panelInfo2.setLayout(pInfoLayout2);

		if (st.equals(State.SINGLE_STUDENT_ASSIGNMENT)) {
			panelInfo2.add(lblName);
			panelInfo2.add(lblAssignment);
			panelInfo2.add(lblGrades);
		} else if (st.equals(State.SINGLE_STUDENT_GENERALIZED)) {
			panelInfo2.add(lblName);
			panelInfo2.add(lblGrades);
		}

		panelInfo.add(lblSpacer2, BorderLayout.LINE_START);
		panelInfo.add(panelInfo2, BorderLayout.CENTER);
		panelInfo.add(lblSpacer3, BorderLayout.LINE_END);
		panelInfo.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Counts the grades.
	 * 
	 * @param al the current criteria
	 * @return a string representation of the number of grades at each level.
	 */
	private String countGrades(ArrayList<Criteria> al) {
		int f = 0, e = 0, c = 0, a = 0;

		for (Criteria c1 : al) {
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

		pCriteriaLayout = new BoxLayout(panelCriteria, BoxLayout.Y_AXIS);
		panelCriteria.setLayout(pCriteriaLayout);

		for (var i = 0; i < criteria.size(); i++) {
			// Adds the action listeners
			JButton[] gradeBtns = criteria.get(i).getGradeBtns();

			//Removes old action listeners
			for (var j = 0; j < gradeBtns.length; j++) {
				try {
					gradeBtns[j].removeActionListener(gradeBtns[j].getActionListeners()[0]);
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Skipped gradebtn -> IndexOutOfBounds no action listeners attached");
				}
			}

			// Since it needs to be final or effectively final in lambda
			final int i2 = i;

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

		panel.setLayout(pLayout);
		panel.add(panelCriteria);

		criteria.forEach(Criteria::updateGUI);

		updateInfo(
				mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
						.get(mf.getCurrentlySelectedStudentIndex()),
				tasks.get(mf.getCurrentlySelectedAssignmentIndex()), criteria, this.state);

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
		c.setGrade(g);
		c.updateGUI();
		this.updateGUI(this.state);
		System.out.println(c.toString());
	}

	/**
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
}
