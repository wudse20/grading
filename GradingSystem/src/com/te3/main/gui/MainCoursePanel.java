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
import com.te3.main.objects.Criteria;
import com.te3.main.objects.SchoolClass;

public class MainCoursePanel extends JPanel implements DocumentListener {
	/** Default */
	private static final long serialVersionUID = 1L;

	private int selectedIndexAddedClasses = -1;
	private int selectedIndexNotAddedClasses = -1;
	private int selectedIndexCriteria = -1;

	private ArrayList<SchoolClass> notAddedClasses = new ArrayList<SchoolClass>();
	private ArrayList<SchoolClass> addedClasses = new ArrayList<SchoolClass>();
	private ArrayList<Criteria> criteria = new ArrayList<Criteria>();

	JList<SchoolClass> listNotAddedClasses = new JList<SchoolClass>();
	JList<SchoolClass> listAddedClasses = new JList<SchoolClass>();
	JList<Criteria> listCriteria = new JList<Criteria>();

	JScrollPane scrNotAddedClasses = new JScrollPane(listNotAddedClasses);
	JScrollPane scrAddedClasses = new JScrollPane(listAddedClasses);
	JScrollPane scrCriteria = new JScrollPane(listCriteria);

	JButton btnAdd = new JButton("Lägg till");

	JTextField txfCriteria = new JTextField(12);

	JLabel lblNotAddedClasses = new JLabel("Klasser: ");
	JLabel lblAddedClasses = new JLabel("Tillagada Klasser: ");
	JLabel lblCriteria = new JLabel("Nytt kunskapskrav: ");
	JLabel lblCriteria2 = new JLabel("Kunskapskrav: ");

	JPanel pClasses = new JPanel();
	JPanel pNewCriteria = new JPanel();
	JPanel pCriteria = new JPanel();
	JPanel pLables = new JPanel();

	BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);

	BorderLayout pClassesLayout = new BorderLayout();
	BorderLayout pCriteriaLayout = new BorderLayout();
	BorderLayout pLablesLayout = new BorderLayout();

	FlowLayout pNewCriteriaLayout = new FlowLayout(FlowLayout.LEFT);

	MainFrame mf;

	public MainCoursePanel(MainFrame mf) {
		this.mf = mf;
		this.setLayout(layout);
		this.copyArrayLists();
		this.refreshClasses();
		this.setProperties();
		this.addComonents();
	}

	private void copyArrayLists() {
		ArrayList<SchoolClass> al = mf.getMainData().getClasses();

		for (int i = 0; i < al.size(); i++) {
			SchoolClass sc = al.get(i);
			notAddedClasses.add(sc);
		}
	}

	private void setProperties() {
		listAddedClasses.setPreferredSize(new Dimension(250, 100));
		listNotAddedClasses.setPreferredSize(new Dimension(250, 100));

		listAddedClasses.getSelectionModel().addListSelectionListener((e) -> {
			selectedIndexAddedClasses = listAddedClasses.getSelectedIndex();
			
			if (selectedIndexAddedClasses != -1) {
				classesSelceted((byte) 1, selectedIndexAddedClasses);
			}
		});

		listNotAddedClasses.getSelectionModel().addListSelectionListener((e) -> {
			selectedIndexNotAddedClasses = listNotAddedClasses.getSelectedIndex();
			
			if (selectedIndexNotAddedClasses != -1) {
				classesSelceted((byte) 0, selectedIndexNotAddedClasses);
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
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					addCriteria();
				}
			}
		});
	}

	private void removeCriteria(int index) {
		criteria.remove(index);
		refreshCriteria();
	}

	private void addCriteria() {
		try {
			Criteria c = new Criteria(txfCriteria.getText(), mf);

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
	private void classesSelceted(byte mode, int index) {
		System.out.println("index: " + index);
		if (mode == 0) {
			SchoolClass sc = notAddedClasses.get(index);
			addedClasses.add(sc);
			notAddedClasses.remove(index);
		} else if (mode == 1) {
			SchoolClass sc = addedClasses.get(index);
			notAddedClasses.add(sc);
			addedClasses.remove(index);
		}

		refreshClasses();
	}

	private void refreshClasses() {
		SchoolClass[] arrAdded = new SchoolClass[addedClasses.size()];
		SchoolClass[] arrNotAdded = new SchoolClass[notAddedClasses.size()];
		listNotAddedClasses.setListData(notAddedClasses.toArray(arrNotAdded));
		listAddedClasses.setListData(addedClasses.toArray(arrAdded));
	}

	private void refreshCriteria() {
		Criteria[] arrCriteria = new Criteria[criteria.size()];
		listCriteria.setListData(criteria.toArray(arrCriteria));
	}

	private void addComonents() {
		pLables.setLayout(pLablesLayout);
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

	public ArrayList<SchoolClass> getAddedClasses() {
		return addedClasses;
	}

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
