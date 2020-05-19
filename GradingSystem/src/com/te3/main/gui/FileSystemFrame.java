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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.exceptions.UnsuccessfulFolderCreationException;

/**
 * The frame that's used when saving a file.
 */
public class FileSystemFrame extends JFrame implements KeyListener, ListSelectionListener {

	/** Default */
	private static final long serialVersionUID = 1L;

	/**
	 * <b>-1</b>: still running <br>
	 * <b>0</b>: new file <br>
	 * <b>1</b>: canceled
	 */
	private byte exitCode;

	// Strings
	private String path;
	private String fileName;
	private String fileType;

	/** The content of the folder */
	private ArrayList<String> content;

	// Paths
	Path p;

	// Containers
	Container cp = this.getContentPane();

	// Panels
	JPanel pWindow = new JPanel();
	JPanel pInput = new JPanel();
	JPanel pBtn = new JPanel();
	JPanel pFiles = new JPanel();
	JPanel pHeader = new JPanel();
	JPanel pName = new JPanel();
	JPanel pLabel = new JPanel();

	// Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pWindowLayout = new BorderLayout();
	BorderLayout pInputLayout = new BorderLayout();
	BorderLayout pFilesLayout = new BorderLayout();

	FlowLayout pBtnLayout = new FlowLayout(FlowLayout.RIGHT);
	FlowLayout pNameLayout = new FlowLayout(FlowLayout.LEFT);
	FlowLayout pLabelLayout = new FlowLayout(FlowLayout.LEFT);

	BoxLayout pHeaderLayout = new BoxLayout(pHeader, BoxLayout.Y_AXIS);

	// Lists
	JList<Object> files = new JList<Object>();

	// Text Fields
	JTextField txfName = new JTextField(12);
	JTextField txfPath = new JTextField(15);

	// Buttons
	JButton btnSave = new JButton("Spara");
	JButton btnCancel = new JButton("Avbryt");
	JButton btnNewFolder = new JButton("Ny Mapp");

