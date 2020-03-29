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

/**
 * The class that holds the Frame for
 * managing courses.
 * */
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

	Course c;

	MainFrame mf;

	NamePanel np = new NamePanel();

	MainCoursePanel mcp;

	AddControlPanel acp = new AddControlPanel();

	EditControlPanel ecp = new EditControlPanel();

	Container cp = this.getContentPane();

	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("      ");
	JLabel lblSpacer3 = new JLabel("      ");
	JLabel lblSpacer4 = new JLabel(" ");

	JPanel panel = new JPanel();

	/**
	 * For adding.
	 * 
	 * @param mf The instance of the MainFrame.
	 */
	public CourseFrame(MainFrame mf) {
		super("Lägg till en kurs");
		this.mf = mf;
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mcp = new MainCoursePanel(mf);

		acp.getBtnAdd().addActionListener((e) -> {
			newCourse();
		});

		acp.getBtnCancel().addActionListener((e) -> {
			dispose();
		});

		acp.getBtnHelp().addActionListener((e) -> {
			new HelpFrame("Lägg till kurs", "<html><p>" + helpInfo + "</p></html>", 500).setVisible(true);
		});

		panel.setLayout(pLayout);
		panel.add(np, BorderLayout.PAGE_START);
		panel.add(mcp, BorderLayout.CENTER);
		panel.add(acp, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * For editing.
	 * 
	 * @param mf the instance of the MainFrame
	 * @param c  the course that's being updated.
	 */
	public CourseFrame(MainFrame mf, Course c) {
		super("Uppdatera en kurs");
		this.mf = mf;
		this.c = c;
		this.setLayout(layout);
		this.setSize(new Dimension(600, 600));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mcp = new MainCoursePanel(mf, c);
		np.setLastInput(c.getName());

		ecp.getBtnUpdate().addActionListener((e) -> {
			updateCourse();
		});

		ecp.getBtnCancel().addActionListener((e) -> {
			dispose();
		});

		ecp.getBtnHelp().addActionListener((e) -> {
			new HelpFrame("Lägg till kurs", "<html><p>" + helpInfo + "</p></html>", 500).setVisible(true);
		});

		panel.setLayout(pLayout);
		panel.add(np, BorderLayout.PAGE_START);
		panel.add(mcp, BorderLayout.CENTER);
		panel.add(ecp, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * TODO: Update CBPanel.<br>
	 * <br>
	 * 
	 * Adds a new course to the main data.
	 */
	private void newCourse() {
		if (mcp.getAddedClasses().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste välja minst en klass!", "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (mcp.getCriteria().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst ett kunskapskrav!", "Fel",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			ArrayList<SchoolClass> al2 = mcp.getAddedClasses();

			for (var i = 0; i < al2.size(); i++) {
				for (var j = 0; j < al2.get(i).getStudents().size(); j++) {
					Course c = new Course(np.getLastInput(), mcp.getCriteria());
					al2.get(i).getStudents().get(j).addCourse(c);
				}
			}

			mf.saveData(mf.getSaveFilePath());
			JOptionPane.showMessageDialog(this, "Du har lagt till kursen: " + np.getLastInput(),
					"Du har lagt till en kurs", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} catch (IllegalNameException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * TODO: Update CBPanel. <br>
	 * <br>
	 * 
	 * Updates a course.
	 */
	private void updateCourse() {
		int ans = JOptionPane.showConfirmDialog(this,
				"Är du säker på att du vill uppdatera kursen: " + np.getLastInput() + "?", "Är du säker?",
				JOptionPane.YES_NO_OPTION);

		if (ans == JOptionPane.NO_OPTION)
			return;

		if (mcp.getAddedClasses().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste välja minst en klass!", "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (mcp.getCriteria().size() == 0) {
			JOptionPane.showMessageDialog(this, "Du måste lägga till minst ett kunskapskrav!", "Fel",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			for (int i = 0; i < mf.getMainData().getClasses().size(); i++) {
				for (int j = 0; j < mf.getMainData().getClasses().get(i).getStudents().size(); j++) {
					int index = mf.getMainData().getClasses().get(i).getStudents().get(j).getCourses().indexOf(c);
					if (index != -1) {
						mf.getMainData().getClasses().get(i).getStudents().get(j).getCourses().remove(index);
					}
				}
			}

			// Adds the course to the correct school classes
			ArrayList<SchoolClass> al = mf.getMainData().getClasses();
			ArrayList<SchoolClass> al2 = mcp.getAddedClasses();

			for (int i = 0; i < al.size(); i++) {
				SchoolClass sc = al.get(i);

				if (al2.contains(sc)) {
					for (int j = 0; j < al.get(i).getStudents().size(); j++) {
						al.get(i).getStudents().get(j).addCourse(new Course(np.getLastInput(), mcp.getCriteria()));
						al.get(i).getStudents().get(j).getCourses()
								.get(al.get(i).getStudents().get(j).getCourses().size() - 1)
								.setCourseTasks(c.getCourseTasks());
					}
				}
			}

			this.dispose();
		} catch (IllegalNameException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
