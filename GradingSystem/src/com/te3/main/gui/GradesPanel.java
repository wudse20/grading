package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.te3.main.enums.State;
import com.te3.main.objects.Criteria;
import com.te3.main.objects.Student;
import com.te3.main.objects.Task;

public class GradesPanel extends JPanel {

	/** Default */
	private static final long serialVersionUID = 1L;

	private State state;
	private ArrayList<Criteria> criteria;
	private ArrayList<Task> tasks;

	JPanel[] criteriaBtnPanels;
	JPanel[] criteriaPanelsNeedsNewName;

	FlowLayout[] criteriaBtnLayouts;
	GridLayout[] criteriaGridLayouts;

	JPanel panel = new JPanel();
	JPanel panelInfo = new JPanel();
	JPanel panelInfo2 = new JPanel();

	BorderLayout layout = new BorderLayout();
	BorderLayout pInfoLayout = new BorderLayout();
	BoxLayout pInfoLayout2 = new BoxLayout(panelInfo2, BoxLayout.Y_AXIS);
	BoxLayout pLayout;

	JScrollPane scroll = new JScrollPane(panel);

	JLabel lblName = new JLabel();
	JLabel lblAssingment = new JLabel();
	JLabel lblGradedAsignments = new JLabel();
	JLabel lblGrades = new JLabel();
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("    ");
	JLabel lblSpacer3 = new JLabel("    ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer6 = new JLabel("    ");

	MainFrame mf;

	public GradesPanel(MainFrame mf) {
		this.state = State.SINGLE_STUDENT_ASSIGNMENT;
		this.mf = mf;
		this.setLayout(layout);

		criteria = mf.getMainData().getCourses().get(mf.getCurrentlySelectedAssingmentIndex()).getCourseCriteria();
		tasks = mf.getMainData().getCourses().get(mf.getCurrentlySelectedAssingmentIndex()).getCourseTasks();

		updateGUI(state);
		
		scroll.setBorder(BorderFactory.createEmptyBorder());

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(panelInfo, BorderLayout.LINE_START);
		this.add(scroll, BorderLayout.CENTER);
		this.add(lblSpacer6, BorderLayout.LINE_END);
	}

	public void updateGUI(State s) {
		if (s.equals(State.SINGLE_STUDENT_ASSIGNMENT)) {
			displayCriteria(criteria);
		} else {
			ArrayList<Criteria> displayedGrades = new ArrayList<Criteria>();

			for (int i = 0; i < tasks.size(); i++) {
				for (var j = 0; j < tasks.get(i).getCriteria().size(); j++) {
					Criteria c = tasks.get(i).getCriteria().get(j);
					int index = displayedGrades.indexOf(c);

					if (index != -1) {
						if (!(displayedGrades.get(index).compare(c))) {
							displayedGrades.remove(index);
							displayedGrades.add(c);
							displayedGrades.get(displayedGrades.indexOf(c)).updateGUI(c.getGrade());
						}
					} else {
						displayedGrades.add(c);
					}
				}
			}

			displayCriteria(displayedGrades);
		}

		// Updates the frame with the new components
		mf.revalidate();
		mf.repaint();
	}

	private void updateInfo(Student s, Task t, ArrayList<Criteria> al) {
		lblName.setText(s.getName());
		lblGradedAsignments.setText("(" + s.getCompletedTasks() + "/" + s.getNumOfTasks() + ")");
		lblGrades.setText(countGrades(al));
		lblAssingment.setText(t.getName());

		lblName.setFont(new Font(lblName.getFont().getName(), Font.BOLD, 20));
		lblGradedAsignments.setFont(new Font(lblName.getFont().getName(), Font.PLAIN, 20));
		lblGrades.setFont(new Font(lblName.getFont().getName(), Font.PLAIN, 20));
		lblAssingment.setFont(new Font(lblAssingment.getFont().getName(), Font.PLAIN, 20));

		panelInfo.setLayout(pInfoLayout);
		panelInfo2.setLayout(pInfoLayout2);

		panelInfo2.add(lblName);
		panelInfo2.add(lblAssingment);
		panelInfo2.add(lblGradedAsignments);
		panelInfo2.add(lblGrades);

		panelInfo.add(lblSpacer2, BorderLayout.LINE_START);
		panelInfo.add(panelInfo2, BorderLayout.CENTER);
		panelInfo.add(lblSpacer3, BorderLayout.LINE_END);
		panelInfo.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	private String countGrades(ArrayList<Criteria> al) {
		int f = 0, e = 0, c = 0, a = 0;
		
		/*
		 * Borde du inte använda al.forEach((n) -> {}); här?
		 */
		for (int i = 0; i < al.size(); i++) {
			switch (al.get(i).getGrade()) {
				case F:
					f++;
					break;
				case E:
					e++;
					break;
				case C:
					c++;
					break;
				case A:
					a++;
					break;
			}
		}
		
		return "F: " + f + ", E: " + e + ", C: " + c + ", A: " + a;
	}

	private void displayCriteria(ArrayList<Criteria> criteria) {
		//Removes all components
		for (Component c : panel.getComponents())
			panel.remove(c);
		
		panel.revalidate();
		panel.repaint();
		
		criteriaBtnPanels = new JPanel[criteria.size()];
		criteriaPanelsNeedsNewName = new JPanel[criteria.size()];
		criteriaBtnLayouts = new FlowLayout[criteria.size()];
		criteriaGridLayouts = new GridLayout[criteria.size()];
		pLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

		panel.setLayout(pLayout);

		for (int i = 0; i < criteria.size(); i++) {
			criteriaBtnPanels[i] = new JPanel();
			criteriaPanelsNeedsNewName[i] = new JPanel();
			criteriaBtnLayouts[i] = new FlowLayout(FlowLayout.LEFT);
			criteriaGridLayouts[i] = new GridLayout(2, 2);
		}

		for (int i = 0; i < criteria.size(); i++) {
			Criteria c = criteria.get(i);
			JButton[] b = c.getGradeBtns();
			criteriaBtnPanels[i].setLayout(criteriaBtnLayouts[i]);
			criteriaPanelsNeedsNewName[i].setLayout(criteriaGridLayouts[i]);

			for (int j = 0; j < b.length; j++) {
				criteriaBtnPanels[i].add(b[j]);
			}

			criteriaPanelsNeedsNewName[i].add(c.getLblCriteria());
			criteriaPanelsNeedsNewName[i].add(c.getLblGrade());
			criteriaPanelsNeedsNewName[i].add(criteriaBtnPanels[i]);

			panel.add(criteriaPanelsNeedsNewName[i]);
		}
		
		updateInfo(
				tasks.get(mf.getCurrentlySelectedAssingmentIndex()).getStudents()
						.get(mf.getCurrentlySelectedStudentIndex()),
				tasks.get(mf.getCurrentlySelectedAssingmentIndex()), criteria);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
