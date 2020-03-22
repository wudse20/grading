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

public class SchoolClassFrame extends JFrame {

	/** Generated */
	private static final long serialVersionUID = 8791137725545409036L;

	private String helpInfo = "Skriv in namnet på klassen/gruppen i rutan: <b>Namn</b>. <br>"
			+ "Namnet måste vara minst tre tecken långt. <br><br>"
			+ "För att lägga till elever till klassen/gruppen<br>"
			+ "är det bara att skriva in namnet på eleven i rutan <b>Elev</b>.<br>"
			+ "Namnet måste vara minst 3 tecken långt, <br>"
			+ "för att det ska vara accepterat. <br>"
			+ "Du kan även lägga in flera namn samtidigt. <br>"
			+ "Det är bara att spearera dem, med kommatecken, ',',<br>"
			+ "notera att dessa namnen även behöver vara minst <br>"
			+ "tre tecken långa för att accepteras.<br>"
			+ "Du lägger till namnen genom att antigen<br>"
			+ "trycka på <b>lägg till</b> eller trycka på enter i rutan.<br><br>"
			+ "För att skapa/uppdatera klassen klicka på <b>lägg till</b> eller <b>uppdatera</b>.";

	MainFrame mf;

	SchoolClass sc;

	NamePanel np = new NamePanel();

	MainSchoolClassPanel mscp;

	AddControllPanel acp = new AddControllPanel();

	EditControlPanel ecp = new EditControlPanel();

	JPanel panel = new JPanel();

	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

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
		super("Skapa en ny klass");
		this.mf = mf;
		this.mscp = new MainSchoolClassPanel(mf);
		this.setProperties();
		this.addComponents(false);

		acp.getBtnAdd().addActionListener((e) -> {
			addSchoolClass();
		});

		acp.getBtnCancel().addActionListener((e) -> {
			this.dispose();
		});

		acp.getBtnHelp().addActionListener((e) -> {
			new HelpFrame("Klasser", "<html><p>" + helpInfo + "</p></html>", 500).setVisible(true);
		});
	}

	/**
	 * For updating.
	 * 
	 * @param mf the instance of the mainframe
	 * @param sc the schoolclass to be updated
	 */
	public SchoolClassFrame(MainFrame mf, SchoolClass sc) {
		super("Uppdatera en klass");
		this.mf = mf;
		this.sc = sc;
		this.mscp = new MainSchoolClassPanel(mf, this.sc);
		this.setProperties();
		this.addComponents(true);
		np.setLastInput(sc.getName());

		ecp.getBtnUpdate().addActionListener((e) -> {
			updateSchoolClass();
		});

		ecp.getBtnCancel().addActionListener((e) -> {
			this.dispose();
		});

		ecp.getBtnHelp().addActionListener((e) -> {
			new HelpFrame("Klasser", "<html><p>" + helpInfo + "</p></html>", 500).setVisible(true);
		});
	}

	private void updateSchoolClass() {
		int ans = JOptionPane.showConfirmDialog(this,
				"Är du säker på att du vill uppdatera klassen: " + np.getLastInput() + "?", "Är du säker?",
				JOptionPane.YES_NO_OPTION);

		if (ans == JOptionPane.NO_OPTION)
			return;

		if (mscp.getStudents().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst en elev", "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int index = mf.getMainData().getClasses().indexOf(sc);

		try {
			SchoolClass s = mf.getMainData().getClasses().get(index);
			s.setName(np.getLastInput());
			s.setStudents(mscp.getStudents());
			mf.saveData(mf.getSaveFilePath());
			this.dispose();
		} catch (IllegalNameException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	/**
	 * TODO: update CBpanel
	 * 
	 * Adds a new class
	 */
	private void addSchoolClass() {
		ArrayList<SchoolClass> al = mf.getMainData().getClasses();

		if (mscp.getStudents().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst en elev", "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			al.add(new SchoolClass(np.getLastInput(), mscp.getStudents()));
			JOptionPane.showMessageDialog(this, "Klassen: " + np.getLastInput() + " är skapad.",
					"Du har skapat en klass", JOptionPane.INFORMATION_MESSAGE);
			mf.saveData(mf.getSaveFilePath());
			this.dispose();
		} catch (IllegalNameException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	/**
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

	private void setProperties() {
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));
		panel.setLayout(pLayout);
	}
}
