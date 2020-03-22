package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The frame used to display a help message.
 * 
 * @author Anton Skorup
 */
public class HelpFrame extends JFrame implements ActionListener {

	/** Default */
	private static final long serialVersionUID = 1L;
	private String title, info;
	private int height;

	Container cp = this.getContentPane();

	BorderLayout layout = new BorderLayout();
	BorderLayout pContentLayout = new BorderLayout();

	JButton btnOk = new JButton("Ok");

	JLabel lblInfo = new JLabel();
	JLabel lblTitle = new JLabel();
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");

	JPanel pContent = new JPanel();

	/**
	 * @param title the title of the window and the text<br>
	 *              Must be HTML-tags.<br>
	 *              .
	 * @param info  the help info about the subject
	 */
	public HelpFrame(String title, String info) {
		this.setTitle(title);
		this.setInfo(info);
		this.setHeight(300);
		this.initialize();
	}

	/**
	 * @param title  the title of the window and the text<br>
	 *               Must be HTML-tags.<br>
	 *               .
	 * @param info   the help info about the subject
	 * @param height the height of the window
	 */
	public HelpFrame(String title, String info, int height) {
		this.setTitle(title);
		this.setInfo(info);
		this.setHeight(height);
		this.initialize();
	}

	private void initialize() {
		this.setTitle(title);
		this.setSize(new Dimension(400, height));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(layout);

		lblTitle.setFont(new Font(lblTitle.getFont().getName(), Font.BOLD, 20));
		lblTitle.setText(title);

		lblInfo.setText(info);

		btnOk.setActionCommand("OK");
		btnOk.addActionListener(this);

		pContent.setLayout(pContentLayout);
		pContent.add(lblTitle, BorderLayout.PAGE_START);
		pContent.add(lblInfo, BorderLayout.CENTER);
		pContent.add(btnOk, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(pContent, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	public void setTitle(String title) {
		this.title = (title.trim().equals("")) ? "Too short title" : title;
	}

	public void setInfo(String info) {
		this.info = (info.trim().equals("")) ? "Too short info" : info;
	}

	public void setHeight(int height) {
		this.height = (height < 100) ? 100 : height;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK")) {
			this.dispose();
		}
	}
}
