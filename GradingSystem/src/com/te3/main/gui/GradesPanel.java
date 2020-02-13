package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.te3.main.objects.Criteria;

public class GradesPanel extends JPanel {

	/** Default */
	private static final long serialVersionUID = 1L;

	/**
	 * An enum for the different states of this panel.
	 * 
	 * @author Anton Skorup
	 */
	enum State {
		/** A generalized view for all the criteria. */
		SINGEL_STUDENT_GENERALIZED,
		/** A view for all the */
		SINGEL_STUDENT_ASSIGNMENT;
	}

	private State state;
	private ArrayList<Criteria> criteria;

	JPanel[] criteriaBtnPanels;
	JPanel[] criteriaPanels;

	FlowLayout[] criteriaBtnLayouts;
	BorderLayout[] criteriaLayouts;

	JPanel panel = new JPanel();
	
	BorderLayout layout = new BorderLayout();
	//Måste bytas
	GridLayout pLayout;

	MainFrame mf;

	public GradesPanel(MainFrame mf) {
		this.state = State.SINGEL_STUDENT_ASSIGNMENT;
		this.mf = mf;
		this.setLayout(layout);

		criteria = mf.getMainData().getCourses().get(mf.getCurrentlySelectedAssingmentIndex()).getCourseCriteria();
		
		updateGUI(state);
		
		this.add(panel, BorderLayout.CENTER);
	}

	private void updateGUI(State s) {
		criteriaBtnPanels = new JPanel[criteria.size()];
		criteriaPanels = new JPanel[criteria.size()];
		criteriaBtnLayouts = new FlowLayout[criteria.size()];
		criteriaLayouts = new BorderLayout[criteria.size()];
		pLayout = new GridLayout(criteria.size(), 1);
		
		panel.setLayout(pLayout);

		for (int i = 0; i < criteria.size(); i++) {
			criteriaBtnPanels[i] = new JPanel();
			criteriaPanels[i] = new JPanel();
			criteriaBtnLayouts[i] = new FlowLayout(FlowLayout.LEFT);
			criteriaLayouts[i] = new BorderLayout();
		}

		if (s.equals(State.SINGEL_STUDENT_ASSIGNMENT)) {
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
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
