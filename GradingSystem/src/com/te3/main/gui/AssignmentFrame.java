package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.te3.main.enums.State;
import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Course;
import com.te3.main.objects.Task;

/**
 * The class that holds the GUI for the assignments
 */
public class AssignmentFrame extends JFrame implements WindowListener {

	/** Generated */
	private static final long serialVersionUID = 1797348437376328717L;

	/** The help text */
	private String helpInfo = "Skriv in namnet på kursen i rutan: <b>Namn</b> <br>"
			+ "Klicka på de kunskapskrav du vill ha i uppgiften. <br>"
			+ "De kunskapskraven till vänster är de som är med i uppgiften. <br>"
			+ "För att avbryta tryck på knappen: <b>Avbryt</b><br>"
			+ "För att lägga till/Uppdatera klicka på knappen <b>Lägg Till/Uppdatera</b> <br>"
			+ "Tryck på knappen: <b>Tabort</b> för att tabort uppgiften.";

	// JLabels
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");

	// Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

	// Panels
	JPanel panel = new JPanel();

	NamePanel np = new NamePanel();
	MainAssignmentPanel map;
	AddControlPanel acp = new AddControlPanel();
	EditControlPanel ecp = new EditControlPanel();

	// Instances
	MainFrame mf;

	Task t;

	/**
	 * For adding
	 * 
	 * @param mf the instance of the MainFrae
	 */
	public AssignmentFrame(MainFrame mf) {
		// Sets the title with the super constructor
		super("Lägg till en uppgift");

		// Stores the instance
		this.mf = mf;

		// Sets the properties and adds the components
		this.setProperties(true);
		this.addComponents(true);
	}

	/**
	 * For updating
	 *
	 * @param mf the instance of the MainFrame.
	 * @param t  the task that's being updated.
	 */
	public AssignmentFrame(MainFrame mf, Task t) {
		// Sets the title with the super constructor
		super("Ändra en uppgift");

		// Stores the instances
		this.mf = mf;

		// Stores a clone of the object
		try {
			this.t = new Task(t.getName(), t.getCriteria());
		} catch (IllegalNameException e) {
			e.printStackTrace();
		}

		// Sets the properties and adds the components
		this.setProperties(false);
		this.addComponents(false);
	}

