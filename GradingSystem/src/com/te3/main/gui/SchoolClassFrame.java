package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.te3.main.enums.State;
import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.SchoolClass;

/**
 * The frame for adding and editing SchoolClasses
 */
public class SchoolClassFrame extends JFrame implements WindowListener {

	/** Generated */
	private static final long serialVersionUID = 8791137725545409036L;

	/** The help text of this frame */
	private String helpInfo = "Skriv in namnet på klassen/gruppen i rutan: <b>Namn</b>. <br>"
			+ "Namnet måste vara minst tre tecken långt. <br><br>"
			+ "För att lägga till elever till klassen/gruppen<br>"
			+ "är det bara att skriva in namnet på eleven i rutan <b>Elev</b>.<br>"
			+ "Namnet måste vara minst 3 tecken långt, <br>" + "för att det ska vara accepterat. <br>"
			+ "Du kan även lägga in flera namn samtidigt. <br>"
			+ "Det är bara att spearera dem, med kommatecken: <i>(\",\")</i>,<br>"
			+ "notera att dessa namnen även behöver vara minst <br>" + "tre tecken långa för att accepteras.<br>"
			+ "Du lägger till namnen genom att antigen<br>"
			+ "trycka på <b>lägg till</b> eller trycka på enter i rutan.<br><br>"
			+ "För att skapa/uppdatera klassen klicka på <b>lägg till</b> eller <b>uppdatera</b>. <br>"
			+ "Tryck på knappen: <b>Tabort</b> för att tabort klassen.";

	// Instances
	MainFrame mf;

	SchoolClass sc;

	// Panels
	NamePanel np = new NamePanel();

	MainSchoolClassPanel mscp;

	AddControlPanel acp = new AddControlPanel();

	EditControlPanel ecp = new EditControlPanel();

	JPanel panel = new JPanel();

	// Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

	// Lables
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");

	/**
	 * For adding.
	 * 
	 * @param mf the instance of the mainframe
	 */
	public SchoolClassFrame(MainFrame mf) {
		// Sets the title through the super constructor
		super("Skapa en ny klass");

		// Stores the instance of the main frame
		this.mf = mf;

		// Initializes the main school class panel
		this.mscp = new MainSchoolClassPanel(mf);

		// Sets the properties
		this.setProperties();

		// Adds the components
		this.addComponents(false);

		// Adds action listener
		acp.getBtnAdd().addActionListener((e) -> {
			addSchoolClass();
		});

		acp.getBtnCancel().addActionListener((e) -> {
			this.dispose();
		});

		acp.getBtnHelp().addActionListener((e) -> {
			new HelpFrame("Skapa en klass", "<html><p>" + helpInfo + "</p></html>", 500, 500).setVisible(true);
		});
	}

	/**
	 * For updating.
	 * 
	 * @param mf the instance of the mainframe
	 * @param sc the schoolclass to be updated
	 */
	public SchoolClassFrame(MainFrame mf, SchoolClass sc) {
		// Calls the super constructor and sets the title of the frame
		super("Uppdatera en klass");

		// Stores the instances
		this.mf = mf;
		try {
			this.sc = new SchoolClass(sc.getName(), sc.getStudents());
		} catch (IllegalNameException e) {
			e.printStackTrace();
		}

		// Initializes the MainSchoolClassPanel
		this.mscp = new MainSchoolClassPanel(mf, this.sc);

		// Sets properties
		this.setProperties();

		// Adds components
		this.addComponents(true);

		// Sets the name to the text field with the name
		np.setLastInput(sc.getName());

		// Adds listeners
		ecp.getBtnUpdate().addActionListener((e) -> updateSchoolClass());

		ecp.getBtnCancel().addActionListener((e) -> this.dispose());

		ecp.getBtnDelete().addActionListener(e -> this.deleteClass());

		ecp.getBtnHelp().addActionListener(
				(e) -> new HelpFrame("Uppdatera en klass", "<html><p>" + helpInfo + "</p></html>", 500, 500)
						.setVisible(true));
	}

