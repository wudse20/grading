package com.te3.main;

import javax.swing.SwingUtilities;

import com.te3.main.gui.MainFrame;

/**
 * Main class
 */
public class Betygsystem {
	public static void main(String[] args) {
		// Initializes the frame
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}
