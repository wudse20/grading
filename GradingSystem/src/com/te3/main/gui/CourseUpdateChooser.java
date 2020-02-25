package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.te3.main.objects.Course;

public class CourseUpdateChooser extends JFrame implements ListSelectionListener {

	/** Generated */
	private static final long serialVersionUID = 4286205984317942327L;

	JList<Course> listCourses = new JList<Course>();

	JScrollPane scrCourses = new JScrollPane(listCourses);

	JButton btnHelp = new JButton("?");
	JButton btnCancel = new JButton("Avbryt");

	JLabel lblInfo = new JLabel("Välj en kurs i listan:");
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");

	JPanel pButtons = new JPanel();
	JPanel pMain = new JPanel();

	BorderLayout layout = new BorderLayout();
	BorderLayout pMainLayout = new BorderLayout();
	FlowLayout pLayout = new FlowLayout(FlowLayout.RIGHT);

	MainFrame mf;

	public CourseUpdateChooser(MainFrame mf) {
		super("Uppdatera en kurs");
		this.mf = mf;
		this.setSize(new Dimension(400, 300));

		Course[] courses = new Course[mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex())
				.getCourses().size()];
		listCourses.setListData(
				mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getCourses().toArray(courses));

		listCourses.getSelectionModel().addListSelectionListener(this);

		pButtons.setLayout(pLayout);
		pButtons.add(btnCancel);
		pButtons.add(btnHelp);

		pMain.setLayout(pMainLayout);
		pMain.add(lblInfo, BorderLayout.PAGE_START);
		pMain.add(scrCourses, BorderLayout.CENTER);
		pMain.add(pButtons, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(pMain, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		new CourseFrame(mf, mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getCourses()
				.get(listCourses.getSelectedIndex())).setVisible(true);
		this.dispose();
	}
}
