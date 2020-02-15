package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.exceptions.UnsucessfulFolderCreationException;

public class FileSystemFrame extends JFrame implements KeyListener, ListSelectionListener {

	/** Default */
	private static final long serialVersionUID = 1L;

	/** <b>-1</b>: still running <br><b>0</b>: new file <br><b>1</b>: canceled */
	private byte exitCode;
	
	private String path;
	private String fileName;
	private String fileType;

	private ArrayList<String> content;

	Path p;

	Container cp = this.getContentPane();

	BorderLayout layout = new BorderLayout();
	BorderLayout pWindowLayout = new BorderLayout();
	BorderLayout pInputLayout = new BorderLayout();
	BorderLayout pFilesLayout = new BorderLayout();

	FlowLayout pBtnLayout = new FlowLayout(FlowLayout.RIGHT);
	FlowLayout pHeaderLayout = new FlowLayout(FlowLayout.LEFT);

	JPanel pWindow = new JPanel();
	JPanel pInput = new JPanel();
	JPanel pBtn = new JPanel();
	JPanel pFiles = new JPanel();
	JPanel pHeader = new JPanel();

	JList<Object> files = new JList<Object>();

	JTextField txfName = new JTextField(12);
	JTextField txfPath = new JTextField(15);

	JButton btnSave = new JButton("Spara");
	JButton btnCancel = new JButton("Avbryt");
	JButton btnNewFolder = new JButton("Ny Mapp");

	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("    ");
	JLabel lblSpacer3 = new JLabel("    ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer5 = new JLabel(" ");
	JLabel lblSpacer6 = new JLabel(" ");
	JLabel lblSpacer7 = new JLabel("                      ");

	JLabel lblInfo = new JLabel("Filer:");
	
	JScrollPane scr;

	public FileSystemFrame(String name, String fileType) {
		super("Filsystem");

		this.path = "./";
		this.exitCode = -1;
		try {
			this.setFileType(fileType);
		} catch (IllegalNameException e) {
			e.printStackTrace();
			this.fileType = "txt";
		}
		
		try {
			this.setFileName(name);
		} catch (IllegalNameException e) {
			this.fileName = "";
		}
		
		this.setProperties();
		this.addComponents();
	}

	private void addComponents() {		
		pHeader.add(lblInfo);
		pHeader.add(lblSpacer7);
		pHeader.add(txfPath);

		pFiles.add(pHeader, BorderLayout.PAGE_START);
		pFiles.add(scr, BorderLayout.CENTER);

		pBtn.add(btnNewFolder);
		pBtn.add(btnSave);
		pBtn.add(btnCancel);

		pInput.add(lblSpacer5, BorderLayout.PAGE_START);
		pInput.add(txfName, BorderLayout.LINE_START);
		pInput.add(lblSpacer6, BorderLayout.CENTER);
		pInput.add(pBtn, BorderLayout.LINE_END);

		pWindow.add(pFiles, BorderLayout.CENTER);
		pWindow.add(pInput, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(pWindow, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	private void setProperties() {
		this.setLayout(layout);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(new Dimension(500, 400));
		this.refreshTable(this.path);

		pInput.setLayout(pInputLayout);
		pWindow.setLayout(pWindowLayout);
		pBtn.setLayout(pBtnLayout);
		pFiles.setLayout(pFilesLayout);
		pHeader.setLayout(pHeaderLayout);
		
		scr = new JScrollPane(files);

		txfName.setText(fileName);

		txfPath.addKeyListener(this);
		txfPath.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				txfPath.setBackground(Color.WHITE);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				txfPath.setBackground(Color.WHITE);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		
		txfName.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				txfName.setBackground(Color.WHITE);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				txfName.setBackground(Color.WHITE);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		files.getSelectionModel().addListSelectionListener(this);

		btnNewFolder.addActionListener((e) -> {
			try {
				createNewFolder(JOptionPane.showInputDialog(this, "Vad ska mappen heta?"));
			} catch (IllegalNameException | UnsucessfulFolderCreationException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		btnCancel.addActionListener((e) -> {
			this.setVisible(false);
			this.exitCode = 1;
		});
		
		btnSave.addActionListener((e) -> {
			try {
				this.save();
			} catch (IllegalNameException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				txfName.setBackground(Color.PINK);
			}
		});
	}

	private void save() throws IllegalNameException {
		if (fileName.trim().equals("")) {
			throw new IllegalNameException("Du måste skriva något i rutan");
		} else if (fileName.indexOf('.') != -1) {
			throw new IllegalNameException("Du får inte ha några punkter, .,  i namnet.");
		} else if (fileName.indexOf('/') != -1) {
			throw new IllegalNameException("Du får inte ha några snedsträck, /, i namnet.");
		}
		
		this.setFileName(txfName.getText());
		this.exitCode = 0;
		this.setVisible(false);
	}

	private void createNewFolder(String path) throws IllegalNameException, UnsucessfulFolderCreationException {
		if (path.trim().equals("")) {
			throw new IllegalNameException("Du måste skriva något i rutan");
		} else if (path.indexOf('.') != -1) {
			throw new IllegalNameException("Du får inte ha några punkter, .,  i namnet.");
		} else if (path.indexOf('/') != -1) {
			throw new IllegalNameException("Du får inte ha några snedsträck, /, i namnet.");
		}

		File file = new File(path);
		
		if (file.exists())
			throw new IllegalNameException("Mappen finns redan!");
		
		boolean sucess = file.mkdir();

		if (sucess) {
			refreshTable(this.path);
			JOptionPane.showMessageDialog(this, "Mappen skapades", "Information", JOptionPane.INFORMATION_MESSAGE);
		} else {
			throw new UnsucessfulFolderCreationException("Mappen skapades inte! Något gick fel!");
		}
	}

	private void refreshTable(String path) {
		p = Paths.get(path);

		String[] orgContent = p.toFile().list();
		content = new ArrayList<String>();
		Comparator<String> c = String.CASE_INSENSITIVE_ORDER.thenComparing(Comparator.naturalOrder());

		for (int i = 0; i < orgContent.length; i++) {
			if (orgContent[i].indexOf('.') != 0) {
				content.add(orgContent[i]);
			}
		}

		content.sort(c);
		files.setListData(content.toArray());

		txfPath.setText(path);
	}

	private void listClicked(int selectedIndex) {
		// Bara för att det händer två gånger.
		if (selectedIndex == -1)
			return;

		String selected = content.get(selectedIndex);

		if (selected.indexOf('.') == -1) {
			try {
				this.setPath(this.path + "/" + selected);
				this.refreshTable(this.path);
			} catch (IllegalInputException e) {
				return;
			}
		}
	}

	public void close() {
		this.dispose();
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) throws IllegalInputException {
		if (path.trim().equals("")) {
			throw new IllegalInputException("Du måste skriva något i rutan");
		} else {
			Path p1 = Paths.get(path);
			if (p1.toFile().exists()) {
				this.path = path;
			} else {
				throw new IllegalInputException("Denna mapp finns inte!");
			}
		}
	}

	public String getName() {
		return fileName;
	}

	public void setFileName(String fileName) throws IllegalNameException {
		if (fileName.trim().equals("")) {
			throw new IllegalNameException("För kort!");
		} else {
			this.fileName = fileName;
		}
	}

	public String getFilePath() {
		return path + "/" + fileName + fileType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) throws IllegalNameException {
		if (fileType.trim().equals("")) {
			throw new IllegalNameException("För kort!");
		} else {
			this.fileType = "." + fileType;
		}
	}

	/** <b>-1</b>: still running <br><b> 0</b>: new file <br><b>1</b>: canceled */
	public byte getExitCode() {
		return exitCode;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			try {
				setPath(txfPath.getText());
				refreshTable(this.path);
			} catch (IllegalInputException ex) {
				txfPath.setBackground(Color.PINK);
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		listClicked(files.getSelectedIndex());
	}
}
