package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.SearchResult;
import com.te3.main.objects.Student;

/**
 * The frame responsible for searching.
 */
public class SearchFrame extends JFrame implements WindowListener, DocumentListener, ListSelectionListener {

	/** The result of this search */
	private ArrayList<SearchResult> result;

	/** The help text of this screen */
	private final String HELP_TEXT = "<br> I översta rutan kan du söka efter en viss elev som du <br>"
			+ "vill kolla extra på. Det är bara att klicka på namnet i listan.";

	/** The instance of the MainFrame */
	MainFrame mf;

	/** Tbe instance of the GradePanel */
	ButtonPanel bp;

	// TextFields
	JTextField txfInput = new JTextField(20);

	// JLists
	JList<SearchResult> resultList = new JList<SearchResult>();

	// JScrollPane
	JScrollPane scrResultList = new JScrollPane(resultList);

	// Buttons
	JButton btnCancel = new JButton("Avbryt");
	JButton btnHelp = new JButton("?");

	// Lables
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer5 = new JLabel(" ");

	// Panels
	JPanel mainPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel inputPanel = new JPanel();

	// Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pMainLayout = new BorderLayout();

	FlowLayout pBtnLayout = new FlowLayout(FlowLayout.RIGHT);

	BoxLayout pInputLayout = new BoxLayout(inputPanel, BoxLayout.Y_AXIS);

	/**
	 * Creates the SearchFrame
	 *
	 * @param mf The instance of the MainFrame
	 * @param bp The instance of the ButtonPanel
	 */
	public SearchFrame(MainFrame mf, ButtonPanel bp) {
		// Sets the title through the super constructor
		super("Sök efter en elev");

		// Sets some properties
		this.setSize(new Dimension(400, 300));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(layout);

		// Stores instances
		this.mf = mf;
		this.bp = bp;

		// Shows all students
		this.search("");

		// Adds listeners
		// Frame
		this.addWindowListener(this);

		// Buttons
		btnCancel.addActionListener(e -> this.closeWindow());
		btnHelp.addActionListener(
				e -> new HelpFrame("Sök", "<html><p>" + HELP_TEXT + "</p></html>", 250, HelpFrame.DEFAULT_WIDTH)
						.setVisible(true));

		// TextField
		txfInput.getDocument().addDocumentListener(this);

		// JList
		resultList.addListSelectionListener(this);

		// Button panel
		// Sets layout manager
		buttonPanel.setLayout(pBtnLayout);

		// Adds components
		buttonPanel.add(btnHelp);
		buttonPanel.add(btnCancel);

		// Input panel
		// sets layout manager
		inputPanel.setLayout(pInputLayout);

		// Adds components
		inputPanel.add(txfInput);
		inputPanel.add(lblSpacer5);

		// Main Panel
		// Sets the layout manager
		mainPanel.setLayout(pMainLayout);

		// Adds components
		mainPanel.add(inputPanel, BorderLayout.PAGE_START);
		mainPanel.add(scrResultList, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

		// adds components to frame
		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Shows the user the overview.
	 *
	 * @param index the index in the list that was clicked, and the index that the
	 *              search result will open in.
	 */
	private void openOverview(int index) {
		// Stores the result
		SearchResult s = this.result.get(index);

		// Debug message:
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] SearchFrame: List selected at index: " + index + ", SearchResult: "
				+ s.getFoundStudents().get(0).getName());

	}

	private void search(String searchTerm) {
		// Resets the result
		this.result = new ArrayList<SearchResult>();

		// Debug message:
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] SearchFrame: Has reset the last result");

