package com.te3.main.gui;

import java.awt.FlowLayout;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
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
	private String helpTitle = "Betygsättning";

	/** The help text of this part of the program */
	private String helpText = "<br><b>Klasser/Grupper:</b><br>"
			+ "För att skapa en klass: välj <b>NY</b> i listan med klasser. <br>"
			+ "För att uppdatera en klass: välj <b>UPPDATERA</b> i listan med klasser. <br>"
			+ "För att välja en klass: välj den önskade klassen i listan med klasser. <br><br>" + "<b>Kurser:</b><br>"
			+ "För att skapa en kurs: välj <b>NY</b> i listan med kurser. <br>"
			+ "För att uppdatera en kurs: välj <b>UPPDATERA</b> i listan med kurser. <br>"
			+ "För att välja en kurs: välj den önskade kurser i listan med kurser. <br><br>" + "<b>Elever:</b><br>"
			+ "För att välja en elev: välj den önskade eleven i listan med elever. <br><br>" + "<b>Uppgifter:</b><br>"
			+ "För att se elevens högst uppnådda resultat i varje kriterie: välj samlad vy <br>"
			+ "För att se elevens resultat på en viss uppgift: välj den önskade uppgiften i listan. <br><br>"
			+ "<b>Kommentar:</b><br>" + "Du kan lämna en kommentar på varje uppgift. Detta gör du genom <br>"
			+ "att skriva in den i rutan till höger. <b>OBS! Den syns bara om en uppgift är vald</b><br>"
			+ "Kommentaren kommer att spara sig själv var 50:de knapp tryck, för att spara:<br>"
			+ "Tryck på spara under rutan. <br>" + "Trycker du på rensa kommentar kommer den att tömmas. <br>"
			+ "<b>DET GÅR INTE ATT FÅ OGJORT!</b><br><br>" + "<b>Spara/Spara som:</b><br>"
			+ "Spara: Sparar nuvarande framstegen <br>"
			+ "Spara som: Sparar nuvarande framstegen till en ny fil och uppdaterar standard filen som allt sparas till. <br><br>"
			+ "<b>Spara till fil:</b><br>" + "Sparar ner till en fil som man kan skicka till eleven. <br>"
			+ "<b>Detta sparar inte nuvarande framsteg!</b> <br><br>" + "<b>Skriv ut:</b><br>"
			+ "Skriver ut ett papper med alla resultat, efter hur programet är inställt. <br><br>"
			+ "<b>Register:</b><br>"
			+ "Ett register över alla elever, där man kan få en samlad bild över hur en elev ligger till i alla kurser. <br><br>"
			+ "<b>Inställningar:</b><br>" + "Några olika inställningar som man kan göra i sitt program. <br>"
			+ "Skulle det bli tråkigt att sätta betyg tryck tre gånger på <br>"
			+ "Första knappen med YODA när bakgrunden är på. <br><br>" + "<b>?</b><br>"
			+ "Överallt är detta hjälpknappen där du kan få hjälp om hur allt fungerar. <br> &#9;";

	/** If the search window's opened or not */
	private boolean isSearchWindowOpened = false;

	// Threads
	Thread threadSaveToFile;
	Thread threadSaveAs;

	// MainFrames
	MainFrame mf;

	// Layouts
	FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);

	// Buttons
	JButton btnSave = new JButton("Spara");
	JButton btnSaveAs = new JButton("Spara som");
	JButton btnSaveToFile = new JButton("Spara till fil");
	JButton btnPrint = new JButton("Skriv ut");
	JButton btnSettings = new JButton("Inställningar");
	JButton btnHelp = new JButton("?");
	JButton btnSearch = new JButton("Register");

	// Labels
	JLabel lblSpacer1 = new JLabel("  ");

	// TextAreas
	JTextArea printView = new JTextArea();

	/**
	 * @param mf the instance of the MainFrame
	 */
	public ButtonPanel(MainFrame mf) {
		// Stores the instance
		this.mf = mf;

		// Adds the components and sets the properties of the components
		this.setProperties();
		this.addComponents();
	}

	/**
	 * @param st the state of the data shown.
	 */
	private void saveToFile(State st) {
		// Gets the student.
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		// Creates a new FileSystemFrame
		FileSystemFrame fsf = new FileSystemFrame(s.getName(), "txt");

		// Sets the frame visible
		fsf.setVisible(true);

		// Creates the thread
		threadSaveToFile = new Thread(() -> saveToFileThread(fsf, st));

		// starts the thread
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
		// keeps the window open while the user is doing it's things.
		while (fsf.getExitCode() == -1) {
			try {
				threadSaveToFile.sleep(450);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// If the user chooses to save to file.
		if (fsf.getExitCode() == 0) {
			// Formats the text
			formatTextFile(st);

			// Gets the text
			String text = printView.getText();

			// Tries the save it to a file then closes the frame
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
		if (!(mf.getGradeState().equals(State.CLASS_COURSE_STUDENT_TASK)
				|| mf.getGradeState().equals(State.CLASS_COURSE_STUDENT))) {
			JOptionPane.showMessageDialog(mf, "Du kan bara skriva ut om du har en Elev/Uppgift vald!",
					"Du kan inte skriva ut!", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Formats the text
		formatTextPrint(st);

		// Tries to print
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
		// Gets the necessary info
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		SchoolClass sc = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex());

		Task t = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex())
				.getCourseTasks().get(mf.getCurrentlySelectedAssignmentIndex());

		Course co = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex());

		ArrayList<Criteria> c;

		// resets the settings of the print view
		printView.setText("");
		printView.setTabSize(15);

		// adds all the info
		printView.append("Namn:\t" + s.getName());
		printView.append("\nKlass:\t" + sc.getName());
		printView.append("\nKurs:\t" + co.getName());

		// Adds info and stores the criteria based on state of the window.
		if (!(st.equals(State.CLASS_COURSE_STUDENT))) {
			printView.append("\nUppgift:\t" + t.getName());
			c = t.getCriteria();
		} else {
			c = this.getHighestGrades();
		}

		// Adds more text
		printView.append("\n\nKunskapskrav:\n");
		for (int i = 0; i < c.size(); i++) {
			Criteria cr = c.get(i);
			printView.append(cr.getName() + ":\t" + cr.getGrade().toString() + "\n");
		}

		// Adds the comment if it exists:
		if (t.getComment().trim().length() != 0
				&& mf.getGradePanel().getState().equals(State.CLASS_COURSE_STUDENT_TASK)) {
			printView.append("\n\nKommentar:\n" + t.getComment());
		}
	}

	/**
	 * Formats the text for the file.
	 *
	 * @param st the state of information shown
	 */
	private void formatTextFile(State st) {
		// Gets the necessary info
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		SchoolClass sc = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex());

		Task t = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex())
				.getCourseTasks().get(mf.getCurrentlySelectedAssignmentIndex());

		Course co = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex());

		// Creates list for criteria
		ArrayList<Criteria> c;

		// Setup
		printView.setText("");

		// adds text
		printView.append(String.format("%-20s%s", "Namn:", s.getName()) + "\n");
		printView.append(String.format("%-20s%s", "Klass:", sc.getName()) + "\n");
		printView.append(String.format("%-20s%s", "Kurs:", co.getName()) + "\n");

		// Adds text and sets up criteria based on the state of the panel
		if (!(st.equals(State.CLASS_COURSE_STUDENT))) {
			printView.append(String.format("%-20s%s", "Uppgift:", t.getName()) + "\n");
			c = t.getCriteria();
		} else {
			c = this.getHighestGrades();
		}

		printView.append("\nKunskapskrav:\n");

		for (int i = 0; i < c.size(); i++) {
			Criteria cr = c.get(i);
			printView.append(String.format("%-20s%-1s", cr.getName() + ":", cr.getGrade().toString()) + "\n");
		}

		// Adds the comment if it exists:
		if (t.getComment().trim().length() != 0
				&& mf.getGradePanel().getState().equals(State.CLASS_COURSE_STUDENT_TASK)) {
			printView.append("\n\nKommentar:\n" + t.getComment());
		}
	}

	private ArrayList<Criteria> getHighestGrades() {
		// An ArrayList with the highest grade in each criteria.
		ArrayList<Criteria> grades = new ArrayList<Criteria>();
		var tasks = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex())
				.getCourseTasks();

		// Loops through all the tasks.
		for (int i = 0; i < tasks.size(); i++) {
			// Loops through all the criteria in each task.
			for (var j = 0; j < tasks.get(i).getCriteria().size(); j++) {
				// Stores the criteria
				Criteria c = tasks.get(i).getCriteria().get(j);

				// Stores the index in the list
				int index = grades.indexOf(c);

				// If it's in the list, displayed grades
				if (index != -1) {
					// If it's higher
					if (!(grades.get(index).compare(c))) {
						// Removes the lower grade
						grades.remove(index);

						// Adds the new one
						grades.add(c);

						// Updates the gui of the criteria.
						grades.get(grades.indexOf(c)).updateGUI();
					}
				} else {
					// Adds it
					grades.add(c);
				}
			}
		}

		// Returns
		return grades;
	}

	/**
	 * Changes and saves the file at a new location.
	 *
	 * @param path the new path that should be saved at
	 */
	public void saveAs(String path) {
		// Creates a new FileSystemFrame
		FileSystemFrame fsf = new FileSystemFrame("saves", "xml");

		// Shows the frame
		fsf.setVisible(true);

		// Creates a thread
		threadSaveAs = new Thread(() -> saveAsThread(fsf));

		// Starts the thread
		threadSaveAs.start();
	}

	/**
	 * Acts on the FileSystemFrames output.
	 *
	 * @param fsf the FileSystemFrame
	 */
	@SuppressWarnings("static-access")
	private void saveAsThread(FileSystemFrame fsf) {
		// Waits for the user to do the input
		while (fsf.getExitCode() == -1) {
			try {
				threadSaveAs.sleep(450);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Saves the data and set's the path
		if (fsf.getExitCode() == 0) {
			try {
				mf.setSaveFilePath(fsf.getFilePath());
				mf.saveData(mf.getSaveFilePath());
				JOptionPane.showMessageDialog(mf, "Du har sparat", "Sparat", JOptionPane.INFORMATION_MESSAGE);
			} catch (IllegalInputException e) {
				e.printStackTrace();
			}
		}

		// Closes the FileSystemFrame
		fsf.close();
	}

	private void settings() {
		// Shows the settings frame
		new SettingsFrame(mf).setVisible(true);
	}

	/**
	 * Adds Components.
	 */
	private void addComponents() {
		this.add(lblSpacer1);
		this.add(btnHelp);
		this.add(btnSettings);
		this.add(btnSearch);
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

		// Adds action listeners
		btnHelp.addActionListener(
				(e) -> new HelpFrame(helpTitle, "<html><p>" + helpText + "</p></html>", 500, 725).setVisible(true));

		btnSave.addActionListener((e) -> {
			mf.saveData(mf.getSaveFilePath());
			JOptionPane.showMessageDialog(mf, "Du har sparat!", "Sparat", JOptionPane.INFORMATION_MESSAGE);
		});

		btnPrint.addActionListener((e) -> print(mf.getGradePanel().getState()));

		btnSaveToFile.addActionListener((e) -> saveToFile(mf.getGradePanel().getState()));

		btnSaveAs.addActionListener((e) -> saveAs(mf.getSaveFilePath()));

		btnSettings.addActionListener((e) -> settings());

		btnSearch.addActionListener(e -> {
			// Checks the windows status
			if (!this.isSearchWindowOpened) {
				//Sets the state
				mf.updateGradePanel(State.NOTHING_SELECTED);

				// Sends Debug message
				System.out.println("["
						+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
								: LocalTime.now().getHour())
						+ ":"
						+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
								: LocalTime.now().getMinute())
						+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
								: LocalTime.now().getSecond())
						+ "] ButtonPanel: Opening SearchWindow");

				// Opens the search frame
				new SearchFrame(mf, this).setVisible(true);

				// Updates the windows status
				this.isSearchWindowOpened = true;
			} else {
				// Sends Debug message
				System.out.println("["
						+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
								: LocalTime.now().getHour())
						+ ":"
						+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
								: LocalTime.now().getMinute())
						+ ":"
						+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
								: LocalTime.now().getSecond())
						+ "] ButtonPanel: Search Window already opened, doing nothing...");
			}
		});
	}

	/**
	 * A getter for the search window
	 *
	 * @param searchWindowOpened if the search window is open or not.
	 */
	public void setSearchWindowOpened(boolean searchWindowOpened) {
		this.isSearchWindowOpened = searchWindowOpened;
	}
}
