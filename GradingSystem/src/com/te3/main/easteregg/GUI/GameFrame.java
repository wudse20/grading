package com.te3.main.easteregg.GUI;

import com.te3.main.gui.SettingsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * The class that holds the main frame
 */
public class GameFrame extends JFrame implements WindowListener {
    GamePanel gp = new GamePanel();
    SettingsFrame sf;

    public GameFrame(SettingsFrame sf) {
        //Calls the super constructor
        super("Sten, Sax, Påse");

        //Stores instance
        this.sf = sf;

        //Sets properties
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(400, 300));

        //Adds components
        this.getContentPane().add(gp);

        //Adds listeners
        this.addKeyListener(gp);
        this.addMouseListener(gp);
        this.addMouseMotionListener(gp);
        this.addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) { }

    @Override
    public void windowClosing(WindowEvent e) {
        sf.setEasterEggOpened(false);
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) { }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
}
