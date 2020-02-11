package com.te3.main.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	/** Default */
	private static final long serialVersionUID = 1L;

	public MainFrame() 
	{
		super("Betygssättning");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(400, 300));
	}

}
