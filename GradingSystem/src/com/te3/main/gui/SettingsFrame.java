package com.te3.main.gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.te3.main.enums.State;
import com.te3.main.gui.easteregg.GUI.GameFrame;
import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.objects.Data;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Settings;

/**
 * The frame that holds all the settings
 * */
public class SettingsFrame extends JFrame {
	/** Generated */
	private static final long serialVersionUID = 2812677933749486134L;

	/** The path of the save file */
	private String savePath;

	/** The help text of this frame */
	private String helpText =
			"För att ställa in hur ofta som <br>"
			+ "programmet kommer att autospara <br>"
			+ "skriv in intervallet i sekunder. <br>"
			+ "Det måste vara mellan 60 och 3600 sekunder. <br><br>"
			+ "För att sätta på bakgrundsbilden klicka på rutan <b>Baby Yoda som bakgrund</b>"
			+ "<br><br> För att välja bakgrundsbild klicka på den bilden du vill ha. <br><br>"
			+ "För att ställa in vart data skall sparas <br>"
			+ "klicka på knappen: <b>Ställ in plats för sparfil</b>"
			+ " och följ instruktionerna <br><br>"
			+ "För att radera all data tryck på knappen <b>Rensa all data</b><br><br>"
			+ "För att applicera inställningarna tryck på <b>verkställ</b><br>"
			+ "För att avbryta tryck på <b>avbryt</b>";

	/** The current yoda */
	private String currentYoda;

	//Instance
	private MainFrame mf;

	/** If the easter egg is opened or not. */
	private boolean isEasterEggOpened = false;

	/** The amount of times that yoda is pressed */
	private int yodaClickCount = 0;

	//Threads
	private Thread threadSetSavePath;

	//Layouts
	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();

	FlowLayout pBtnsLayout = new FlowLayout(FlowLayout.RIGHT);
	FlowLayout pTimerLayout = new FlowLayout(FlowLayout.LEFT);
	FlowLayout pSettingBtnsLayout = new FlowLayout(FlowLayout.LEFT);
	FlowLayout pCheckBoxLayout = new FlowLayout(FlowLayout.LEFT);
	FlowLayout pYodaBtnsLayout = new FlowLayout(FlowLayout.LEFT);
	FlowLayout pChoseYodaLayout = new FlowLayout(FlowLayout.LEFT);

	BoxLayout pSettingsLayout;

	//Container
	Container cp = this.getContentPane();

	//Buttons
	JButton btnApply = new JButton("Verkställ");
	JButton btnCancel = new JButton("Avbryt");
	JButton btnHelp = new JButton("?");
	JButton btnSetSavePath = new JButton("Ställ in plats för sparfil"); // Försök komma på bättre text för att knappen
	JButton banDeleteData = new JButton("Rensa all data");

	JButton btnYoda1 = new JButton();
	JButton btnYoda2 = new JButton();
	JButton btnYoda3 = new JButton();
	JButton btnYoda4 = new JButton();

	//Lables
	JLabel lblSpacer = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer5 = new JLabel(" ");
	JLabel lblSpacer6 = new JLabel(" ");
	JLabel lblInfo = new JLabel("Inställningar: ");
	JLabel lblTimer = new JLabel("Sparintervall (s):");
	JLabel lblChoseYoda = new JLabel("Välj Yoda:");

	//Text fields
	JTextField txfSaveTimer;

	//Check boxes
	JCheckBox cBoxShouldShowBabyYoda = new JCheckBox("Baby Yoda som bakgrund");

	//Panels
	JPanel panel = new JPanel();
	JPanel pBtns = new JPanel();
	JPanel pTimer = new JPanel();
	JPanel pSettingBtns = new JPanel();
	JPanel pSettings = new JPanel();
	JPanel pCheckBox = new JPanel();
	JPanel pChoseYoda = new JPanel();
	JPanel pYodaBtns = new JPanel();

