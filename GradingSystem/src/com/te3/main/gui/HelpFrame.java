package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The frame used to display a help message.
 */
public class HelpFrame extends JFrame implements ActionListener {

	/** Default */
	private static final long serialVersionUID = 1L;

	//Strings
	private String title, info;

	//Integers
	private int height, width;

	//Containers
	Container cp = this.getContentPane();

	//Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pContentLayout = new BorderLayout();

	//Buttons
	JButton btnOk = new JButton("Ok");

	//Labels
	JLabel lblInfo = new JLabel();
	JLabel lblTitle = new JLabel();
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");

	//ScrollPane
	JScrollPane scrPane = new JScrollPane(lblInfo);

	//Panels
	JPanel pContent = new JPanel();

	/**
	 * @param title the title of the window and the text<br>
	 *              Must be HTML-tags.<br>
	 *              .
	 * @param info  the help info about the subject
	 */
	public HelpFrame(String title, String info) {
		//Sets properties
		this.setTitle(title);
		this.setInfo(info);
		this.setHeight(300);
		this.setWidth(400);
		this.initialize();
	}

	/**
	 * Sets up the HelpFrame
	 *
	 * @param title  the title of the window and the text<br>
	 *               Must be HTML-tags.
	 * @param info   the help info about the subject
	 * @param height the height of the window
	 */
	public HelpFrame(String title, String info, int height) {
		//Sets some properties
		this.setTitle(title);
		this.setInfo(info);
		this.setHeight(height);
		this.setWidth(400);
		this.initialize();
	}

	/**
	 * Sets up the HelpFrame
	 *
	 * @param title  the title of the window and the text<br>
	 *               Must be HTML-tags.
	 * @param info   the help info about the subject
	 * @param height the height of the window
	 */
	public HelpFrame(String title, String info, int height, int width) {
		//Sets some properties
		this.setTitle(title);
		this.setInfo(info);
		this.setHeight(height);
		this.setWidth(width);
		this.initialize();
	}

	/**
	 * Initializes the GUI
	 * */
	private void initialize() {
		//Sets some properties
		this.setTitle(title);
		this.setSize(new Dimension(width, height));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(layout);

		lblTitle.setFont(new Font(lblTitle.getFont().getName(), Font.BOLD, 20));
		lblTitle.setText(title);

		lblInfo.setText(info);

		//Sets action command and adds an action listener
		btnOk.setActionCommand("OK");
		btnOk.addActionListener(this);

		//Adds the components
		pContent.setLayout(pContentLayout);
		pContent.add(lblTitle, BorderLayout.PAGE_START);
		pContent.add(scrPane, BorderLayout.CENTER);
		pContent.add(btnOk, BorderLayout.PAGE_END);

		cp.add(lblSpacer1, BorderLayout.PAGE_START);
		cp.add(lblSpacer2, BorderLayout.LINE_START);
		cp.add(pContent, BorderLayout.CENTER);
		cp.add(lblSpacer3, BorderLayout.LINE_END);
		cp.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * A setter for the title
	 *
	 * @param title the title of the frame
	 * */
	public void setTitle(String title) {
		this.title = (title.trim().equals("")) ? "Too short title" : title;
	}

	/**
	 * A setter for the info
	 *
	 * @param info the main body of the frame
	 * */
	public void setInfo(String info) {
		this.info = (info.trim().equals("")) ? "Too short info" : info;
	}

	/**
	 * A setter for the height
	 *
	 * @param height the height of the frame can't be less then 100.
	 * */
	public void setHeight(int height) {
		this.height = (height < 100) ? 100 : height;
	}

	/**
	 * A setter for the width
	 *
	 * @param width the width of teh frame, can't be less then 100.
	 * */
	public void setWidth(int width) {
		this.width = (width < 100) ? 100 : width;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Shuts down the frame on button press with action command: OK
		if (e.getActionCommand().equals("OK")) {
			this.dispose();
		}
	}

}
