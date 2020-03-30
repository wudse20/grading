package com.te3.main.gui;

import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {
    /** Default */
    private static final long serialVersionUID = 1L;

    /** The width of the graph */
    private final int WIDTH = 182;

    /** The height of the graph */
    private final int HEIGHT = 125;

    /** The left margin of the graph */
    private final int MARGIN_LEFT = 5;

    /** The top margin of the graph */
    private final int MARGIN_TOP = 100;

    /** The space between the y-axis and the first pillar */
    private final int GRAPH_AXIS_PILLAR_MARGIN = 32;

    private int f;
    private int e;
    private int c;
    private int a;

    /**
     * Draws a graph with theses numbers.
     *
     * @param f the number of f:s
     * @param e the number of e:s
     * @param c the number of c:s
     * @param a the number of a:s
     */
    public GraphPanel(int f, int e, int c, int a) {
        this.f = f;
        this.e = e;
        this.c = c;
        this.a = a;
    }

    @Override
    public void paintComponent(Graphics gOld) {
        //Uses the new and bigger library for graphics
        Graphics2D g = (Graphics2D) gOld;

        //Calculates the total amount of grades
        double criteriaTotal = f + e + c + a;

        //Calculates the height of each pillar.
        int fHeight = (int) ((f / criteriaTotal) * HEIGHT);
        int eHeight = (int) ((e / criteriaTotal) * HEIGHT);
        int cHeight = (int) ((c / criteriaTotal) * HEIGHT);
        int aHeight = (int) ((a / criteriaTotal) * HEIGHT);

        //Sets the line width
        g.setStroke(new BasicStroke(20));

        //The extra diff needed to make everything look good.
        int diff = 10;

        //Draws the F-pillar

        //Sets the color
        g.setColor(Color.RED);

        if (f != 0) {
            //Draws the line
            g.drawLine(MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN, MARGIN_TOP + HEIGHT - diff, MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN, MARGIN_TOP + HEIGHT - fHeight + diff);
        }

        //Draws the E-pillar

        //Sets the color
        g.setColor(Color.YELLOW);

        if (e != 0) {
            //Draws the line
            g.drawLine((MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) * 2, MARGIN_TOP + HEIGHT - diff, (MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) * 2, MARGIN_TOP + HEIGHT - eHeight + diff);
        }

        //Draws the C-Pillar

        //Sets the color
        g.setColor(new Color(189, 124, 4));

        if (c != 0) {
            //Draws the line
            g.drawLine((MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) * 3, MARGIN_TOP + HEIGHT - diff, (MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) * 3, MARGIN_TOP + HEIGHT - cHeight + diff);
        }

        //Draws the A-Pillar

        //Sets the color
        g.setColor(Color.GREEN);

        if (a != 0) {
            //Draws the line
            g.drawLine((MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) * 4, MARGIN_TOP + HEIGHT - diff, (MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) * 4, MARGIN_TOP + HEIGHT - aHeight + diff);
        }

        //Labels Graph

        //The letter diffs
        int letterDiffY = 15;
        float letterDiffX = 3.5F;

        //Sets size
        g.setFont(new Font("TimesRoman", Font.BOLD, 12));

        //Sets color
        g.setColor(Color.BLACK);

        //Draws letter F
        g.drawString("F", (MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) - letterDiffX, MARGIN_TOP + HEIGHT + letterDiffY);

        //Draws letter E
        g.drawString("E", (MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) * 2 - letterDiffX, MARGIN_TOP + HEIGHT + letterDiffY);

        //Draws letter C
        g.drawString("C", (MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) * 3 - letterDiffX, MARGIN_TOP + HEIGHT + letterDiffY);

        //Draws letter A
        g.drawString("A", (MARGIN_LEFT + GRAPH_AXIS_PILLAR_MARGIN) * 4 - letterDiffX, MARGIN_TOP + HEIGHT + letterDiffY);

        //Sets line width
        g.setStroke(new BasicStroke(2.25f));

        //Draws X-Axis
        g.drawLine(MARGIN_LEFT, HEIGHT + MARGIN_TOP, WIDTH + MARGIN_LEFT, HEIGHT + MARGIN_TOP);

        //Draws Y-Axis
        g.drawLine(MARGIN_LEFT, MARGIN_TOP, MARGIN_LEFT, HEIGHT + MARGIN_TOP);
    }
}
