package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.te3.main.enums.State;
import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Course;
import com.te3.main.objects.SchoolClass;

/**
 * The class that holds the Frame for managing courses.
 */
public class CourseFrame extends JFrame implements WindowListener {

	/** Default */
	private static final long serialVersionUID = 1L;

	/** The help info of this frame */
	private String helpInfo = "I <b>namn</b>-rutan skall namnet på kursen skrivas in. <br>"
			+ "Namnet måste vara minst 3 tecken långt. <br><br>"
			+ "Klicka på en klass i rutan <b>klasser</b> så kommer den att <br>"
			+ "kopplas till kursen. För att tabort en klass från kursen <br>"
			+ "klicka på den i rutan <b>tillagda klasser</b>. <br><br>"
			+ "För att lägga till ett kunskapskrav till kursen så skriv in <br>"
			+ "in den i rutan: <b>Nytt kunskapskrav</b>. Detta måste vara <br>"
			+ "minst tre tecken långt. Skulle du vilja ta bort <br>"
			+ "ett kunskapskrav behöver du bara klicka på det<br>" + "i <b>kunskapskravs</b>-rutan.<br>"
			+ "Tryck på knappen: <b>Tabort</b> för att tabort kursen.";

	/** The original SchoolClasses before editing */
	private ArrayList<SchoolClass> orgSchoolClasses;

	// Instances
	Course c;

	MainFrame mf;

	// Panels
	NamePanel np = new NamePanel();

	MainCoursePanel mcp;

	AddControlPanel acp = new AddControlPanel();

	EditControlPanel ecp = new EditControlPanel();

	JPanel panel = new JPanel();

	// Containers
	Container cp = this.getContentPane();

	// Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

	// Labels
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("      ");
	JLabel lblSpacer3 = new JLabel("      ");
	JLabel lblSpacer4 = new JLabel(" ");

