package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.te3.main.exceptions.IllegalInputException;

public class FileSystemFrame extends JFrame {

	/** Default */
	private static final long serialVersionUID = 1L;
	
	private String path;
	private String name;
	
	Path p;
		
	Container cp = this.getContentPane();
	
	BorderLayout layout = new BorderLayout();
	BorderLayout pWindowLayout = new BorderLayout();
	BorderLayout pInputLayout = new BorderLayout();

	FlowLayout pBtnLayout = new FlowLayout(FlowLayout.RIGHT);
	
	JPanel pWindow = new JPanel();
	JPanel pInput = new JPanel();
	JPanel pBtn = new JPanel();
	
	JList<Object> files = new JList<Object>();
		
	JTextField txfName = new JTextField(12);
	
	JButton btnSave = new JButton("Spara");
	JButton btnCancel = new JButton("Avbryt");
	
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("    ");
	JLabel lblSpacer3 = new JLabel("    ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer5 = new JLabel(" ");
	JLabel lblSpacer6 = new JLabel(" ");
	
	
	public FileSystemFrame(String name) {
		super("Filsystem");
		
		this.path = "./";
		this.setName(name);
		this.setProperties();
		this.addComponents();
	}
	
	private void addComponents() {
		pBtn.add(btnSave);
		pBtn.add(btnCancel);
		
		pInput.add(lblSpacer5, BorderLayout.PAGE_START);
		pInput.add(txfName, BorderLayout.LINE_START);
		pInput.add(lblSpacer6, BorderLayout.CENTER);
		pInput.add(pBtn, BorderLayout.LINE_END);
		
		pWindow.add(files, BorderLayout.CENTER);
		pWindow.add(pInput, BorderLayout.PAGE_END);
		
		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(pWindow, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	private void setProperties() {
		this.setLayout(layout);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(400, 300));
		this.populateTable();
		
		pInput.setLayout(pInputLayout);
		pWindow.setLayout(pWindowLayout);
		pBtn.setLayout(pBtnLayout);
		
		txfName.setText(name);
	}

	private void populateTable() {
		p = Paths.get(this.path);
		
		String[] orgContent = p.toFile().list();
		ArrayList<String> al = new ArrayList<String>();
        Comparator<String> c = String.CASE_INSENSITIVE_ORDER.thenComparing(Comparator.naturalOrder());

		for (int i = 0; i < orgContent.length; i++) {
			if (orgContent[i].indexOf('.') != 0) {
				al.add(orgContent[i]);
			}
		}
		
		al.sort(c);
		files.setListData(al.toArray());
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) throws IllegalInputException {
		if (path.trim().equals("")) {
			throw new IllegalInputException("Path can't be empty!");
		} else {
			this.path = path;
		}
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name.trim().equals("")) {
			this.name = "a.txt";
		} else {
			this.name = name + ".txt";
		}
	}
	
	public String getFilePath() {
		return path + name;
	}
}
