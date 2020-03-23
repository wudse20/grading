package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.te3.main.exceptions.IllegalInputException;
import com.te3.main.objects.Data;
import com.te3.main.objects.SchoolClass;
import com.te3.main.objects.Settings;

/**
 * The frame that holds all the settings
 *
 * @Author Anton Skorup
 * */
public class SettingsFrame extends JFrame {
	/** Generated */
	private static final long serialVersionUID = 2812677933749486134L;

	private String savePath;

	/** The help text of this frame */
	private String helpText = "För att ställa in hur ofta som <br>" + "programmet kommer att autospara <br>"
			+ "skriv in intervallet i sekunder. <br>" + "Detta måste vara mellan 60 och 3600 sekunder. <br><br>"
			+ "För att ställa in vart data skall sparas <br>" + "klicka på knappen: <b>Ställ in plats för sparfil</b>"
			+ "och följ instruktionerna";

	private MainFrame mf;

	private Thread threadSetSavePath;

	BorderLayout layout = new BorderLayout();
	BorderLayout pLayout = new BorderLayout();
	FlowLayout pBtnsLayout = new FlowLayout(FlowLayout.RIGHT);
	FlowLayout pTimerLayout = new FlowLayout(FlowLayout.LEFT);
	FlowLayout pSettingBtnsLayout = new FlowLayout(FlowLayout.LEFT);
	BoxLayout pSettingsLayout;

	Container cp = this.getContentPane();

	JButton btnApply = new JButton("Verkställ");
	JButton btnCancel = new JButton("Avbryt");
	JButton btnHelp = new JButton("?");
	JButton btnSetSavePath = new JButton("Ställ in plats för sparfil"); // Försök komma på bättre text för att knappen
	JButton btnDeletData = new JButton("Rensa all data");

	JLabel lblSpacer = new JLabel(" ");
	JLabel lblSpacer2 = new JLabel("     ");
	JLabel lblSpacer3 = new JLabel("     ");
	JLabel lblSpacer4 = new JLabel(" ");
	JLabel lblSpacer5 = new JLabel(" ");
	JLabel lblSpacer6 = new JLabel(" ");
	JLabel lblInfo = new JLabel("Inställningar: ");
	JLabel lblTimer = new JLabel("Sparintervall (s):");

	JTextField txfSaveTimer;

	JPanel panel = new JPanel();
	JPanel pBtns = new JPanel();
	JPanel pTimer = new JPanel();
	JPanel pSettingBtns = new JPanel();
	JPanel pSettings = new JPanel();

	/**
     * Sets everything up
     *
     * @param mf the instance of the MainFrame
     * */
	public SettingsFrame(MainFrame mf) {
		super("Inställningar");

		this.mf = mf;
		this.savePath = mf.getSaveFilePath();

		this.setProperties();
		this.addComponents();
	}

	/**
     * Adds the components
     * */
	private void addComponents() {
		this.add(lblSpacer, BorderLayout.PAGE_START);
		this.add(lblSpacer2, BorderLayout.LINE_START);
		this.add(panel, BorderLayout.CENTER);
		this.add(lblSpacer3, BorderLayout.LINE_END);
		this.add(lblSpacer4, BorderLayout.PAGE_END);
	}

	/**
     * Sets the properties
     * */
	private void setProperties() {
		this.setLayout(layout);
		this.setSize(new Dimension(400, 225));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		txfSaveTimer = new JTextField(12);
		txfSaveTimer.setText(Integer.toString(mf.getSaveTimer()));
		txfSaveTimer.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				txfSaveTimer.setBackground(Color.WHITE);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				txfSaveTimer.setBackground(Color.WHITE);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		btnApply.addActionListener((e) -> applySettings());
		btnCancel.addActionListener((e) -> this.dispose());
		btnHelp.addActionListener(
				(e) -> new HelpFrame("Inställningar", "<html><p>" + helpText + "</p></html>").setVisible(true));
		btnSetSavePath.addActionListener((e) -> setSavePath());
		btnDeletData.addActionListener((e) -> deleteData());

		pTimer.setLayout(pTimerLayout);
		pTimer.add(lblTimer);
		pTimer.add(txfSaveTimer);

		pSettingBtns.setLayout(pSettingBtnsLayout);
		pSettingBtns.add(btnSetSavePath);
		pSettingBtns.add(btnDeletData);

		pSettingsLayout = new BoxLayout(pSettings, BoxLayout.Y_AXIS);
		pSettings.setLayout(pSettingsLayout);
		pSettings.add(lblSpacer5);
		pSettings.add(pTimer);
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
	 * Not a setter
	 */
	private void setSavePath() {
		FileSystemFrame fsf = new FileSystemFrame(
				this.savePath.substring(this.savePath.lastIndexOf('/') + 1, this.savePath.lastIndexOf('.')), "xml");
		fsf.setVisible(true);

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
		while (fsf.getExitCode() == -1) {
			try {
				threadSetSavePath.sleep(450);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (fsf.getExitCode() == 0) {
			this.savePath = fsf.getFilePath();
			System.out.println(this.savePath);
		}

		fsf.close();
	}

	/**
     * Applies the settings
     * */
	private void applySettings() {
		Settings s = new Settings();

		try {
			s.setSaveTimer(Integer.parseInt(txfSaveTimer.getText()));
		} catch (IllegalInputException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			txfSaveTimer.setBackground(Color.PINK);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Du måste skriva in ett heltal", "Error", JOptionPane.ERROR_MESSAGE);
			txfSaveTimer.setBackground(Color.PINK);
		}

		try {
			s.setSavePath(this.savePath);
		} catch (IllegalInputException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		mf.setSettings(s);
		mf.saveSettings();

		JOptionPane.showMessageDialog(this, "Du har uppdaterat dina inställningar", "Uppdaterad",
				JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
	}

	/**
	 * Deletes all the data
	 * */
	private void deleteData() {
		int prompt = JOptionPane.showConfirmDialog(this,"Är du säker på att du vill tabort all data", "Är du säker?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (prompt == JOptionPane.YES_OPTION) {
			mf.setMainData(new Data(new ArrayList<SchoolClass>()));
			mf.saveData(mf.getSaveFilePath());
			mf.updateGUI();
			JOptionPane.showMessageDialog(this, "All data är raderad!", "Raderingen lyckades", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