	/**
	 * For adding.
	 * 
	 * @param mf The instance of the MainFrame.
	 */
	public CourseFrame(MainFrame mf) {
		// Sets some properties
		super("Lägg till en kurs");
		this.mf = mf;
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(this);

		// Creates the MainCoursePanel
		mcp = new MainCoursePanel(mf);

		// Adds action listeners
		acp.getBtnAdd().addActionListener((e) -> newCourse());
		acp.getBtnCancel().addActionListener((e) -> dispose());

		acp.getBtnHelp().addActionListener((
				e) -> new HelpFrame("Lägg till en kurs", "<html><p>" + helpInfo + "</p></html>", 500).setVisible(true));

		// Adds the components
		panel.setLayout(pLayout);
		panel.add(np, BorderLayout.PAGE_START);
		panel.add(mcp, BorderLayout.CENTER);
		panel.add(acp, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * For editing.
	 * 
	 * @param mf the instance of the MainFrame
	 * @param c  the course that's being updated.
	 */
	public CourseFrame(MainFrame mf, Course c) {
		// Sets some properties
		super("Uppdatera en kurs");
		this.mf = mf;
		this.c = c;
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Adds a window listener
		this.addWindowListener(this);

		// Panel setup
		mcp = new MainCoursePanel(mf, c);
		np.setLastInput(c.getName());

		// Gets a clone of the original added SchoolClasses.
		this.orgSchoolClasses = (ArrayList<SchoolClass>) mcp.getAddedClasses().clone();

		// Adds the action listener
		ecp.getBtnUpdate().addActionListener((e) -> this.updateCourse());
		ecp.getBtnCancel().addActionListener((e) -> this.dispose());
		ecp.getBtnDelete().addActionListener(e -> this.deleteCourse());
		ecp.getBtnHelp().addActionListener(
				e -> new HelpFrame("Uppdatera en kurs", "<html><p>" + helpInfo + "</p></html>", 500).setVisible(true));

		// Adds components
		panel.setLayout(pLayout);
		panel.add(np, BorderLayout.PAGE_START);
		panel.add(mcp, BorderLayout.CENTER);
		panel.add(ecp, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Removes a Course
	 */
	private void deleteCourse() {
		// User confirmation
		int ans = JOptionPane.showConfirmDialog(this, "Är du säker på att du vill tabort: " + c.getName() + "?",
				"Tabort", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		// Returns if no
		if (ans == JOptionPane.NO_OPTION)
			return;

		// Removes the course from the students
		for (int i = 0; i < mf.getMainData().getClasses().size(); i++) {
			for (int j = 0; j < mf.getMainData().getClasses().get(i).getStudents().size(); j++) {
				int index = mf.getMainData().getClasses().get(i).getStudents().get(j).getCourses().indexOf(c);
				if (index != -1) {
					mf.getMainData().getClasses().get(i).getStudents().get(j).getCourses().remove(index);
				}
			}
		}

		// Updates grade panel
		mf.updateGradePanel(State.CLASS);

		// Updates CBPanel
		mf.cbPanel.refreshData(mf.getMainData());

		// Closes the frame
		this.dispose();
	}

	/**
	 * Adds a new course to the main data.
	 */
	private void newCourse() {
		// Set to true if it should return, after checking for cases that breaks the
		// code.
		boolean shouldReturn = false;

		// Some cases that will break it, so it handles them

		/*
		 * If there are no added classes, then it will start flashing the list, then it
		 * will send a message to the user and finally tell the program to return.
		 */
		if (mcp.getAddedClasses().size() == 0) {
			mcp.startFlashing(mcp.getListAddedClasses(), Color.PINK, Color.WHITE, .5D, (byte) 0);
			JOptionPane.showMessageDialog(this, "Du måste välja minst en klass!", "Fel", JOptionPane.ERROR_MESSAGE);
			shouldReturn = true;
		}

		/*
		 * If there are no added criteria, then it will start flashing the list, then it
		 * will send a message to the user and finally tell the program to return.
		 */
		if (mcp.getCriteria().size() == 0) {
			mcp.startFlashing(mcp.getListCriteria(), Color.PINK, Color.WHITE, .5D, (byte) 1);
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst ett kunskapskrav!", "Fel",
					JOptionPane.ERROR_MESSAGE);
			shouldReturn = true;
		}

		// Returns if it's supposed to return
		if (shouldReturn)
			return;

		try {
			// Adds the courses to the new classes
			ArrayList<SchoolClass> al2 = mcp.getAddedClasses();

			for (var i = 0; i < al2.size(); i++) {
				for (var j = 0; j < al2.get(i).getStudents().size(); j++) {
					Course c = new Course(np.getLastInput(), mcp.getCriteria());
					al2.get(i).getStudents().get(j).addCourse(c);
				}
			}

			// Saves the data
			mf.saveData(mf.getSaveFilePath());

			// Message
			JOptionPane.showMessageDialog(this, "Du har lagt till kursen: " + np.getLastInput(),
					"Du har lagt till en kurs", JOptionPane.INFORMATION_MESSAGE);

			// Updates the grade panel
			mf.updateGradePanel(State.CLASS_COURSE);

			// Updates CBPanel
			mf.cbPanel.refreshData(mf.getMainData());

			// Disposes the frame
			this.dispose();
		} catch (IllegalNameException e) {
			// Sets the TextBox to red
			np.setTextFieldColour(Color.PINK);

			// sends error message
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);

			// returns
			return;
		}
	}

	/**
	 * Updates a course.
	 */
	private void updateCourse() {
		// Set to true if it should return, after checking for cases that breaks the
		// code.
		boolean shouldReturn = false;

		// Control message
		int ans = JOptionPane.showConfirmDialog(this,
				"Är du säker på att du vill uppdatera kursen: " + np.getLastInput() + "?", "Är du säker?",
				JOptionPane.YES_NO_OPTION);

		if (ans == JOptionPane.NO_OPTION)
			return;

		// Checking for illegal inputs.
		if (mcp.getAddedClasses().size() == 0) {
			mcp.startFlashing(mcp.getListAddedClasses(), Color.PINK, Color.WHITE, .5D, (byte) 0);
			JOptionPane.showMessageDialog(this, "Du måste välja minst en klass!", "Fel", JOptionPane.ERROR_MESSAGE);
			shouldReturn = true;
		}

		if (mcp.getCriteria().size() == 0) {
			mcp.startFlashing(mcp.getListCriteria(), Color.PINK, Color.WHITE, .5D, (byte) 1);
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst ett kunskapskrav!", "Fel",
					JOptionPane.ERROR_MESSAGE);
			shouldReturn = true;
		}

		// Returns if it is supposed to do that.
		if (shouldReturn)
			return;

		// Loops through the list and updates the courses
		try {
			// Removes the old courses
			for (int i = 0; i < mf.getMainData().getClasses().size(); i++) {
				for (int j = 0; j < mf.getMainData().getClasses().get(i).getStudents().size(); j++) {
					int index = mf.getMainData().getClasses().get(i).getStudents().get(j).getCourses().indexOf(c);
					if (index != -1) {
						mf.getMainData().getClasses().get(i).getStudents().get(j).getCourses().remove(index);
					}
				}
			}

			// Adds the course to the correct school classes
			ArrayList<SchoolClass> allSchoolClasses = mf.getMainData().getClasses();
			ArrayList<SchoolClass> addedSchoolClasses = mcp.getAddedClasses();

			// Loops through all the school classes
			for (int i = 0; i < allSchoolClasses.size(); i++) {
				// Stores the current school class under a new name.
				SchoolClass sc = allSchoolClasses.get(i);

				// If the current school class is in the list of added school classes
				if (addedSchoolClasses.contains(sc)) {
					// Loops through the students and adds the course
					for (int j = 0; j < allSchoolClasses.get(i).getStudents().size(); j++) {
						/*
						 * If the the current school class already had the course it will keep the same
						 * grades, comment. If there is a new SchoolClass added the it will create new
						 * course tasks with everything new.
						 */
						if (orgSchoolClasses.contains(sc)) {
							// Adds the updated course
							allSchoolClasses.get(i).getStudents().get(j)
									.addCourse(new Course(np.getLastInput(), mcp.getCriteria(), c.getCourseTasks()));

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
									+ "] CourseFrame: Updated a course, no new course created in the student.");
						} else {
							// Adds the updated course with new blank tasks
							allSchoolClasses.get(i).getStudents().get(j)
									.addCourse(new Course(np.getLastInput(), mcp.getCriteria(), c.getNewCourseTask()));

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
									+ "] CourseFrame: Updated a course, a new course created in the student.");
						}
					}
				}
			}

			// Saves the data
			mf.saveData(mf.getSaveFilePath());

			// Message
			JOptionPane.showMessageDialog(this, "Du har uppdaterat kursen: " + np.getLastInput(),
					"Du har uppdaterat en kurs", JOptionPane.INFORMATION_MESSAGE);

			// Updates the grade panel.
			mf.updateGradePanel();

			// Updates CBPanel
			mf.cbPanel.refreshData(mf.getMainData());

			// Closes the frame
			this.dispose();
		} catch (IllegalNameException e) {
			// Sets the TextField to red
			np.setTextFieldColour(Color.PINK);

			// Sends error message
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);

			// returns
			return;
		}
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
		//Stops flashing
		mcp.stopFlashing(mcp.getListAddedClasses(), (byte) 0);
		mcp.stopFlashing(mcp.getListCriteria(), (byte) 1);

		//Disposes the window
		this.dispose();
	}

	/**
	 * Invoked when a window has been closed as the result of calling dispose on the
	 * window.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowClosed(WindowEvent e) {

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
