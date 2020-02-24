package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Course;
import com.te3.main.objects.SchoolClass;

public class CourseFrame extends JFrame {

	/** Default */
	private static final long serialVersionUID = 1L;

	private String helpInfo = "I <b>namn</b>-rutan skall namnet på kursen skrivas in. <br>"
			+ "Namnet måste vara minst 3 tecken långt. <br><br>"
			+ "Klicka på en klass i rutan <b>klasser</b> så kommer den att <br>"
			+ "kopplas till kursen. För att tabort en klass från kursen <br>"
			+ "klicka på den i rutan <b>tillagda klasser</b>. <br><br>"
			+ "För att lägga till ett kunskaps krav till kursen så skriv in <br>"
			+ "in den i rutan: <b>Nytt kunskapskrav</b>. Detta måste vara <br>"
			+ "minst tre tecken långt. Skulle du vilja ta bort <br>"
			+ "ett kunskapskrav behöver du bara klicka på det<br>" + "i <b>kunskapskravs</b>-rutan.";

	MainFrame mf;

	NamePanel np = new NamePanel();

	MainCoursePanel mcp;

	AddControllPanel ccp = new AddControllPanel();

	Container cp = this.getContentPane();

	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("      ");
	JLabel lblSpacer3 = new JLabel("      ");
	JLabel lblSpacer4 = new JLabel(" ");

	JPanel panel = new JPanel();

	public CourseFrame(MainFrame mf) {
		super("Lägg till en kurs");
		this.mf = mf;
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mcp = new MainCoursePanel(mf);

		ccp.getBtnAdd().addActionListener((e) -> {
			newCourse();
		});

		ccp.getBtnCancel().addActionListener((e) -> {
			dispose();
		});

		ccp.getBtnHelp().addActionListener((e) -> {
			new HelpFrame("Lägg till kurs", "<html><p>" + helpInfo + "</p></html>", 500).setVisible(true);
		});

		panel.setLayout(pLayout);
		panel.add(np, BorderLayout.PAGE_START);
		panel.add(mcp, BorderLayout.CENTER);
		panel.add(ccp, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * TODO: Update CBPanel.
	 * 
	 * Adds a new course to the main data.
	 */
	private void newCourse() {
		if (mcp.getAddedClasses().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste välja minst en klass!", "Fel", JOptionPane.ERROR_MESSAGE);
		}

		if (mcp.getCriteria().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst ett kunskapskrav!", "Fel",
					JOptionPane.ERROR_MESSAGE);
		}

		try {
			Course cr = new Course(np.getName(), mcp.getCriteria());
			ArrayList<SchoolClass> al = mf.getMainData().getClasses();
			ArrayList<SchoolClass> al2 = mcp.getAddedClasses();

			for (SchoolClass sc : al2) {
				if (al.contains(sc)) {
					al.get(al.indexOf(sc)).getCourses().add(cr);
				}
			}

			mf.save(mf.getSaveFilePath());
			JOptionPane.showMessageDialog(this, "Du har lagt till kursen: " + np.getLastInput(),
					"Du har lagt till en kurs", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} catch (IllegalNameException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
		}
	}
}