	/**
	 * Removes a SchoolClass
	 */
	private void deleteClass() {
		// User Confirmation
		int ans = JOptionPane.showConfirmDialog(this, "Är du säker på att du vill tabort: " + this.sc.getName() + "?",
				"Tabort", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		// If no returns
		if (ans == JOptionPane.NO_OPTION)
			return;

		// Removes the class
		mf.getMainData().removeClass(this.sc);

		// Message to the user
		JOptionPane.showMessageDialog(this, "Du har tagit bort klassen: " + this.sc.getName(), "Tabort",
				JOptionPane.INFORMATION_MESSAGE);

		// Updates gradepanel
		mf.updateGradePanel(State.NOTHING_SELECTED);

		// Updates cbpanel
		mf.cbPanel.refreshData(mf.getMainData());

		// Closes the frame
		this.dispose();
	}

	/**
	 * Updates the class
	 */
	private void updateSchoolClass() {
		// Confirmation message
		int ans = JOptionPane.showConfirmDialog(this,
				"Är du säker på att du vill uppdatera klassen: " + np.getLastInput() + "?", "Är du säker?",
				JOptionPane.YES_NO_OPTION);

		if (ans == JOptionPane.NO_OPTION)
			return;

		// Handles errors

		/*
		 * If there are no students in the the list, the size of the added students is
		 * equal to zero, then it will send a message to the user. After the user
		 * disposes the popup, this method will return, and then nothing happens.
		 */
		if (mscp.getStudents().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst en elev", "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		/*
		 * Checks for class with the same name, but only if the user hasn't changed the
		 * name.
		 *
		 * It will fist check if the name is updated, if not then it will not check for
		 * duplicate names, else it will check for it.
		 *
		 * Then it will send a message to the user and turn the name box red. Then it
		 * will return, so nothing else happens in the method.
		 */
		if (!(sc.getName().equals(np.getLastInput().trim()))) {
			// Checks for duplicate names
			if (this.doesNameExist(np.getLastInput().trim())) {
				JOptionPane.showMessageDialog(this,
						"Namnet: " + np.getLastInput().trim() + " finns redan, välj ett nytt namn.", "Namn finns redan",
						JOptionPane.ERROR_MESSAGE);
				np.setTextFieldColour(Color.PINK);
				return;
			}
		}

		// Gets teh index of the school class
		int index = mf.getMainData().getClasses().indexOf(sc);

		try {
			// Store the instance of the school class
			SchoolClass s = mf.getMainData().getClasses().get(index);

			// Sets the name
			s.setName(np.getLastInput());

			// Sets the students
			s.setStudents(mscp.getStudents());

			// Saves the data
			mf.saveData(mf.getSaveFilePath());

			// Disposes this frame
			this.dispose();
		} catch (IllegalNameException e) {
			// Sets the text box to red
			this.np.setTextFieldColour(Color.PINK);

			// Sends error message
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);

			// returns
			return;
		}

		// Updates the grade panel
		mf.updateGradePanel();

		// Updates cbPanel
		mf.cbPanel.refreshData(mf.getMainData());
	}

	/**
	 * Adds a new class
	 */
	private void addSchoolClass() {
		// Stores the instance of the classes
		ArrayList<SchoolClass> al = mf.getMainData().getClasses();

		// Handles errors

		/*
		 * If there are no students in the the list, the size of the added students is
		 * equal to zero, then it will send a message to the user. After the user
		 * disposes the popup, this method will return, and then nothing happens. It
		 * will also make the list blink.
		 */
		if (mscp.getStudents().size() == 0) {
			// Makes the list blink
			mscp.startFlashing(Color.pink, Color.RED, .5D);

			// Sends error message
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst en elev", "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		/*
		 * Checks for duplicate name.
		 *
		 * If the name is a duplicate then it will send an error message to the user and
		 * set the input box to red, then it will return.
		 */
		if (this.doesNameExist(np.getLastInput().trim())) {
			// Sets the text box to red
			this.np.setTextFieldColour(Color.PINK);

			// Sends error
			JOptionPane.showMessageDialog(this, np.getLastInput().trim() + " är ett namn som redan finns.",
					"Namnet finns redan", JOptionPane.ERROR_MESSAGE);

			// Returns
			return;
		}

		try {
			// Adds a school class
			al.add(new SchoolClass(np.getLastInput(), mscp.getStudents()));

			// Sends a message
			JOptionPane.showMessageDialog(this, "Klassen: " + np.getLastInput() + " är skapad.",
					"Du har skapat en klass", JOptionPane.INFORMATION_MESSAGE);

			// Saves the data
			mf.saveData(mf.getSaveFilePath());

			// Disposes the frame
			this.dispose();
		} catch (IllegalNameException e) {
			// Sets the text box to red
			this.np.setTextFieldColour(Color.PINK);

			// Shows a message to the user
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);

			// returns
			return;
		}

		// Updates the grade panel
		mf.updateGradePanel();

		// Updates CBPanel
		mf.cbPanel.refreshData(mf.getMainData());
	}

	/**
	 * Adds the components
	 *
	 * @param isEdit <br>
	 *               if {@code true} then it's in edit mode. <br>
	 *               if {@code false} then it's in add mode
	 */
	private void addComponents(boolean isEdit) {
		panel.add(np, BorderLayout.PAGE_START);
		panel.add(mscp, BorderLayout.CENTER);
		panel.add((isEdit) ? ecp : acp, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Sets the properties.
	 */
	private void setProperties() {
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));
		panel.setLayout(pLayout);

		this.addWindowListener(this);
	}

	/**
	 * Checks for duplicate names.
	 *
	 * @param name the name of the class
	 *
	 * @return {@code true} if the name is a duplicate. <br>
	 *         {@code false} if the name isn't a duplicate.
	 */
	private boolean doesNameExist(String name) {
		/*
		 * Loops through all the classes and if the name is equal then it will return
		 * true, if it gets through all the classes without being the same name it will
		 * return false.
		 */
		for (var i = 0; i < mf.getMainData().getClasses().size(); i++) {
			if (name.equals(mf.getMainData().getClasses().get(i).getName())) {
				System.out.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
						+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
								: LocalTime.now().getSecond())
						+ "] Found name: " + name + " in another SchoolClass, returning true...");
				return true;
			}
		}

		System.out.println("[" + LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
				+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond() : LocalTime.now().getSecond())
				+ "] " + name + " wasn't found returning false...");
		return false;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// Stops the flashing
		mscp.stopFlashing();

		// Disposes
		this.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
