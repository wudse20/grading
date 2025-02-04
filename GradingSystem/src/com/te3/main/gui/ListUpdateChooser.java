package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.te3.main.objects.Course;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Task;

/**
 * The list that shows all the different options before editing.
 *
 * @param <E> The type
 */
public class ListUpdateChooser<E> extends JFrame implements ListSelectionListener {

	/** Generated */
	private static final long serialVersionUID = 4286205984317942327L;

	/** The help info */
	private String helpText = "Välj det objektet i listan som du vill <br>"
			+ "ändra något med, genom att klicka på det.";

	// JList
	JList<E> listObjects = new JList<E>();

	// ArrayLists
	ArrayList<E> al;

	// JScrollPanes
	JScrollPane scrObjects = new JScrollPane(listObjects);

	// Buttons
	JButton btnHelp = new JButton("?");
	JButton btnCancel = new JButton("Avbryt");

	// JLables
	JLabel lblInfo = new JLabel("Välj en kurs i listan:");
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");

	// Panels
	JPanel pButtons = new JPanel();
	JPanel pMain = new JPanel();

	// Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pMainLayout = new BorderLayout();
	FlowLayout pLayout = new FlowLayout(FlowLayout.RIGHT);

	// Instances
	MainFrame mf;

	// Classes
	Class<E> clazz;

	/**
	 * Sets everything up for the GUI.
	 *
	 * @param mf    The instance of the MainFrame
	 * @param al    the list of objects that're available to be edited.
	 * @param clazz the class of the object being edited. ClassName.class
	 */
	public ListUpdateChooser(MainFrame mf, ArrayList<E> al, Class<E> clazz) {
		// Sets properties and stores instances
		super("Uppdatera en kurs");
		this.mf = mf;
		this.al = al;
		this.setSize(new Dimension(400, 300));
		this.clazz = clazz;
		this.setLabelText();

		// Adds the list objects to the JList
		@SuppressWarnings("unchecked")
		E[] courses = (E[]) Array.newInstance(clazz, al.size());
		listObjects.setListData(al.toArray(courses));

		// Adds listeners
		listObjects.getSelectionModel().addListSelectionListener(this);

		btnCancel.addActionListener((e) -> this.dispose());

		btnHelp.addActionListener(
				(e) -> new HelpFrame("Uppdatera", "<html><p>" + helpText + "</html></p>").setVisible(true));

		// Adds components
		pButtons.setLayout(pLayout);
		pButtons.add(btnCancel);
		pButtons.add(btnHelp);

		pMain.setLayout(pMainLayout);
		pMain.add(lblInfo, BorderLayout.PAGE_START);
		pMain.add(scrObjects, BorderLayout.CENTER);
		pMain.add(pButtons, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(pMain, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Sets the GUI text based on the type
	 */
	private void setLabelText() {
		if (clazz.equals(Course.class)) {
			lblInfo.setText("Välj en kurs:");
			this.setTitle("Uppdatera en kurs");
		} else if (clazz.equals(SchoolClass.class)) {
			lblInfo.setText("Välj en klass:");
			this.setTitle("Uppdatera en klass");
		} else if (clazz.equals(Task.class)) {
			lblInfo.setText("Välj en uppgift");
			this.setTitle("Uppdatera en uppgift:");
		}
	}

	/**
	 * Opens the correct gui based on the ino
	 *
	 * @param index the index in the list
	 */
	private void selection(int index) {
		if (clazz.equals(Course.class)) {
			new CourseFrame(mf, (Course) al.get(index)).setVisible(true);
		} else if (clazz.equals(SchoolClass.class)) {
			new SchoolClassFrame(mf, (SchoolClass) al.get(index)).setVisible(true);
		} else if (clazz.equals(Task.class)) {
			new AssignmentFrame(mf, (Task) al.get(index)).setVisible(true);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent ev) {
		// Stores the index
		int index = listObjects.getSelectedIndex();

		// Acts on the index
		selection(index);

		// Closes the frame
		this.dispose();
	}
}
