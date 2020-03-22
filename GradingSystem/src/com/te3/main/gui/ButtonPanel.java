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
 * 
 * @author Anton Skorup
 */
public class ButtonPanel extends JPanel {

	/** Default */
	private static final long serialVersionUID = 1L;

	private String helpTitle = "Huvudvy (Placeholder)";
	private String helpInfo = "<html>Detta är en mening. <br> Detta är en till mening. <br> Detta är en placeholder!</html>";

	Thread threadSaveToFile;
	Thread threadSaveAs;

	MainFrame mf;

	FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);

	JButton btnSave = new JButton("Spara");
	JButton btnSaveAs = new JButton("Spara som");
	JButton btnSaveToFile = new JButton("Spara till fil"); // Ska vi döpa om denna?
	JButton btnPrint = new JButton("Skriv ut");
	JButton btnHelp = new JButton("?");

	JLabel lblSpacer1 = new JLabel("  ");

	JTextArea printView = new JTextArea();

	/**
	 * @param mf the instance of the MainFrame
	 */
	public ButtonPanel(MainFrame mf) {
		this.mf = mf;
		this.setProperties();
		this.addComponents();
	}

	/**
	 * @param st the state of the data shown.
	 */
	private void saveToFile(State st) {
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		FileSystemFrame fsf = new FileSystemFrame(s.getName(), "txt");
		fsf.setVisible(true);

		/*
		 * Kom inte på något bättre just nu Måste verkligen fixas, riktigt kass lösning.
		 * Vill inte ha den i fsf, eftersom vill att den ska gå och använda över allt.
		 * Den kör bara 20ggr på 9s det är ju inga problem med performance. Det är enda
		 * lösnigen jag kommer på. Den borde inte lagga ner något.
		 * 
		 * Hjälp mig gärna!
		 */
		threadSaveToFile = new Thread(() -> {
			saveToFileThread(fsf, st);
		});

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
		while (fsf.getExitCode() == -1) {
			try {
				threadSaveToFile.sleep(450);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (fsf.getExitCode() == 0) {
			formatTextFile(st);
			String text = printView.getText();

			try {
				BufferedWriter w = new BufferedWriter(new FileWriter(fsf.getFilePath(), true));
				w.append(text);
				w.close();
				JOptionPane.showMessageDialog(mf, "Du har sparat till en fil!", "Sparat",
						JOptionPane.INFORMATION_MESSAGE);
				fsf.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
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
		formatTextPrint(st);

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
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		SchoolClass sc = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex());

		Task t = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks().get(mf.getCurrentlySelectedAssingmentIndex());

		Course co = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex());

		ArrayList<Criteria> c;

		printView.setText("");
		printView.setTabSize(15);

		printView.append("Namn:\t" + s.getName());
		printView.append("\nKlass:\t" + sc.getName());
		printView.append("\nKurs:\t" + co.getName());

		if (st.equals(State.SINGLE_STUDENT_ASSIGNMENT)) {
			printView.append("\nUppgift:\t" + t.getName());
			c = t.getCriteria();
		} else {
			c = co.getCourseCriteria();
		}

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
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		SchoolClass sc = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex());

		Task t = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks().get(mf.getCurrentlySelectedAssingmentIndex());

		Course co = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(mf.getCurrentlySelectedStudentIndex()).getCourses().get(mf.getCurrentlySelectedCourseIndex());

		ArrayList<Criteria> c;

		printView.setText("");

		printView.append(String.format("%-20s%s", "Namn:", s.getName()) + "\n");
		printView.append(String.format("%-20s%s", "Klass:", sc.getName()) + "\n");
		printView.append(String.format("%-20s%s", "Kurs:", co.getName()) + "\n");

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
		FileSystemFrame fsf = new FileSystemFrame("saves", "xml");
		fsf.setVisible(true);

		/*
		 * Kom inte på något bättre just nu Måste verkligen fixas, riktigt kass lösning.
		 * Vill inte ha den i fsf, eftersom vill att den ska gå och använda över allt.
		 * Den kör bara 20ggr på 9s det är ju inga problem med performance. Det är enda
		 * lösnigen jag kommer på. Den borde inte lagga ner något.
		 * 
		 * Hjälp mig gärna!
		 */
		threadSaveAs = new Thread(() -> {
			saveAsThread(fsf);
		});

		threadSaveAs.start();
	}

	/**
	 * Acts on the FileSystemFrames output.
	 * 
	 * @param fsf the FileSystemFrame
	 */
	@SuppressWarnings("static-access")
	private void saveAsThread(FileSystemFrame fsf) {
		while (fsf.getExitCode() == -1) {
			try {
				threadSaveAs.sleep(450);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (fsf.getExitCode() == 0) {
			try {
				mf.setSaveFilePath(fsf.getFilePath());
				mf.save(mf.getSaveFilePath());
				JOptionPane.showMessageDialog(mf, "Du har sparat", "Sparat", JOptionPane.INFORMATION_MESSAGE);
			} catch (IllegalInputException e) {
				e.printStackTrace();
			}
		}

		fsf.close();
	}

	/**
	 * Adds Components.
	 */
	private void addComponents() {
		this.add(lblSpacer1);
		this.add(btnHelp);
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

		btnHelp.addActionListener((e) -> {
			new HelpFrame(helpTitle, helpInfo, 500).setVisible(true);
		});

		btnSave.addActionListener((e) -> {
			mf.save(mf.getSaveFilePath());
			JOptionPane.showMessageDialog(mf, "Du har sparat!", "Sparat", JOptionPane.INFORMATION_MESSAGE);
		});

		btnPrint.addActionListener((e) -> {
			print(mf.getGradePanel().getState());
		});

		btnSaveToFile.addActionListener((e) -> {
			saveToFile(mf.getGradePanel().getState());
		});

		btnSaveAs.addActionListener((e) -> {
			saveAs(mf.getSaveFilePath());
		});
	}
}
