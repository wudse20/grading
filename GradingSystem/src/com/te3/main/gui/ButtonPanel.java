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
import com.te3.main.objects.Criteria;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Student;
import com.te3.main.objects.Task;

public class ButtonPanel extends JPanel {

	/** Default */
	private static final long serialVersionUID = 1L;

	private String helpTitle = "Huvudvy (Placeholder)";
	private String helpInfo = "<html>Detta är en mening. <br> Detta är en till mening. <br> Detta är en placeholder!</html>";

	MainFrame mf;

	FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);

	JButton btnSave = new JButton("Spara");
	JButton btnSaveToFile = new JButton("Spara till fil");
	JButton btnPrint = new JButton("Skriv ut");
	JButton btnHelp = new JButton("?");

	JLabel lblSpacer1 = new JLabel("  ");

	JTextArea printView = new JTextArea();

	public ButtonPanel(MainFrame mf) {
		this.mf = mf;
		this.setProperites();
		this.addComponents();

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
			FileSystemFrame fsf = new FileSystemFrame("Jon W");
			fsf.setVisible(true);
			saveToFile(mf.getGradePanel().getState());
			JOptionPane.showMessageDialog(mf, "Du har sparat till en fil!", "Sparat", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	/**
	 * Behöver ett system för att välja 
	 * spardestination.
	 * 
	 * @param st
	 */
	private void saveToFile(State st) {
		fillTextArea(st);
		String text = printView.getText();
		
		Student s = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents()
				.get(mf.getCurrentlySelectedStudentIndex());
		
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("./" + s.getName() + ".txt", true));
			w.append(text);
			w.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void print(State st) {
		fillTextArea(st);
		
		try {
			printView.print();
		} catch (PrinterException e) {
			System.out.println(e.getMessage());
		}
	}

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
		
		//Lite spaceing
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

	private void addComponents() {
		this.add(lblSpacer1);
		this.add(btnHelp);
		this.add(btnPrint);
		this.add(btnSaveToFile);
		this.add(btnSave);
	}

	private void setProperites() {
		this.setLayout(layout);
	}
}
