package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Task;

/**
 * The class that holds the GUI for the assignments
 */
public class AssignmentFrame extends JFrame {

	/** Generated */
	private static final long serialVersionUID = 1797348437376328717L;

	/** The help text */
	private String helpInfo = "PLACEHOLDER";

	//JLabels
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");

	//Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

	//Panels
	JPanel panel = new JPanel();

	NamePanel np = new NamePanel();
	MainAssignmentPanel map;
	AddControlPanel acp = new AddControlPanel();
	EditControlPanel ecp = new EditControlPanel();

	//Instances
	MainFrame mf;

	Task t;

	/**
	 * For adding
	 * 
	 * @param mf the instance of the MainFrae
	 */
	public AssignmentFrame(MainFrame mf) {
		//Sets the title with the super constructor
		super("Lägg till en uppgift");

		//Stores the instance
		this.mf = mf;

		//Sets the properties and adds the componentss
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
		//Sets the title with the super constructor
		super("Ändra en uppgift");

		//Stores the instances
		this.mf = mf;
		this.t = t;

		//Sets the properties and adds the components
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
		//Sets the layout and size
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));

		//Sets the name
		np.setLastInput((isAddMode) ? "" : t.getName());

		/*
		 * If the frame is in add modes it adds a task,
		 * else it will edit the chosen task.
		 * */
		if (isAddMode) {
			//Adds action listeners
			acp.getBtnAdd().addActionListener((e) -> {
				try {
					//adds the assignment
					this.addAssignment();
				} catch (IllegalNameException ex) {
					//Sends error message to the user
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalInputException ex) {
					//Sends error message to the user
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				}
			});

			acp.getBtnCancel().addActionListener((e) -> {
				//Closes the frame
				this.dispose();
			});

			acp.getBtnHelp().addActionListener((e) -> {
				//Shows a new help frame.
				new HelpFrame("Lägg till en kurs", "<html><p>" + helpInfo + "</p></html>").setVisible(true);
			});
		} else {
			//Adds action listeners
			ecp.getBtnUpdate().addActionListener((e) -> {
				try {
					//updates the task
					this.updateAssignment();
				} catch (IllegalNameException ex) {
					//Sends error message to the user
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalInputException ex) {
					//Sends error message to the user
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				}
			});

			ecp.getBtnCancel().addActionListener((e) -> {
				//Closes the frame
				this.dispose();
			});

			ecp.getBtnHelp().addActionListener((e) -> {
				//Shows a new help frame.
				new HelpFrame("Ändra en kurs", "<html><p>" + helpInfo + "</p></html>").setVisible(true);
			});
		}
	}

	/**
	 * Adds the components
	 *
	 * @param isAddMode <br>
	 *                  if {@code true} then the frame is in add mode <br>
	 *                  if {@code false} then the frame is in edit mode
	 */
	private void addComponents(boolean isAddMode) {
		//Chooses a constructor based on if it's in add mode or not.
		map = (isAddMode) ? new MainAssignmentPanel(mf) : new MainAssignmentPanel(mf, t);

		//Sets the layout
		panel.setLayout(pLayout);

		//adds the components to the panel
		panel.add(np, BorderLayout.PAGE_START);
		panel.add(map, BorderLayout.CENTER);
		//Chooses a component based on if it's in add mode or not.
		panel.add((isAddMode) ? acp : ecp, BorderLayout.PAGE_END);

		//Adds the components to the frame
		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Adds a new assignment
	 *
	 * @throws IllegalInputException if there are no criteria being added.
	 * @throws IllegalNameException if the inputted name isn't allowed.
	 */
	private void addAssignment() throws IllegalNameException, IllegalInputException {
		//If it's to short of a name and if it's just whitespaces.
		if (np.getLastInput().trim().length() < 3) {
			throw new IllegalNameException("För kort namn!");
		}

		//If there are no criteria in the assingment
		if (map.getAddedCriteria().size() == 0) {
			throw new IllegalInputException("Du måste ha minst ett kunskapskrav i uppgiften.");
		}

		//Loops through the students and adds the task to the student
		for (var i = 0; i < mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.size(); i++) {
			mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(i).getCourses()
					.get(mf.getCurrentlySelectedCourseIndex())
					.addCourseTask(new Task(np.getLastInput(), map.getAddedCriteria()));
		}

		//Saves the progress
		mf.saveData(mf.getSaveFilePath());
		mf.updateGradePanel();
	}

	/**
	 * Updates the assignment
	 *
	 * @throws IllegalInputException if there are no criteria being added.
	 * @throws IllegalNameException if the inputted name isn't allowed.
	 */
	@SuppressWarnings("unlikely-arg-type")
	private void updateAssignment() throws IllegalNameException, IllegalInputException {
		//If it's to short of a name and if it's just whitespaces.
		if (np.getLastInput().length() < 3) {
			throw new IllegalNameException("För kort namn!");
		}

		//If there are no criteria in the assingment
		if (map.getAddedCriteria().size() == 0) {
			throw new IllegalInputException("Du måste ha minst ett kunskapskrav i uppgiften.");
		}

		// Removes the task and adds the updated task
		for (int i = 0; i < mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.size(); i++) {
			var newList = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(i)
					.getCourses();
			newList.remove(this.t);
			mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(i)
					.setCourses(newList);

			mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(i).getCourses()
					.get(mf.getCurrentlySelectedCourseIndex())
					.addCourseTask(new Task(np.getLastInput(), map.getAddedCriteria()));
		}

		//Saves the task
		mf.saveData(mf.getSaveFilePath());
		mf.updateGradePanel();
	}

}