	/**
	 * Sets the properties
	 *
	 * @param isAddMode <br>
	 *                  if {@code true} then the frame is in add mode <br>
	 *                  if {@code false} then the frame is in edit mode
	 */
	private void setProperties(boolean isAddMode) {
		// Sets the layout and size
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));

		// Sets the name
		np.setLastInput((isAddMode) ? "" : t.getName());

		/*
		 * If the frame is in add modes it adds a task, else it will edit the chosen
		 * task.
		 */
		if (isAddMode) {
			// Adds action listeners
			acp.getBtnAdd().addActionListener((e) -> {
				try {
					// adds the assignment
					this.addAssignment();
				} catch (IllegalNameException ex) {
					// Sends error message to the user
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalInputException ex) {
					// Sends error message to the user
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				}
			});

			acp.getBtnCancel().addActionListener((e) -> {
				// Closes the frame
				this.dispose();
			});

			acp.getBtnHelp().addActionListener((e) -> {
				// Shows a new help frame.
				new HelpFrame("Lägg till en uppgift", "<html><p>" + helpInfo + "</p></html>", 300, 475)
						.setVisible(true);
			});
		} else {
			// Adds action listeners
			ecp.getBtnUpdate().addActionListener((e) -> {
				try {
					// updates the task
					this.updateAssignment();
				} catch (IllegalNameException ex) {
					// Sends error message to the user
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalInputException ex) {
					// Sends error message to the user
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				}
			});

			ecp.getBtnCancel().addActionListener((e) -> {
				// Closes the frame
				this.dispose();
			});

			ecp.getBtnDelete().addActionListener(e -> this.deleteTask());

			ecp.getBtnHelp().addActionListener((e) -> {
				// Shows a new help frame.
				new HelpFrame("Ändra en uppgift", "<html><p>" + helpInfo + "</p></html>", 350, 475).setVisible(true);
			});
		}

		// Adds a window listener
		this.addWindowListener(this);
	}

	/**
	 * Adds the components
	 *
	 * @param isAddMode <br>
	 *                  if {@code true} then the frame is in add mode <br>
	 *                  if {@code false} then the frame is in edit mode
	 */
	private void addComponents(boolean isAddMode) {
		// Chooses a constructor based on if it's in add mode or not.
		map = (isAddMode) ? new MainAssignmentPanel(mf) : new MainAssignmentPanel(mf, t);

		// Sets the layout
		panel.setLayout(pLayout);

		// adds the components to the panel
		panel.add(np, BorderLayout.PAGE_START);
		panel.add(map, BorderLayout.CENTER);
		// Chooses a component based on if it's in add mode or not.
		panel.add((isAddMode) ? acp : ecp, BorderLayout.PAGE_END);

		// Adds the components to the frame
		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Deletes the task that's being updated
	 */
	private void deleteTask() {
		// Confirmation
		int ans = JOptionPane.showConfirmDialog(this, "Är du säker på att du vill tabort: " + t.getName() + "?",
				"Tabort", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		// If no return
		if (ans == JOptionPane.NO_OPTION)
			return;

		// Loops through the students and removes the task from the student
		for (int i = 0; i < mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.size(); i++) {
			mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(i).getCourses()
					.get(mf.getCurrentlySelectedCourseIndex()).removeTask(t);
		}

		// Message to the user
		JOptionPane.showMessageDialog(this, "Du har nu tagit bort uppgiften: " + t.getName(), "Tabort",
				JOptionPane.INFORMATION_MESSAGE);

		// Stores the list of courses.
		var listCourses = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(0)
				.getCourses().get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks();

		/*
		 * If no tasks it will set the state to CLASS_COURSE, else it will set the state
		 * to CLASS_COURSE_STUDENT
		 */
		mf.updateGradePanel((listCourses.size() != 0) ? State.CLASS_COURSE_STUDENT : State.CLASS_COURSE);

		// Updates CBPanel
		mf.cbPanel.refreshData(mf.getMainData());

		// Closes the frame
		this.dispose();
	}

	/**
	 * Adds a new assignment
	 *
	 * @throws IllegalInputException if there are no criteria being added.
	 * @throws IllegalNameException  if the inputted name isn't allowed.
	 */
	private void addAssignment() throws IllegalNameException, IllegalInputException {
		// If it's to short of a name and if it's just whitespaces.
		if (np.getLastInput().trim().length() < 3) {
			// Sets the color to pink
			np.setTextFieldColour(Color.PINK);

			// Throws an exception with a message
			throw new IllegalNameException("För kort namn!");
		}

		// If there are no criteria in the assingment
		if (map.getAddedCriteria().size() == 0) {
			// Starts flashing the List
			map.startFlashingJList(Color.PINK, Color.WHITE, .5D);

			// Throws an exception with a message:
			throw new IllegalInputException("Du måste ha minst ett kunskapskrav i uppgiften.");
		}

		// Loops through the students and adds the task to the student
		for (var i = 0; i < mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.size(); i++) {
			mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(i).getCourses()
					.get(mf.getCurrentlySelectedCourseIndex())
					.addTask(new Task(np.getLastInput(), map.getAddedCriteria()));
		}

		// Saves the progress
		mf.saveData(mf.getSaveFilePath());

		// Updates GradePanel
		mf.updateGradePanel();

		// Updates CBPanel
		mf.cbPanel.refreshData(mf.getMainData());

		// Closes the window
		this.dispose();
	}

	/**
	 * Updates the assignment
	 *
	 * @throws IllegalInputException if there are no criteria being added.
	 * @throws IllegalNameException  if the inputted name isn't allowed.
	 */
	private void updateAssignment() throws IllegalNameException, IllegalInputException {
		// If it's to short of a name and if it's just whitespaces.
		if (np.getLastInput().length() < 3) {
			// Turns the TextField red
			np.setTextFieldColour(Color.PINK);

			// Throws an exception with a message
			throw new IllegalNameException("För kort namn!");
		}

		// If there are no criteria in the assingment
		if (map.getAddedCriteria().size() == 0) {
			// Starts flashing the list
			map.startFlashingJList(Color.PINK, Color.WHITE, .5D);

			// Throws an exception with a message
			throw new IllegalInputException("Du måste ha minst ett kunskapskrav i uppgiften.");
		}

		// Removes the task and adds the updated task
		for (int i = 0; i < mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.size(); i++) {
			// Stores the course instance
			Course c = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(i)
					.getCourses().get(mf.getCurrentlySelectedCourseIndex());

			// Removes the old task to prevent duplicates
			c.removeTask(this.t);

			// Adds the updated task.
			c.addTask(new Task(np.getLastInput(), map.getAddedCriteria()));
		}

		// Saves the task
		mf.saveData(mf.getSaveFilePath());

		// Updates GradePanel
		mf.updateGradePanel();

		// Updates CBPanel
		mf.cbPanel.refreshData(mf.getMainData());

		// Closes the window
		this.dispose();
	}

	/**
	 * Invoked the first time a window is made visible.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowOpened(WindowEvent e) {
	}

	/**
	 * Invoked when the user attempts to close the window from the window's system
	 * menu.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowClosing(WindowEvent e) {
	}

	/**
	 * Invoked when a window has been closed as the result of calling dispose on the
	 * window.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		// Stops flashing the JList
		map.stopFlashingJList();
	}

	/**
	 * Invoked when a window is changed from a normal to a minimized state. For many
	 * platforms, a minimized window is displayed as the icon specified in the
	 * window's iconImage property.
	 *
	 * @param e the event to be processed
	 * @see Frame#setIconImage
	 */
	@Override
	public void windowIconified(WindowEvent e) {
	}

	/**
	 * Invoked when a window is changed from a minimized to a normal state.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	/**
	 * Invoked when the Window is set to be the active Window. Only a Frame or a
	 * Dialog can be the active Window. The native windowing system may denote the
	 * active Window or its children with special decorations, such as a highlighted
	 * title bar. The active Window is always either the focused Window, or the
	 * first Frame or Dialog that is an owner of the focused Window.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowActivated(WindowEvent e) {
	}

	/**
	 * Invoked when a Window is no longer the active Window. Only a Frame or a
	 * Dialog can be the active Window. The native windowing system may denote the
	 * active Window or its children with special decorations, such as a highlighted
	 * title bar. The active Window is always either the focused Window, or the
	 * first Frame or Dialog that is an owner of the focused Window.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
