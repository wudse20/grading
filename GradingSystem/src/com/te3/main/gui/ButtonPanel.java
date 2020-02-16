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
import com.te3.main.objects.Criteria;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Student;
import com.te3.main.objects.Task;

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
	JButton btnSaveToFile = new JButton("Spara till fil");
	JButton btnPrint = new JButton("Skriv ut");
	JButton btnHelp = new JButton("?");

	JLabel lblSpacer1 = new JLabel("  ");

	JTextArea printView = new JTextArea();

	/**
	 * @param mf the instance of the MainFrame
	 */
	public ButtonPanel(MainFrame mf) {
		this.mf = mf;
		this.setProperites();
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
	 * @param st the state of the information in the program
	 */
	@SuppressWarnings("static-access")
	private void saveToFileThread(FileSystemFrame fsf, State st) {
		while (fsf.getExitCode() == -1) {
			try {
				threadSaveToFile.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (fsf.getExitCode() == 0) {
			fillTextArea(st);
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
		fillTextArea(st);

		try {
			printView.print();
		} catch (PrinterException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Formats the text for save or print.
	 * 
	 * @param st the state of informations shown
	 */
	private void fillTextArea(State st) {
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());

		SchoolClass sc = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex());

		Task t = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getCourses()
				.get(mf.getCurrentlySelectedCourseIndex()).getCourseTasks()
				.get(mf.getCurrentlySelectedAssingmentIndex());

		ArrayList<Criteria> c;

		printView.setText("");
		printView.append("Namn:......." + s.getName() + "\n");
		printView.append("Klass:......" + sc.getName() + "\n");

		if (st.equals(State.SINGLE_STUDENT_ASSIGNMENT)) {
			printView.append("Uppgift:...." + t.getName() + "\n\n");
			c = t.getCriteria();
		} else {
			c = mf.getMainData().getCourses().get(mf.getCurrentlySelectedCourseIndex()).getCourseCriteria();
		}

		printView.append("Kunskapskrav: \n");

//		Gör om senare, detta är bara en temporär fix
//		Detta är en extremt dåligt lösning, för att få
//		fina jämna mellanrum i textfilen och utskriften.
		int length = 0;
		for (int i = 0; i < c.size(); i++) {
			Criteria cr = c.get(i);

			if (cr.getName().length() > length)
				length = cr.getName().length();
		}

		// Lite spaceing
		length += 5;

		for (int i = 0; i < c.size(); i++) {
			Criteria cr = c.get(i);
			StringBuffer sbuf = new StringBuffer();
			String added = "";
			int maxCount = length - cr.getName().length();

			for (int j = 0; j < maxCount; j++) {
				sbuf.append('.');

				if (j == maxCount - 1) {
					added = sbuf.toString();
				}
			}

			printView.append(cr.getName() + ":" + added + cr.getGrade().toString() + "\n");
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
				threadSaveAs.sleep(100);
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
	private void setProperites() {
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
