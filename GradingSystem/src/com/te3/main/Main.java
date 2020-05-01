package com.te3.main;

import com.te3.main.gui.MainFrame;

import javax.swing.*;

/**
 * Main class
 */
public class Main {
	public static void main(String[] args) {
		// Initializes the frame
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}
