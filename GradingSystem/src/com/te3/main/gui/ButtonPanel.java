package com.te3.main.gui;

import java.awt.FlowLayout;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.te3.main.enums.State;
import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.objects.Course;
import com.te3.main.objects.Criteria;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Student;
import com.te3.main.objects.Task;

/**
 * The panel with the buttons on the bottom of the GUI.
 */
public class ButtonPanel extends JPanel {

	/** Default */
	private static final long serialVersionUID = 1L;

	/** The title in the help part of the GUI */
	private String helpTitle = "Huvudvy (Placeholder)";

	/** The help info och the GUI */
	private String helpInfo = "<html>Detta är en mening. <br> Detta är en till mening. <br> Detta är en placeholder!</html>";

	//Threads
	Thread threadSaveToFile;
	Thread threadSaveAs;

	//MainFrames
	MainFrame mf;

	//Layouts
	FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);

	//Buttons
	JButton btnSave = new JButton("Spara");
	JButton btnSaveAs = new JButton("Spara som");
	JButton btnSaveToFile = new JButton("Spara till fil"); // Ska vi döpa om denna?
	JButton btnPrint = new JButton("Skriv ut");
	JButton btnSettings = new JButton("Inställningar");
	JButton btnHelp = new JButton("?");

	//Labels
	JLabel lblSpacer1 = new JLabel("  ");

	//TextAreas
	JTextArea printView = new JTextArea();

	/**
	 * @param mf the instance of the MainFrame
	 */
	public ButtonPanel(MainFrame mf) {
		//Stores the instance
		this.mf = mf;

		//Adds the components and sets the properties of the components
		this.setProperties();
		this.addComponents();
	}

	/**
	 * @param st the state of the data shown.
	 */
	private void saveToFile(State st) {
		//Gets the student.
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		//Creates a new FileSystemFrame
		FileSystemFrame fsf = new FileSystemFrame(s.getName(), "txt");

		//Sets the frame visible
		fsf.setVisible(true);

		//Creates the thread
		threadSaveToFile = new Thread(() -> saveToFileThread(fsf, st));

		//starts the thread
		threadSaveToFile.start();
	}

	/**
	 * Acts on the output from the FileSystemFrame
	 * 
	 * @param fsf the FileSystemFrame
	 * @param st  the state of the information in the program
	 */
	@SuppressWarnings("static-access")
	private void saveToFileThread(FileSystemFrame fsf, State st) {
		//keeps the window open while the user is doing it's things.
		while (fsf.getExitCode() == -1) {
			try {
				threadSaveToFile.sleep(450);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//If the user chooses to save to file.
		if (fsf.getExitCode() == 0) {
			//Formats the text
			formatTextFile(st);

			//Gets the text
			String text = printView.getText();

			//Tries the save it to a file then closes the frame
			try {
				BufferedWriter w = new BufferedWriter(new FileWriter(fsf.getFilePath(), true));
				w.append(text);
				w.close();
				JOptionPane.showMessageDialog(mf, "Du har sparat till en fil!", "Sparat",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally {
				fsf.close();
			}
		} else {
			fsf.close();
		}
	}

	/**
	 * Prints the file.
	 * 
	 * @param st the State of the panel
	 */
	private void print(State st) {
		//Formats the text
		formatTextPrint(st);

		//Tries to print
		try {
			printView.print();
		} catch (PrinterException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Used to format the text for printing.
	 * 
	 * @param st the current state of the panel
	 */
	private void formatTextPrint(State st) {
		//Gets the necessary info
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		SchoolClass sc = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex());

		Task t = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex())
				.getCourseTasks().get(mf.getCurrentlySelectedAssignmentIndex());

		Course co = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex());

		ArrayList<Criteria> c;

		//resets the settings of the print view
		printView.setText("");
		printView.setTabSize(15);

		//adds all the info
		printView.append("Namn:\t" + s.getName());
		printView.append("\nKlass:\t" + sc.getName());
		printView.append("\nKurs:\t" + co.getName());

		//Adds info and stores the criteria based on state of the window.
		if (st.equals(State.SINGLE_STUDENT_ASSIGNMENT)) {
			printView.append("\nUppgift:\t" + t.getName());
			c = t.getCriteria();
		} else {
			c = co.getCourseCriteria();
		}

		//Adds more text
		printView.append("\n\nKunskapskrav:\n");
		for (int i = 0; i < c.size(); i++) {
			Criteria cr = c.get(i);
			printView.append(cr.getName() + ":\t" + cr.getGrade().toString() + "\n");
		}
	}

	/**
	 * Formats the text for the file.
	 * 
	 * @param st the state of informations shown
	 */
	private void formatTextFile(State st) {
		//Gets the necessary info
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		SchoolClass sc = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex());

		Task t = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex())
				.getCourseTasks().get(mf.getCurrentlySelectedAssignmentIndex());

		Course co = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex());

		//Creates list for criteria
		ArrayList<Criteria> c;

		//Setup
		printView.setText("");

		//adds text
		printView.append(String.format("%-20s%s", "Namn:", s.getName()) + "\n");
		printView.append(String.format("%-20s%s", "Klass:", sc.getName()) + "\n");
		printView.append(String.format("%-20s%s", "Kurs:", co.getName()) + "\n");

		//Adds text and sets up criteria based on the state of the panel
		if (st.equals(State.SINGLE_STUDENT_ASSIGNMENT)) {
			printView.append(String.format("%-20s%s", "Uppgift:", t.getName()) + "\n");
			c = t.getCriteria();
		} else {
			c = co.getCourseCriteria();
		}

		printView.append("\nKunskapskrav:\n");

		for (int i = 0; i < c.size(); i++) {
			Criteria cr = c.get(i);
			printView.append(String.format("%-20s%-1s", cr.getName() + ":", cr.getGrade().toString()) + "\n");
		}

	}

	/**
	 * Changes and saves the file at a new location.
	 * 
	 * @param path the new path that should be saved at
	 */
	public void saveAs(String path) {
		//Creates a new FileSystemFrame
		FileSystemFrame fsf = new FileSystemFrame("saves", "xml");

		//Shows the frame
		fsf.setVisible(true);

		//Creates a thread
		threadSaveAs = new Thread(() -> saveAsThread(fsf));

		//Starts the thread
		threadSaveAs.start();
	}

	/**
	 * Acts on the FileSystemFrames output.
	 * 
	 * @param fsf the FileSystemFrame
	 */
	@SuppressWarnings("static-access")
	private void saveAsThread(FileSystemFrame fsf) {
		//Waits for the user to do the input
		while (fsf.getExitCode() == -1) {
			try {
				threadSaveAs.sleep(450);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//Saves the data and set's the path
		if (fsf.getExitCode() == 0) {
			try {
				mf.setSaveFilePath(fsf.getFilePath());
				mf.saveData(mf.getSaveFilePath());
				JOptionPane.showMessageDialog(mf, "Du har sparat", "Sparat", JOptionPane.INFORMATION_MESSAGE);
			} catch (IllegalInputException e) {
				e.printStackTrace();
			}
		}

		//Closes the FileSystemFrame
		fsf.close();
	}

	private void settings() {
		//Shows the settings frame
		new SettingsFrame(mf).setVisible(true);
	}

	/**
	 * Adds Components.
	 */
	private void addComponents() {
		this.add(lblSpacer1);
		this.add(btnHelp);
		this.add(btnSettings);
		this.add(btnPrint);
		this.add(btnSaveToFile);
		this.add(btnSaveAs);
		this.add(btnSave);
	}

	/**
	 * Sets the properties.
	 */
	private void setProperties() {
		this.setLayout(layout);

		//Adds action listeners
		btnHelp.addActionListener((e) -> new HelpFrame(helpTitle, helpInfo, 500).setVisible(true));

		btnSave.addActionListener((e) -> {
			mf.saveData(mf.getSaveFilePath());
			JOptionPane.showMessageDialog(mf, "Du har sparat!", "Sparat", JOptionPane.INFORMATION_MESSAGE);
		});

		btnPrint.addActionListener((e) -> print(mf.getGradePanel().getState()));

		btnSaveToFile.addActionListener((e) -> saveToFile(mf.getGradePanel().getState()));

		btnSaveAs.addActionListener((e) -> saveAs(mf.getSaveFilePath()));

		btnSettings.addActionListener((e) -> settings());
	}
}
