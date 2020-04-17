package com.te3.main.easteregg.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * The class that holds the main frame
 */
public class GameFrame extends JFrame {
    GamePanel gp = new GamePanel();

    public GameFrame() {
        //Calls the super constructor
        super("Sten, Sax, Påse");

        //Sets properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 300));

        //Adds components
        this.getContentPane().add(gp);

        //Adds listeners
        this.addKeyListener(gp);
        this.addMouseListener(gp);
        this.addMouseMotionListener(gp);
    }
}
