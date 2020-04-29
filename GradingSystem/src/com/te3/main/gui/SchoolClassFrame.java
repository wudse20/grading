package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.SchoolClass;

/**
 * The frame for adding and editing SchoolClasses
 *
 * TODO: handle delete action, update helptext
 * */
public class SchoolClassFrame extends JFrame {

	/** Generated */
	private static final long serialVersionUID = 8791137725545409036L;

	/** The help text of this frame */
	private String helpInfo = "Skriv in namnet på klassen/gruppen i rutan: <b>Namn</b>. <br>"
			+ "Namnet måste vara minst tre tecken långt. <br><br>"
			+ "För att lägga till elever till klassen/gruppen<br>"
			+ "är det bara att skriva in namnet på eleven i rutan <b>Elev</b>.<br>"
			+ "Namnet måste vara minst 3 tecken långt, <br>" + "för att det ska vara accepterat. <br>"
			+ "Du kan även lägga in flera namn samtidigt. <br>"
			+ "Det är bara att spearera dem, med kommatecken, ',',<br>"
			+ "notera att dessa namnen även behöver vara minst <br>" + "tre tecken långa för att accepteras.<br>"
			+ "Du lägger till namnen genom att antigen<br>"
			+ "trycka på <b>lägg till</b> eller trycka på enter i rutan.<br><br>"
			+ "För att skapa/uppdatera klassen klicka på <b>lägg till</b> eller <b>uppdatera</b>.";

	//Instances
	MainFrame mf;

	SchoolClass sc;

	//Panels
	NamePanel np = new NamePanel();

	MainSchoolClassPanel mscp;

	AddControlPanel acp = new AddControlPanel();

	EditControlPanel ecp = new EditControlPanel();

	JPanel panel = new JPanel();

	//Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

	//Lables
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
		//Sets the title through the super constructor
		super("Skapa en ny klass");

		//Stores the instance of the main frame
		this.mf = mf;

		//Initializes the main school class panel
		this.mscp = new MainSchoolClassPanel(mf);

		//Sets the properties
		this.setProperties();

		//Adds the components
		this.addComponents(false);

		//Adds action listener
		acp.getBtnAdd().addActionListener((e) -> {
			addSchoolClass();
		});

		acp.getBtnCancel().addActionListener((e) -> {
			this.dispose();
		});

		acp.getBtnHelp().addActionListener((e) -> {
			new HelpFrame("Skapa en klass", "<html><p>" + helpInfo + "</p></html>", 500).setVisible(true);
		});
	}

	/**
	 * For updating.
	 * 
	 * @param mf the instance of the mainframe
	 * @param sc the schoolclass to be updated
	 */
	public SchoolClassFrame(MainFrame mf, SchoolClass sc) {
		//Calls the super constructor and sets the title of the fram
		super("Uppdatera en klass");

		//Stores the instances
		this.mf = mf;
		this.sc = sc;

		//Initializes the MainSchoolClassPanel
		this.mscp = new MainSchoolClassPanel(mf, this.sc);

		//Sets properties
		this.setProperties();

		//Adds components
		this.addComponents(true);

		//Sets the name to the text field with the name
		np.setLastInput(sc.getName());

		//Adds listeners
		ecp.getBtnUpdate().addActionListener((e) -> {
			updateSchoolClass();
		});

		ecp.getBtnCancel().addActionListener((e) -> {
			this.dispose();
		});

		ecp.getBtnHelp().addActionListener((e) -> {
			new HelpFrame("Uppdatera en klass", "<html><p>" + helpInfo + "</p></html>", 500).setVisible(true);
		});
	}

	/**
	 * Updates the class
	 * */
	private void updateSchoolClass() {
		//Confirmation message
		int ans = JOptionPane.showConfirmDialog(this,
				"Är du säker på att du vill uppdatera klassen: " + np.getLastInput() + "?", "Är du säker?",
				JOptionPane.YES_NO_OPTION);

		if (ans == JOptionPane.NO_OPTION)
			return;

		//Handles errors
		if (mscp.getStudents().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst en elev", "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		//Gets teh index of the school class
		int index = mf.getMainData().getClasses().indexOf(sc);

		try {
			//Store the instance of the school class
			SchoolClass s = mf.getMainData().getClasses().get(index);

			//Sets the name
			s.setName(np.getLastInput());

			//Sets the students
			s.setStudents(mscp.getStudents());

			//Saves the data
			mf.saveData(mf.getSaveFilePath());

			//Disposes this frame
			this.dispose();
		} catch (IllegalNameException e) {
			//Sends error message
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		//Updates the grade panel
		mf.updateGradePanel();
	}

	/**
	 * Adds a new class
	 */
	private void addSchoolClass() {
		//Stores the instance of the classes
		ArrayList<SchoolClass> al = mf.getMainData().getClasses();

		//Handles errors
		if (mscp.getStudents().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst en elev", "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			//Adds a school class
			al.add(new SchoolClass(np.getLastInput(), mscp.getStudents()));

			//Sends a message
			JOptionPane.showMessageDialog(this, "Klassen: " + np.getLastInput() + " är skapad.",
					"Du har skapat en klass", JOptionPane.INFORMATION_MESSAGE);

			//Saves the data
			mf.saveData(mf.getSaveFilePath());

			//Disposes the frame
			this.dispose();
		} catch (IllegalNameException e) {
			//Shows a message to the user
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);

			//returns
			return;
		}

		//Updates the grade panel
		mf.updateGradePanel();

		//Updates CBPanel
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
	 * */
	private void setProperties() {
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));
		panel.setLayout(pLayout);
	}
}
