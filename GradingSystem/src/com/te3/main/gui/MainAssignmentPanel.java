package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Criteria;
import com.te3.main.objects.Task;

/**
 * The class used to handle the assignments, <br>
 * with GUI components.
 */
public class MainAssignmentPanel extends JPanel {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 3651008258198927088L;

	/** If the JList is flashing or not */
	private boolean isJListFlashing = false;

	/** Flashes the JList */
	private Timer flashTimer = null;

	// ArrayLists
	ArrayList<Criteria> courseCriteria;
	ArrayList<Criteria> addedCriteria;

	// Layouts
	BorderLayout layout = new BorderLayout();

	// Instances
	MainFrame mf;

	Task t;

	// JLabels
	JLabel lblCriteria = new JLabel("Välj Kunskapskrav: ");
	JLabel lblSpacer = new JLabel(" ");

	// JLists
	JList<Criteria> listCourseCriteria = new JList<Criteria>();
	JList<Criteria> listAddedCriteria = new JList<Criteria>();

	// JScrollPanes
	JScrollPane scrCourseCriteria = new JScrollPane(listCourseCriteria);
	JScrollPane scrAddedCriteria = new JScrollPane(listAddedCriteria);

	/**
	 * For adding
	 *
	 * @param mf the instance of the MainFrame
	 */
	public MainAssignmentPanel(MainFrame mf) {
		// Stores the instances
		this.mf = mf;

		this.courseCriteria = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(0).getCourses().get(mf.getCurrentlySelectedCourseIndex()).getCourseCriteria();
		this.addedCriteria = new ArrayList<Criteria>();

		// Refreshes the lists
		this.refreshLists();

		// Sets the properties
		this.setProperties();

		// Adds the components
		this.addComponents();
	}

	/**
	 * For editing
	 */
	public MainAssignmentPanel(MainFrame mf, Task t) {
		// Stores the instances
		this.mf = mf;
		this.t = t;

		this.addedCriteria = t.getCriteria();
		this.courseCriteria = this.getRestOfCourseCriteria();

		// Refreshes the lists
		this.refreshLists();

		// Sets the properties
		this.setProperties();

		// Adds the components
		this.addComponents();
	}

	/**
	 * Sets the properties
	 */
	private void setProperties() {
		// Sets the size
		listAddedCriteria.setPreferredSize(new Dimension(250, 100));
		listCourseCriteria.setPreferredSize(new Dimension(250, 100));

		// Sets layout
		this.setLayout(layout);

		// Adds selection listeners
		listAddedCriteria.getSelectionModel().addListSelectionListener((e) -> {
			updateCriteriaLists(false, listAddedCriteria.getSelectedIndex());
		});

		listCourseCriteria.getSelectionModel().addListSelectionListener((e) -> {
			updateCriteriaLists(true, listCourseCriteria.getSelectedIndex());
		});
	}

	/**
	 * Adds the components to the frame
	 */
	private void addComponents() {
		this.add(lblCriteria, BorderLayout.PAGE_START);
		this.add(scrCourseCriteria, BorderLayout.LINE_START);
		this.add(scrAddedCriteria, BorderLayout.LINE_END);
		this.add(lblSpacer, BorderLayout.PAGE_END);
	}

	/**
	 * Updates the criteria lists in the GUI
	 *
	 * @param isCourseCriteria <br>
	 *                         if {@code true} -> the selection happened in the
	 *                         course criteria list <br>
	 *                         if {@code false} -> the selection happened in the
	 *                         added criteria list
	 * @param index            the selected index in the list, must be positive,
	 *                         else it returns.
	 */
	private void updateCriteriaLists(boolean isCourseCriteria, int index) {
		// If -1 the no index is selected then it returns
		if (index == -1) {
			return;
		}

		// Updates the list based on the users input
		if (isCourseCriteria) {
			this.updateLists(this.courseCriteria, this.addedCriteria, this.courseCriteria.get(index));
		} else {
			this.updateLists(this.addedCriteria, this.courseCriteria, this.addedCriteria.get(index));
		}

		// Refreshes the lists
		this.refreshLists();
	}