	// Labels
	JLabel lblSpacer1 = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("    ");
	JLabel lblSpacer3 = new JLabel("    ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer5 = new JLabel(" ");
	JLabel lblFileName = new JLabel("Filnamn: ");
	JLabel lblPath = new JLabel("Plats: ");

	JLabel lblInfo = new JLabel("Filer:");

	// Scroll panes
	JScrollPane scr;

	/**
	 * @param name     the file name
	 * @param fileType the file type<br>
	 *                 Ex: xml <br>
	 *                 NOT: .xml
	 */
	public FileSystemFrame(String name, String fileType) {
		super("Filsystem");

		// Setup for att the variables.
		this.path = "./";
		this.exitCode = -1;

		// Sets the file path
		try {
			this.setFileType(fileType);
		} catch (IllegalNameException e) {
			e.printStackTrace();
			this.fileType = "txt";
		}

		// Sets the file name
		try {
			this.setFileName(name);
		} catch (IllegalNameException e) {
			this.fileName = "";
		}

		// Methods for GUI setup
		this.setProperties();
		this.addComponents();
	}

	/**
	 * Adds everything to the frame, and the different panels.
	 */
	private void addComponents() {
		pName.add(lblPath);
		pName.add(txfPath);

		pLabel.add(lblInfo);

		pHeader.add(pName);
		pHeader.add(pLabel);

		pFiles.add(pHeader, BorderLayout.PAGE_START);
		pFiles.add(scr, BorderLayout.CENTER);

		pBtn.add(btnNewFolder);
		pBtn.add(btnSave);
		pBtn.add(btnCancel);

		pInput.add(lblFileName, BorderLayout.LINE_START);
		pInput.add(txfName, BorderLayout.CENTER);
		pInput.add(lblSpacer5, BorderLayout.PAGE_START);
		pInput.add(pBtn, BorderLayout.LINE_END);

		pWindow.add(pFiles, BorderLayout.CENTER);
		pWindow.add(pInput, BorderLayout.PAGE_END);

		this.add(lblSpacer1, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(pWindow, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
	 * Sets all the properties.
	 */
	private void setProperties() {
		// Everything to do with the frame
		this.setLayout(layout);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(new Dimension(500, 400));

		// Refreshes the files in the window.
		this.refreshTable(this.path);

		// Sets all the panel layouts.
		pInput.setLayout(pInputLayout);
		pWindow.setLayout(pWindowLayout);
		pBtn.setLayout(pBtnLayout);
		pFiles.setLayout(pFilesLayout);
		pHeader.setLayout(pHeaderLayout);
		pName.setLayout(pNameLayout);
		pLabel.setLayout(pLabelLayout);

		// Set up for the scroll pane.
		scr = new JScrollPane(files);

		// Sets text in the name text box.
		txfName.setText(fileName);

		// Adds listeners
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

		txfName.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					try {
						save();
					} catch (IllegalNameException ex) {
						txfName.setBackground(Color.PINK);
						JOptionPane.showMessageDialog(null, ex.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		files.getSelectionModel().addListSelectionListener(this);

		btnNewFolder.addActionListener((e) -> {
			try {
				createNewFolder(JOptionPane.showInputDialog(this, "Vad ska mappen heta?"));
			} catch (IllegalNameException | UnsuccessfulFolderCreationException ex) {
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

	/**
	 * Tells the program that the saveData button is pressed and that hides the GUI.
	 * 
	 * @throws IllegalNameException if the name isn't accepted
	 */
	private void save() throws IllegalNameException {
		// Checks for all the inputs that wouldn't work
		if (txfName.getText().trim().equals("")) {
			throw new IllegalNameException("Du måste skriva något i rutan");
		}

		if (txfName.getText().indexOf('.') != -1) {
			throw new IllegalNameException(
					"Du får inte ha några punkter, .,  i namnet. Detta kan vara att du har skivit in filtypen, ta isåfall bort den biten.");
		}

		if (txfName.getText().indexOf('/') != -1) {
			throw new IllegalNameException("Du får inte ha några snedsträck, /, i namnet.");
		}

		// Checks if the file exists
		if (new File(this.getFilePath()).exists()) {
			// Sends confirmation message to the user
			var ans = JOptionPane.showConfirmDialog(this,
					"Denna filen finns redan; är du säker på att du vill spara över den?", "Filen finns redan!",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			/*
			 * If yes was answered then it will continue with the method, if no was answered
			 * it will return.
			 */
			if (ans == JOptionPane.YES_OPTION) {
				// Sends message to the user
				JOptionPane.showMessageDialog(this, "Filen skrivs över!", "OBSERVERA", JOptionPane.INFORMATION_MESSAGE);

				// Debug message
				System.out.println("["
						+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
								: LocalTime.now().getHour())
						+ ":"
						+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
								: LocalTime.now().getMinute())
						+ ":"
						+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
								: LocalTime.now().getSecond())
						+ "] FileSystemFrame: Got permission to overwrite file(" + this.getFilePath()
						+ ") by user, continuing...");
			} else {
				// Debug message
				System.out.println("["
						+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour()
								: LocalTime.now().getHour())
						+ ":"
						+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
								: LocalTime.now().getMinute())
						+ ":"
						+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
								: LocalTime.now().getSecond())
						+ "] FileSystemFrame: Permission denied to overwrite file(" + this.getFilePath()
						+ ") by user, returning...");

				// Returns
				return;
			}
		}

		// Sets some variables up
		this.setFileName(txfName.getText());
		this.exitCode = 0;

		// Hides the frame
		this.setVisible(false);
	}

	/**
	 * Creates a new folder.
	 * 
	 * @param path the path of the new folder.
	 * @throws IllegalNameException                If the name isn't accepted
	 * @throws UnsuccessfulFolderCreationException if the folder creation would
	 *                                             fail.
	 */
	private void createNewFolder(String path) throws IllegalNameException, UnsuccessfulFolderCreationException {
		// So it won't throw a NullPointerException if cancel button is pressed.
		if (path == null)
			return;

		// Checks for all the inputs that wouldn't work
		// If indexOf returns -1 the character isn't in
		// the String.
		if (path.trim().equals("")) {
			throw new IllegalNameException("Du måste skriva något i rutan");
		} else if (path.indexOf('.') != -1) {
			throw new IllegalNameException("Du får inte ha några punkter, .,  i namnet.");
		} else if (path.indexOf('/') != -1) {
			throw new IllegalNameException("Du får inte ha några snedsträck, /, i namnet.");
		}

		// Creates the file
		File file = new File(path);

		// Checks if the folder exists
		if (file.exists())
			throw new IllegalNameException("Mappen finns redan!");

		// Stores if it was successful in it's creation of the folder
		boolean success = file.mkdir();

		// Acts on the folder creation
		if (success) {
			refreshTable(this.path);
			JOptionPane.showMessageDialog(this, "Mappen skapades", "Information", JOptionPane.INFORMATION_MESSAGE);
		} else {
			throw new UnsuccessfulFolderCreationException("Mappen skapades inte! Något gick fel!");
		}
	}

	/**
	 * Updates the part of the program with the files to a set directory.
	 * 
	 * @param path the path that should be displayed
	 */
	private void refreshTable(String path) {
		// Store the path
		p = Paths.get(path);

		// Stores the content in the file
		String[] orgContent = p.toFile().list();

		// Defines the list.
		content = new ArrayList<String>();

		// Defines a comparator for ordering the list in alphabetical order
		Comparator<String> c = String.CASE_INSENSITIVE_ORDER.thenComparing(Comparator.naturalOrder());

		// Adds the content to a list
		for (int i = 0; i < orgContent.length; i++) {
			if (orgContent[i].indexOf('.') != 0) {
				content.add(orgContent[i]);
			}
		}

		// Sorts the list
		content.sort(c);

		// Sets the list data
		files.setListData(content.toArray());

		// Sets the file path to the text field
		txfPath.setText(path);
	}

	/**
	 * Changes path to another path, when clicked.
	 * 
	 * @param selectedIndex the index of the table
	 */
	private void listClicked(int selectedIndex) {
		// It's called twice, to prevent error.
		if (selectedIndex == -1)
			return;

		String selected = content.get(selectedIndex);

		// If there isn't a dot then it's a folder and then we can open that,
		// then it will put the name in to the name box.
		if (selected.indexOf('.') == -1) {
			try {
				// Sets path
				this.setPath(this.path + "/" + selected);

				// Refreshes the table
				this.refreshTable(this.path);
			} catch (IllegalInputException e) {
				// returns
				return;
			}
		} else {
			// Sets the text of the name to the text field.
			txfName.setText(selected.substring(0, selected.indexOf('.')));
		}
	}

	/**
	 * Kills the JFrame. This is a must, else it will run in the background.
	 */
	public void close() {
		this.dispose();
	}

	/**
	 * Gives the directory that's currently selected.
	 * 
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the new directory.
	 * 
	 * @param path the new path
	 * @throws IllegalInputException if the path param isn't accepted.
	 */
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

	/**
	 * returns the file name
	 */
	public String getName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName the new file name
	 * @throws IllegalNameException if the input isn't accepted.
	 */
	public void setFileName(String fileName) throws IllegalNameException {
		if (fileName.trim().equals("")) {
			throw new IllegalNameException("För kort filnamn!");
		} else {
			this.fileName = fileName;
		}
	}

	/**
	 * Gives the new file path.
	 * 
	 * @return the path with the filename and the type (i.e. ./saves/saveData.xml)
	 */
	public String getFilePath() {
		return path + "/" + fileName + fileType;
	}

	/**
	 * Returns the file type with a dot. <br>
	 * I.e. .xml
	 * 
	 * @return the file type
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * Sets the file type.
	 * 
	 * @param fileType the file type without a dot. (i.e. xml)
	 * @throws IllegalNameException if the input isn't accepted.
	 */
	public void setFileType(String fileType) throws IllegalNameException {
		if (fileType.trim().equals("")) {
			throw new IllegalNameException("För kort filtyp!");
		} else {
			this.fileType = "." + fileType;
		}
	}

	/**
	 * <b>-1</b>: still running <br>
	 * <b> 0</b>: new file <br>
	 * <b>1</b>: canceled
	 */
	public byte getExitCode() {
		return exitCode;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// If enter key pressed then set the new path
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
		// Acts on the clicked list
		listClicked(files.getSelectedIndex());
	}
}
