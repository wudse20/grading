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

public class AssignmentFrame extends JFrame {

	/** Generated */
	private static final long serialVersionUID = 1797348437376328717L;

	private String helpInfo = "PLACEHOLDER";

	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");

	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

	JPanel panel = new JPanel();

	NamePanel np = new NamePanel();
	MainAssignmentPanel map;
	AddControllPanel acp = new AddControllPanel();
	EditControlPanel ecp = new EditControlPanel();

	MainFrame mf;

	Task t;

	/**
	 * For adding
	 * 
	 * @param mf the instance of the MainFrae
	 */
	public AssignmentFrame(MainFrame mf) {
		super("Lägg till en uppgift");
		this.mf = mf;
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
		super("Ändra en uppgift");
		this.mf = mf;
		this.t = t;
		this.setProperties(false);
		this.addComponents(false);
	}

	/**
	 * Sets the properties
	 *
	 * @param isAddMoce <br>
	 *                  if {@code true} then the frame is in add mode <br>
	 *                  if {@code false} then the frame is in edit mode
	 */
	private void setProperties(boolean isAddMoce) {
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));

		np.setLastInput((isAddMoce) ? "" : t.getName());

		if (isAddMoce) {
			acp.getBtnAdd().addActionListener((e) -> {
				try {
					this.addAssignment();
				} catch (IllegalNameException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalInputException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				}
			});

			acp.getBtnCancel().addActionListener((e) -> {
				this.dispose();
			});

			acp.getBtnHelp().addActionListener((e) -> {
				new HelpFrame("Lägg till en kurs", "<html><p>" + helpInfo + "</p></html>").setVisible(true);
			});
		} else {
			ecp.getBtnUpdate().addActionListener((e) -> {
				try {
					this.updateAssignment();
				} catch (IllegalNameException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalInputException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				}
			});

			ecp.getBtnCancel().addActionListener((e) -> {
				this.dispose();
			});

			ecp.getBtnHelp().addActionListener((e) -> {
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
		map = (isAddMode) ? new MainAssignmentPanel(mf) : new MainAssignmentPanel(mf, t);

		panel.setLayout(pLayout);

		panel.add(np, BorderLayout.PAGE_START);
		panel.add(map, BorderLayout.CENTER);
		panel.add((isAddMode) ? acp : ecp, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Adds a new assignment
	 */
	private void addAssignment() throws IllegalNameException, IllegalInputException {
		if (np.getLastInput().length() < 3) {
			throw new IllegalNameException("För kort namn!");
		}

		if (map.getAddedCriteria().size() == 0) {
			throw new IllegalInputException("Du måste ha minst ett kunskapskrav i uppgiften.");
		}

		for (var i = 0; i < mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.size(); i++) {
			mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(i).getCourses()
					.get(mf.getCurrentlySelectedCourseIndex())
					.addCourseTask(new Task(np.getLastInput(), map.getAddedCriteria()));
		}

		mf.saveData(mf.getSaveFilePath());
	}

	@SuppressWarnings("unlikely-arg-type")
	private void updateAssignment() throws IllegalNameException, IllegalInputException {
		if (np.getLastInput().length() < 3) {
			throw new IllegalNameException("För kort namn!");
		}

		if (map.getAddedCriteria().size() == 0) {
			throw new IllegalInputException("Du måste ha minst ett kunskapskrav i uppgiften.");
		}

		// Removes the task
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

		mf.saveData(mf.getSaveFilePath());
	}

}
