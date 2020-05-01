package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Course;
import com.te3.main.objects.Criteria;
import com.te3.main.objects.SchoolClass;

/**
 * The main part of the CourseFrame GUI.
 */
public class MainCoursePanel extends JPanel implements DocumentListener {
	/** Default */
	private static final long serialVersionUID = 1L;

	// The index
	private int selectedIndexAddedClasses = -1;
	private int selectedIndexNotAddedClasses = -1;
	private int selectedIndexCriteria = -1;

	// The array lists
	private ArrayList<SchoolClass> notAddedClasses = new ArrayList<SchoolClass>();
	private ArrayList<SchoolClass> addedClasses = new ArrayList<SchoolClass>();
	private ArrayList<Criteria> criteria = new ArrayList<Criteria>();

	// The JLists
	JList<SchoolClass> listNotAddedClasses = new JList<SchoolClass>();
	JList<SchoolClass> listAddedClasses = new JList<SchoolClass>();
	JList<Criteria> listCriteria = new JList<Criteria>();

	// The scroll panes
	JScrollPane scrNotAddedClasses = new JScrollPane(listNotAddedClasses);
	JScrollPane scrAddedClasses = new JScrollPane(listAddedClasses);
	JScrollPane scrCriteria = new JScrollPane(listCriteria);

	// Buttons
	JButton btnAdd = new JButton("Lägg till");

	// Text boxes
	JTextField txfCriteria = new JTextField(12);

	// Labels
	JLabel lblNotAddedClasses = new JLabel("Klasser: ");
	JLabel lblAddedClasses = new JLabel("Tillagada Klasser: ");
	JLabel lblCriteria = new JLabel("Nytt kunskapskrav: ");
	JLabel lblCriteria2 = new JLabel("Kunskapskrav: ");

	// Panels
	JPanel pClasses = new JPanel();
	JPanel pNewCriteria = new JPanel();
	JPanel pCriteria = new JPanel();
	JPanel pLables = new JPanel();

