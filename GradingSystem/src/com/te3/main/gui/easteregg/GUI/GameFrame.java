package com.te3.main.gui.easteregg.GUI;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import com.te3.main.gui.SettingsFrame;

/**
 * The class that holds the main frame
 */
public class GameFrame extends JFrame implements WindowListener {
	/** Generated */
	private static final long serialVersionUID = -4356306834956152395L;

	// Panels
	GamePanel gp = new GamePanel();

	// Instances
	SettingsFrame sf;

	public GameFrame(SettingsFrame sf) {
		// Calls the super constructor
		super("Sten, Sax, Påse");

		// Stores instance
		this.sf = sf;

		// Sets properties
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(400, 300));

		// Adds components
		this.getContentPane().add(gp);

		// Adds listeners
		this.addKeyListener(gp);
		this.addMouseListener(gp);
		this.addMouseMotionListener(gp);
		this.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// Tells the program that the easter egg is closed
		sf.setEasterEggOpened(false);

		// Closes the easter egg
		this.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
