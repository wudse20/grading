package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.te3.main.enums.State;
import com.te3.main.objects.Criteria;
import com.te3.main.objects.Task;

public class GradesPanel extends JPanel {

	/** Default */
	private static final long serialVersionUID = 1L;

	private State state;
	private ArrayList<Criteria> criteria;
	private ArrayList<Task> tasks;

	JPanel[] criteriaBtnPanels;
	JPanel[] criteriaPanels;

	FlowLayout[] criteriaBtnLayouts;
	BorderLayout[] criteriaLayouts;

	JPanel panel = new JPanel();

	BorderLayout layout = new BorderLayout();
	BoxLayout pLayout;

	MainFrame mf;

	public GradesPanel(MainFrame mf) {
		this.state = State.SINGEL_STUDENT_GENERALIZED;
		this.mf = mf;
		this.setLayout(layout);

		criteria = mf.getMainData().getCourses().get(mf.getCurrentlySelectedAssingmentIndex()).getCourseCriteria();
		tasks = mf.getMainData().getCourses().get(mf.getCurrentlySelectedAssingmentIndex()).getCourseTasks();

		updateGUI(state);

		this.add(panel, BorderLayout.CENTER);
	}

	private void updateGUI(State s) {
		if (s.equals(State.SINGEL_STUDENT_ASSIGNMENT)) {
			dispalyCriteria(criteria);
		} else {
			// Temporärt namn, det är inte bra
			ArrayList<Criteria> highestGrades = new ArrayList<Criteria>();

			for (int i = 0; i < tasks.size(); i++) {
				for (var j = 0; j < tasks.get(i).getCriteria().size(); j++) {
					Criteria c = tasks.get(i).getCriteria().get(j);
					int index = highestGrades.indexOf(c);
					
					if (index != -1) {
						if (!(highestGrades.get(index).compare(c))) {
							highestGrades.remove(index);
							highestGrades.add(c);
							highestGrades.get(highestGrades.indexOf(c)).updateGUI(c.getGrade());
						}
					} else {
						highestGrades.add(c);
					}
				}
			}
			
			dispalyCriteria(highestGrades);
		}

		// Updates the frame with the new components
		mf.revalidate();
	}

	private void dispalyCriteria(ArrayList<Criteria> criteria) {
		criteriaBtnPanels = new JPanel[criteria.size()];
		criteriaPanels = new JPanel[criteria.size()];
		criteriaBtnLayouts = new FlowLayout[criteria.size()];
		criteriaLayouts = new BorderLayout[criteria.size()];
		pLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

		panel.setLayout(pLayout);

		for (int i = 0; i < criteria.size(); i++) {
			criteriaBtnPanels[i] = new JPanel();
			criteriaPanels[i] = new JPanel();
			criteriaBtnLayouts[i] = new FlowLayout(FlowLayout.LEFT);
			criteriaLayouts[i] = new BorderLayout();
		}
		
		for (int i = 0; i < criteria.size(); i++) {
			Criteria c = criteria.get(i);
			JButton[] b = c.getGradeBtns();
			criteriaBtnPanels[i].setLayout(criteriaBtnLayouts[i]);

			for (int j = 0; j < b.length; j++) {
				criteriaBtnPanels[i].add(b[j]);
			}

			criteriaPanels[i].add(c.getLblCriteria(), BorderLayout.LINE_START);
			criteriaPanels[i].add(criteriaBtnPanels[i], BorderLayout.CENTER);
			criteriaPanels[i].add(c.getLblGrade(), BorderLayout.LINE_END);

			panel.add(criteriaPanels[i]);
		}
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