	// Layouts
	BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);

	BorderLayout pClassesLayout = new BorderLayout();
	BorderLayout pCriteriaLayout = new BorderLayout();
	BorderLayout pLabelsLayout = new BorderLayout();

	FlowLayout pNewCriteriaLayout = new FlowLayout(FlowLayout.LEFT);

	// Instances
	MainFrame mf;

	@SuppressWarnings("unused")
	private Course c;

	/**
	 * For adding.
	 * 
	 * @param mf the instance of the MainFrame
	 */
	public MainCoursePanel(MainFrame mf) {
		// Stores the instance
		this.mf = mf;

		// Sets the layout
		this.setLayout(layout);

		// Copies the array lists
		this.cloneArrayLists();

		// Refreshes the classes
		this.refreshClasses();

		// Sets the properties
		this.setProperties();

		// Adds the components
		this.addComponents();
	}

	/**
	 * For editing.
	 * 
	 * @param mf the instance of the MainFrame
	 * @param c  the course that's being edited
	 */
	public MainCoursePanel(MainFrame mf, Course c) {
		// Stores the instances
		this.mf = mf;
		this.c = c;

		// Gets the classes
		ArrayList<SchoolClass> classes = mf.getMainData().getClasses();

		// Adds the school classes.
		for (int i = 0; i < classes.size(); i++) {
			SchoolClass sc = classes.get(i);

			if (sc.getStudents().get(0).getCourses().contains(c)) {
				addedClasses.add(sc);
			} else {
				notAddedClasses.add(sc);
			}
		}

		// Gets the course criteria
		criteria = c.getCourseCriteria();

		// Sets the layout
		this.setLayout(layout);

		// Refreshes the classes
		this.refreshClasses();

		// Refreshes the criteria
		this.refreshCriteria();

		// Sets the properties
		this.setProperties();

		// Adds the components
		this.addComponents();
	}

	/**
	 * Clones the ArrayList
	 */
	private void cloneArrayLists() {
		// Gets the classes
		ArrayList<SchoolClass> classes = mf.getMainData().getClasses();

		// Makes a clone of the classes
		for (int i = 0; i < classes.size(); i++) {
			SchoolClass sc = classes.get(i);
			try {
				notAddedClasses.add(new SchoolClass(sc.getName(), sc.getStudents()));
			} catch (IllegalNameException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the properties.
	 */
	private void setProperties() {
		// Sets the size
		listAddedClasses.setPreferredSize(new Dimension(250, 100));
		listNotAddedClasses.setPreferredSize(new Dimension(250, 100));

		// Adds listeners
		listAddedClasses.getSelectionModel().addListSelectionListener((e) -> {
			selectedIndexAddedClasses = listAddedClasses.getSelectedIndex();

			if (selectedIndexAddedClasses != -1) {
				classesSelected((byte) 1, selectedIndexAddedClasses);
			}
		});

		listNotAddedClasses.getSelectionModel().addListSelectionListener((e) -> {
			selectedIndexNotAddedClasses = listNotAddedClasses.getSelectedIndex();

			if (selectedIndexNotAddedClasses != -1) {
				classesSelected((byte) 0, selectedIndexNotAddedClasses);
			}
		});

		listCriteria.getSelectionModel().addListSelectionListener((e) -> {
			selectedIndexCriteria = listCriteria.getSelectedIndex();

			if (selectedIndexCriteria != -1) {
				removeCriteria(selectedIndexCriteria);
			}
		});

		btnAdd.addActionListener((e) -> {
			addCriteria();
		});

		txfCriteria.getDocument().addDocumentListener(this);

		txfCriteria.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					addCriteria();
				}
			}
		});
	}

	/**
	 * Removes a criteria.
	 *
	 * @param index the index of the criteria in the lsit
	 */
	private void removeCriteria(int index) {
		// Removes the criteria
		criteria.remove(index);

		// Refreshes the criteira
		refreshCriteria();
	}

	/**
	 * Adds a criteria
	 */
	private void addCriteria() {
		try {
			// Creates a new criteria with the name in the text box
			Criteria c = new Criteria(txfCriteria.getText());

			// If it already exists then it will send an error message else it will add it
			if (criteria.contains(c)) {
				JOptionPane.showMessageDialog(this, "Du har redan lagt till detta kunskapskravet!", "Fel",
						JOptionPane.ERROR_MESSAGE);
				txfCriteria.setBackground(Color.PINK);
				return;
			} else {
				txfCriteria.setText("");
				criteria.add(c);
				refreshCriteria();
			}
		} catch (IllegalNameException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
			txfCriteria.setBackground(Color.PINK);
		}
	}

	/**
	 * Adds and removes classes from the added list.
	 * 
	 * @param mode  <br>
	 *              <b>0</b>: added <br>
	 *              <b>1</b>: removed
	 * @param index the selected index
	 */
	private void classesSelected(byte mode, int index) {
		// Adds or removes a class from the list
		if (mode == 0) {
			SchoolClass sc = notAddedClasses.get(index);
			addedClasses.add(sc);
			notAddedClasses.remove(index);
		} else if (mode == 1) {
			SchoolClass sc = addedClasses.get(index);
			notAddedClasses.add(sc);
			addedClasses.remove(index);
		}

		// refreshes the classes
		refreshClasses();
	}

	/**
	 * Refreshes the class GUI
	 */
	private void refreshClasses() {
		// Gets the data and sets it to JLists
		SchoolClass[] arrAdded = new SchoolClass[addedClasses.size()];
		SchoolClass[] arrNotAdded = new SchoolClass[notAddedClasses.size()];

		listNotAddedClasses.setListData(notAddedClasses.toArray(arrNotAdded));
		listAddedClasses.setListData(addedClasses.toArray(arrAdded));
	}

	/**
	 * Refreshes the criteria GUI
	 */
	private void refreshCriteria() {
		// Gets the data and sets it to the JList
		Criteria[] arrCriteria = new Criteria[criteria.size()];

		listCriteria.setListData(criteria.toArray(arrCriteria));
	}

	/**
	 * Adds the components
	 */
	private void addComponents() {
		pLables.setLayout(pLabelsLayout);
		pLables.add(lblNotAddedClasses, BorderLayout.LINE_START);
		pLables.add(lblAddedClasses, BorderLayout.LINE_END);

		pClasses.setLayout(pClassesLayout);
		pClasses.add(scrNotAddedClasses, BorderLayout.LINE_START);
		pClasses.add(scrAddedClasses, BorderLayout.LINE_END);

		pNewCriteria.setLayout(pNewCriteriaLayout);
		pNewCriteria.add(lblCriteria);
		pNewCriteria.add(txfCriteria);
		pNewCriteria.add(btnAdd);

		pCriteria.setLayout(pCriteriaLayout);
		pCriteria.add(lblCriteria2, BorderLayout.PAGE_START);
		pCriteria.add(scrCriteria, BorderLayout.CENTER);

		this.add(pLables);
		this.add(pClasses);
		this.add(pNewCriteria);
		this.add(pCriteria);
	}

	/**
	 * Getter for the added classes
	 *
	 * @return the added classes
	 */
	public ArrayList<SchoolClass> getAddedClasses() {
		return addedClasses;
	}

	/**
	 * Getter for the criteria
	 *
	 * @return the criteria
	 */
	public ArrayList<Criteria> getCriteria() {
		return criteria;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		txfCriteria.setBackground(Color.white);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		txfCriteria.setBackground(Color.white);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}
}