	/**
	 * Moves an element, e, form arrList1 to arrList2.
	 *
	 * @param arrList1 the first array list, the one that's is the original host.
	 * @param arrList2 the second array list, the one that gets the element.
	 * @param e        the element that's being moved.
	 */
	private <E> void updateLists(ArrayList<E> arrList1, ArrayList<E> arrList2, E e) {
		// adds the element
		arrList2.add(e);

		// removes it
		int index = arrList1.indexOf(e);
		arrList1.remove(index);
	}

	/**
	 * Refreshes the list with the current content of the ArrayLists.
	 */
	private void refreshLists() {
		// Stores the data and refreshes the JLists
		Criteria[] addedCriteria = new Criteria[this.addedCriteria.size()];
		Criteria[] courseCriteria = new Criteria[this.courseCriteria.size()];

		listAddedCriteria.setListData(this.addedCriteria.toArray(addedCriteria));
		listCourseCriteria.setListData(this.courseCriteria.toArray(courseCriteria));

		// Stops flashing the list
		this.stopFlashingJList();
	}

	/**
	 * Starts flashing the JList.
	 *
	 * @param c1       The first colour
	 * @param c2       The second colour
	 * @param interval The interval in seconds.
	 */
	public void startFlashingJList(Color c1, Color c2, double interval) {
		// Returns if the JList is already flashing.
		if (this.isJListFlashing) {
			// Sends a debug message to the console
			System.out.println("["
					+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour())
					+ ":"
					+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
							: LocalTime.now().getMinute())
					+ ":"
					+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
							: LocalTime.now().getSecond())
					+ "] MainAssignmentPanel: JList: listAddedCriteria already flashing");

			// returns
			return;
		}

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
					listAddedCriteria.setBackground(c1);
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
					listAddedCriteria.setBackground(c2);
				}

				// Increments the count
				count++;
			}
		});

		// Starts the timer
		flashTimer.start();

		// Tells the program that the timer is running.
		this.isJListFlashing = true;

		// Debug message
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] MinAssignmentPanel: JList: listAddedCriteria, has started flashing");
	}

	/**
	 * Stops flashing the JList.
	 */
	public void stopFlashingJList() {
		// If the timer isn't running then don't stop it.
		if (!this.isJListFlashing) {
			// Sends Debug message
			System.out.println("["
					+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour())
					+ ":"
					+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
							: LocalTime.now().getMinute())
					+ ":"
					+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
							: LocalTime.now().getSecond())
					+ "] MainAssignmentPanel: JList: listAddedCriteria, isn't flashing");

			// Returns
			return;
		}

		// Stops the timer
		flashTimer.stop();

		// Sets the background color
		listAddedCriteria.setBackground(Color.WHITE);

		// Tells the program that the timer isn't running.
		this.isJListFlashing = false;

		// Debug message
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] MinAssignmentPanel: JList: listAddedCriteria, has stopped flashing");

	}

	/**
	 * Adds the not added criteria to the course.
	 *
	 * @return <br>
	 *         if all the course criteria is added to the assignment, then it
	 *         returns an empty ArrayList <br>
	 *         else it will return the rest of the course criteria.
	 */
	private ArrayList<Criteria> getRestOfCourseCriteria() {
		// Stores the criteria
		ArrayList<Criteria> allCourseCriteria = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex())
				.getStudents().get(0).getCourses().get(mf.getCurrentlySelectedCourseIndex()).getCourseCriteria();

		// The rest of the criteria
		ArrayList<Criteria> rest = new ArrayList<Criteria>();

		// It's empty if they are of equal size.
		if (allCourseCriteria.size() == addedCriteria.size()) {
			return new ArrayList<Criteria>();
		}

		// Adds the criteria.
		for (Criteria c : allCourseCriteria) {
			if (!(addedCriteria.contains(c))) {
				rest.add(c);
			}
		}

		// returns it
		return rest;
	}

	/**
	 * A getter for the added criteria.
	 *
	 * @return the added criteria. (Cloned)
	 */
	public ArrayList<Criteria> getAddedCriteria() {
		//The list with the new criteria
		var list = new ArrayList<Criteria>();

		//Loops through and adds the criteria
		for (Criteria c : this.addedCriteria) {
			try {
				list.add(new Criteria(c.getName()));
			} catch (IllegalNameException e) {
				e.printStackTrace();
			}
		}

		//Returns the list
		return list;
	}
}