	/**
     * Sets everything up
     *
     * @param mf the instance of the MainFrame
     * */
	public SettingsFrame(MainFrame mf) {
		//Calls the super constructor
		super("Inställningar");

		//Saves the necessary information
		this.mf = mf;
		this.savePath = mf.getSaveFilePath();
		this.currentYoda = mf.getCurrentYoda();

		//Sets the properties
		this.setProperties();

		//Adds the components
		this.addComponents();
	}

	/**
     * Adds the components
     * */
	private void addComponents() {
		cp.add(lblSpacer, BorderLayout.PAGE_START);
		cp.add(lblSpacer2, BorderLayout.LINE_START);
		cp.add(panel, BorderLayout.CENTER);
		cp.add(lblSpacer3, BorderLayout.LINE_END);
		cp.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
     * Sets the properties
     * */
	private void setProperties() {
		//Sets the layout
		this.setLayout(layout);

		//Sets the size
		this.setSize(new Dimension(472, 350));

		//Sets the closing operation
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//Sets the current yoda
		currentYoda = mf.getCurrentYoda();

		//Some properties
		cBoxShouldShowBabyYoda.setSelected(mf.shouldShowBabyYoda());
		setYodaBtnStatus(mf.shouldShowBabyYoda());
		setYodaBtnSize(100, 56);
		setYodaBtnIcons();

		txfSaveTimer = new JTextField(12);
		txfSaveTimer.setText(Integer.toString(mf.getSaveTimer()));

		//Adds listeners
		txfSaveTimer.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				//Sets the color to white
				txfSaveTimer.setBackground(Color.WHITE);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				//Sets the color to white
				txfSaveTimer.setBackground(Color.WHITE);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		btnApply.addActionListener((e) -> applySettings());
		btnCancel.addActionListener((e) -> this.dispose());
		btnHelp.addActionListener(
				(e) -> new HelpFrame("Inställningar", "<html><p>" + helpText + "</p></html>", 500).setVisible(true));
		btnSetSavePath.addActionListener((e) -> setSavePath());
		banDeleteData.addActionListener((e) -> deleteData());
		cBoxShouldShowBabyYoda.addActionListener((e) -> setYodaBtnStatus(cBoxShouldShowBabyYoda.isSelected()));

		btnYoda1.addActionListener((e) -> {
			//Sets the current yoda
			currentYoda = "yoda1.jpg";

			//Sets the button icons
			setYodaBtnIcons();

			//Increments the count
			yodaClickCount++;

			//Opens the easter egg if it should else it resets the counter.
			if (yodaClickCount > 3 && !isEasterEggOpened) {
				isEasterEggOpened = true;
				yodaClickCount = 0;
				new GameFrame(this).setVisible(true);
			} else if (yodaClickCount > 3 && isEasterEggOpened) {
				yodaClickCount = 0;
			}
		});

		btnYoda2.addActionListener((e) -> {
			//Sets the current yoda
			currentYoda = "yoda2.jpg";

			//Sets the button icons
			setYodaBtnIcons();
		});

		btnYoda3.addActionListener((e) -> {
			//Sets the current yoda
			currentYoda = "yoda3.jpg";

			//Sets the button icons
			setYodaBtnIcons();
		});

		btnYoda4.addActionListener((e) -> {
			//Sets the current yoda
			currentYoda = "yoda4.jpg";

			//Adds the button icons
			setYodaBtnIcons();
		});

		/*
		 * Adds components and sets
		 * layout to all the different
		 * panels of this GUI
		 * */
		pTimer.setLayout(pTimerLayout);
		pTimer.add(lblTimer);
		pTimer.add(txfSaveTimer);

		pSettingBtns.setLayout(pSettingBtnsLayout);
		pSettingBtns.add(btnSetSavePath);
		pSettingBtns.add(banDeleteData);

		pCheckBox.setLayout(pCheckBoxLayout);
		pCheckBox.add(cBoxShouldShowBabyYoda);

		pChoseYoda.setLayout(pChoseYodaLayout);
		pChoseYoda.add(lblChoseYoda);

		pYodaBtns.setLayout(pYodaBtnsLayout);
		pYodaBtns.add(btnYoda1);
		pYodaBtns.add(btnYoda2);
		pYodaBtns.add(btnYoda3);
		pYodaBtns.add(btnYoda4);

		pSettingsLayout = new BoxLayout(pSettings, BoxLayout.Y_AXIS);
		pSettings.setLayout(pSettingsLayout);
		pSettings.add(lblSpacer5);
		pSettings.add(pTimer);
		pSettings.add(pCheckBox);
		pSettings.add(pChoseYoda);
		pSettings.add(pYodaBtns);
		pSettings.add(pSettingBtns);
		pSettings.add(lblSpacer6);

		pBtns.setLayout(pBtnsLayout);
		pBtns.add(btnHelp);
		pBtns.add(btnCancel);
		pBtns.add(btnApply);

		panel.setLayout(pLayout);
		panel.add(lblInfo, BorderLayout.PAGE_START);
		panel.add(pSettings, BorderLayout.CENTER);
		panel.add(pBtns, BorderLayout.PAGE_END);
	}


	/**
	 * Sets the icons of the Yoda buttons
	 * */
	private void setYodaBtnIcons() {
		//If the number is in the string then it acts based on that
		//and makes the buttons look the correct way.
		if (currentYoda.indexOf('1') != -1) {
			btnYoda1.setIcon(new ImageIcon("./src/images/thumbnails/tYoda1Selected.jpg"));
			btnYoda2.setIcon(new ImageIcon("./src/images/thumbnails/tYoda2.jpg"));
			btnYoda3.setIcon(new ImageIcon("./src/images/thumbnails/tYoda3.jpg"));
			btnYoda4.setIcon(new ImageIcon("./src/images/thumbnails/tYoda4.jpg"));
		} else if (currentYoda.indexOf('2') != -1) {
			btnYoda1.setIcon(new ImageIcon("./src/images/thumbnails/tYoda1.jpg"));
			btnYoda2.setIcon(new ImageIcon("./src/images/thumbnails/tYoda2Selected.jpg"));
			btnYoda3.setIcon(new ImageIcon("./src/images/thumbnails/tYoda3.jpg"));
			btnYoda4.setIcon(new ImageIcon("./src/images/thumbnails/tYoda4.jpg"));
		} else if (currentYoda.indexOf('3') != -1) {
			btnYoda1.setIcon(new ImageIcon("./src/images/thumbnails/tYoda1.jpg"));
			btnYoda2.setIcon(new ImageIcon("./src/images/thumbnails/tYoda2.jpg"));
			btnYoda3.setIcon(new ImageIcon("./src/images/thumbnails/tYoda3Selected.jpg"));
			btnYoda4.setIcon(new ImageIcon("./src/images/thumbnails/tYoda4.jpg"));
		} else if (currentYoda.indexOf('4') != -1) {
			btnYoda1.setIcon(new ImageIcon("./src/images/thumbnails/tYoda1.jpg"));
			btnYoda2.setIcon(new ImageIcon("./src/images/thumbnails/tYoda2.jpg"));
			btnYoda3.setIcon(new ImageIcon("./src/images/thumbnails/tYoda3.jpg"));
			btnYoda4.setIcon(new ImageIcon("./src/images/thumbnails/tYoda4Selected.jpg"));
		}
	}

	/**
	 * Sets the size of the yoda buttons.
	 *
	 * @param width the width of the image
	 * @param height the height of the image
	 */
	private void setYodaBtnSize(int width, int height) {
		//Sets the size of the button
		btnYoda1.setPreferredSize(new Dimension(width, height));
		btnYoda2.setPreferredSize(new Dimension(width, height));
		btnYoda3.setPreferredSize(new Dimension(width, height));
		btnYoda4.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * Sets the status of the yoda buttons.
	 *
	 * @param isActivated if the buttons are activated or not.
	 * */
	private void setYodaBtnStatus(boolean isActivated) {
		btnYoda1.setEnabled(isActivated);
		btnYoda2.setEnabled(isActivated);
		btnYoda3.setEnabled(isActivated);
		btnYoda4.setEnabled(isActivated);
	}

	/**
	 * Not a setter
	 */
	private void setSavePath() {
		//Creates a new FileSystemFrame
		FileSystemFrame fsf = new FileSystemFrame(
				this.savePath.substring(this.savePath.lastIndexOf('/') + 1, this.savePath.lastIndexOf('.')), "xml");

		//Displays the FileSystemFrame
		fsf.setVisible(true);

		//Defines and start the thread
		threadSetSavePath = new Thread(() -> setSavePathThread(fsf));
		threadSetSavePath.start();
	}

	/**
     * The thread method for settings the save path
     *
     * @param fsf the instance of the current FileSystemFrame
     * */
	@SuppressWarnings("static-access")
	private void setSavePathThread(FileSystemFrame fsf) {
		//Waiting for user input
		while (fsf.getExitCode() == -1) {
			//Sleeping to save on performance
			try {
				threadSetSavePath.sleep(450);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//Acts on the users input
		if (fsf.getExitCode() == 0) {
			this.savePath = fsf.getFilePath();
			System.out.println(this.savePath);
		}

		//Closes the FileSystemFrame
		fsf.close();
	}

	/**
     * Applies the settings
     * */
	private void applySettings() {
		//Creates a new settings object
		Settings s = new Settings();

		//Sets the timer or sends an error message to the user
		try {
			s.setSaveTimer(Integer.parseInt(txfSaveTimer.getText()));
		} catch (IllegalInputException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			txfSaveTimer.setBackground(Color.PINK);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Du måste skriva in ett heltal", "Error", JOptionPane.ERROR_MESSAGE);
			txfSaveTimer.setBackground(Color.PINK);
		}

		//Sets the save path or sends an error message to the user.
		try {
			s.setSavePath(this.savePath);
		} catch (IllegalInputException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		//Sets the current yoda
		mf.setCurrentYoda(currentYoda);
		s.setCurrentYoda(currentYoda);

		//Sets if baby yoda should be shown or not
		mf.setShouldShowBabyYoda(cBoxShouldShowBabyYoda.isSelected());
		mf.setBabyYoda(cBoxShouldShowBabyYoda.isSelected());

		s.setShouldShowYoda(cBoxShouldShowBabyYoda.isSelected());

		//Sets the settings and save them
		mf.setSettings(s);
		mf.saveSettings();

		//Repaint
		mf.repaint();

		//Message to user
		JOptionPane.showMessageDialog(this, "Du har uppdaterat dina inställningar", "Uppdaterad",
				JOptionPane.INFORMATION_MESSAGE);

		//Closes this frame
		this.dispose();
	}

	/**
	 * Deletes all the data
	 * */
	private void deleteData() {
		//Ask user if the user wants to delete all data
		int prompt = JOptionPane.showConfirmDialog(this,"Är du säker på att du vill tabort all data", "Är du säker?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		//If yes then deletes
		if (prompt == JOptionPane.YES_OPTION) {
			mf.updateGradeState(State.NOTHING_SELECTED);
			mf.updateGradePanel();
			mf.setMainData(new Data(new ArrayList<SchoolClass>()));
			mf.saveData(mf.getSaveFilePath());
			JOptionPane.showMessageDialog(this, "All data är raderad!", "Raderingen lyckades", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * A setter for isEasterEggOpened
	 *
	 * @param isEasterEggOpened if the easter egg is opened or not.
	 */
	public void setEasterEggOpened(boolean isEasterEggOpened) {
		this.isEasterEggOpened = isEasterEggOpened;
	}
}