		// Loops through the classes
		for (SchoolClass s : mf.getMainData().getClasses()) {
			// Debug message:
			System.out.println("["
					+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour())
					+ ":"
					+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
							: LocalTime.now().getMinute())
					+ ":"
					+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
							: LocalTime.now().getSecond())
					+ "] SearchFrame: Currently searching through the class: " + s.getName() + "...");

			// Loops through the students
			for (int i = 0; i < s.getStudents().size(); i++) {
				// Stores the student.
				Student st = s.getStudentsNotCloned().get(i);

				// Stores the student's name
				String studentName = st.getName();

				/*
				 * Checks if the search term isn't a substring of the student name. If this case
				 * is true then it will continue, after sending a debug message.
				 */
				if (studentName.indexOf(searchTerm) == -1) {
					// Debug message:
					System.out.println("["
							+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
									: LocalTime.now().getHour())
							+ ":"
							+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
									: LocalTime.now().getMinute())
							+ ":"
							+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
									: LocalTime.now().getSecond())
							+ "] SearchFrame: " + searchTerm + " is not a substring to: " + studentName);

					// Continues
					continue;
				}

				/*
				 * If there are no results in the list it will create a new result and add the
				 * current student. Then continue.
				 */
				if (result.size() == 0) {
					// Debug message:
					System.out.println("["
							+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
									: LocalTime.now().getHour())
							+ ":"
							+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
									: LocalTime.now().getMinute())
							+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
									: LocalTime.now().getSecond())
							+ "] SearchFrame: Adding first result...");

					// Adds a new result
					result.add(new SearchResult());

					// Adds the student
					result.get(0).addStudent(st);

					// Continues
					continue;
				}

				// loops through the search result
				resultLoop: for (int j = 0; j < this.result.size(); j++) {
					// Stores the current result.
					SearchResult res = this.result.get(j);

					/*
					 * If the student is in a search result, it will add this student to that search
					 * result, and if the name isn't in a search result and it's the last iteration
					 * of the loop, then it will add a new search result and add the student.
					 */
					if (res.compare(studentName)) {
						// Adds the student
						res.addStudent(st);

						// Debug message:
						System.out.println("["
								+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
										: LocalTime.now().getHour())
								+ ":"
								+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
										: LocalTime.now().getMinute())
								+ ":"
								+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
										: LocalTime.now().getSecond())
								+ "] SearchFrame: Adding student: " + studentName + " to a current result...");

						// Breaks the result loop
						break resultLoop;
					} else if (j == this.result.size() - 1) {
						// Adds the result
						result.add(new SearchResult());

						// Adds the student
						result.get(j + 1).addStudent(st);

						// Debug message:
						System.out.println("["
								+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
										: LocalTime.now().getHour())
								+ ":"
								+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
										: LocalTime.now().getMinute())
								+ ":"
								+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
										: LocalTime.now().getSecond())
								+ "] SearchFrame: " + studentName
								+ "not found, creating a new result and adding the student...");
					}
				}
			}
		}

		// Debug message:
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] SearchFrame: Updating list data...");

		// Adds the list data to the JList
		SearchResult[] res = new SearchResult[this.result.size()];
		this.resultList.setListData(this.result.toArray(res));
	}

	/**
	 * Closes this Frame, and updates ButtonPanels status.
	 */
	private void closeWindow() {
		// Sets the status to the ButtonPanel
		bp.setSearchWindowOpened(false);

		// Sends debug message
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] SearchFrame: Closing");

		// Disposes this window
		this.dispose();
	}

	/**
	 * Invoked the first time a window is made visible.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowOpened(WindowEvent e) {

	}

	/**
	 * Invoked when the user attempts to close the window from the window's system
	 * menu.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		// Closes the window
		this.closeWindow();
	}

	/**
	 * Invoked when a window has been closed as the result of calling dispose on the
	 * window.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowClosed(WindowEvent e) {

	}

	/**
	 * Invoked when a window is changed from a normal to a minimized state. For many
	 * platforms, a minimized window is displayed as the icon specified in the
	 * window's iconImage property.
	 *
	 * @param e the event to be processed
	 * @see Frame#setIconImage
	 */
	@Override
	public void windowIconified(WindowEvent e) {

	}

	/**
	 * Invoked when a window is changed from a minimized to a normal state.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	/**
	 * Invoked when the Window is set to be the active Window. Only a Frame or a
	 * Dialog can be the active Window. The native windowing system may denote the
	 * active Window or its children with special decorations, such as a highlighted
	 * title bar. The active Window is always either the focused Window, or the
	 * first Frame or Dialog that is an owner of the focused Window.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowActivated(WindowEvent e) {

	}

	/**
	 * Invoked when a Window is no longer the active Window. Only a Frame or a
	 * Dialog can be the active Window. The native windowing system may denote the
	 * active Window or its children with special decorations, such as a highlighted
	 * title bar. The active Window is always either the focused Window, or the
	 * first Frame or Dialog that is an owner of the focused Window.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	/**
	 * Gives notification that there was an insert into the document. The range
	 * given by the DocumentEvent bounds the freshly inserted region.
	 *
	 * @param e the document event
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		// Searches
		this.search(txfInput.getText());
	}

	/**
	 * Gives notification that a portion of the document has been removed. The range
	 * is given in terms of what the view last saw (that is, before updating sticky
	 * positions).
	 *
	 * @param e the document event
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		// Searches
		this.search(txfInput.getText());
	}

	/**
	 * Gives notification that an attribute or set of attributes changed.
	 *
	 * @param e the document event
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	/**
	 * Called whenever the value of the selection changes.
	 *
	 * @param e the event that characterizes the change.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Debug message:
		System.out.println("["
				+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":"
				+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())
				+ ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] SearchFrame: List selected at index: " + resultList.getSelectedIndex());

		// Checks so the index isn't -1, i.e. nothing selected
		if (resultList.getSelectedIndex() != -1) {
			// Opens the overview GUI
			this.openOverview(resultList.getSelectedIndex());
		}
	}
}
