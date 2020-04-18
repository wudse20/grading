package com.te3.main.gui.easteregg.GUI;

import com.te3.main.gui.easteregg.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
    /** True if game is running*/
    private boolean isGameRunning = false;

    /** If true the button is being hovered at that index */
    private boolean[] hoveredButtons = new boolean[] {false, false, false};

    /** The number of computer wins*/
    private int cWins = 0;
    /** The number of player wins*/
    private int pWins = 0;
    /** The spacing between the buttons */
    private int space;
    /** The side of the button squares */
    private int squareSideLength;
    /** The counter for color change to lower the amount of blinking */
    private int colorChangeCount = 0;

    /**
     * Constructor
     */
    public GamePanel() {}

    /**
     * Runs a round
     *
     * @return {@code true} if player wins else {@code false}
     * */
    private boolean round(Move pMove) {
        Move cMove;

        /*
         * While there moves are the same the computer will
         * try again until they differ, since draws are for
         * losers according to this program.
         * */
        do {
            cMove = Move.values()[new Random().nextInt(3)];
        } while (cMove.equals(pMove));

        switch(pMove)
        {
            case ROCK:
                return !(cMove.equals(Move.PAPER));
            case PAPER:
                return !(cMove.equals(Move.SCISSORS));
            case SCISSORS:
                return !(cMove.equals(Move.ROCK));
            default:
                return true;
        }
    }

    /**
     * Draws the buttons
     *
     * @param g the graphics object in use
     */
    private void drawButtons(Graphics2D g) {
        //For loop that draws the each button
        for (int i = 0; i < 3; i++) {
            //Calculates position and size and padding
            int x = space + squareSideLength * i + space * i;
            int y = space;
            int btnPadding = squareSideLength / 10;

            //Sets the color based on if it's being hovered over or not.
            if (this.hoveredButtons[i]) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.red);
            }


            //Sets the line width
            g.setStroke(new BasicStroke(1));

            //Draws the rectangles
            g.drawRect(x, y, squareSideLength, squareSideLength);

            //Draws the icons
            if (i == 0) {
                //Draws the stone Icon in the button
                this.drawStone(g, x + btnPadding, y + btnPadding, (squareSideLength - (2 * btnPadding)));
            } else if (i == 1) {
                //Draws the scissors
                this.drawScissors(g, x + btnPadding, y + btnPadding, squareSideLength - (2 * btnPadding), squareSideLength - (2 * btnPadding));
            } else {
                //Draws the stone
                this.drawPaper(g, x + btnPadding, y + btnPadding, squareSideLength - (2 * btnPadding), squareSideLength - (2 * btnPadding));
            }
        }
    }

    /**
     * Draws the paper
     *
     * @param g the graphics object that draws
     * @param x the x pos
     * @param y the y pos
     * @param width the width
     * @param height the height
     */
    private void drawPaper(Graphics2D g, int x, int y, int width, int height) {
        //Sets the color
        g.setColor(Color.WHITE);

        //Draws the paper
        g.fillRect(x + width / 10, y, (4 * width) / 5, height);

        //Draws some lines with a for loop
        for (var i = 1; i <= 4; i++) {
            //Sets the color
            g.setColor(Color.LIGHT_GRAY);

            //Sets the width of the line
            g.setStroke(new BasicStroke(1.5F));

            //Draws the line
            g.drawLine(x + width / 5, y + i * (height / 5), x + (3 * width) / 5, y + i * (height / 5));
        }
    }

    /**
     * Draws the scissors
     *
     * @param g the graphics object in use
     * @param x the x pos
     * @param y the y pos
     * @param width the width
     * @param height the height
     */
    private void drawScissors(Graphics2D g, int x, int y, int width, int height) {
        //Sets the color
        g.setColor(Color.GRAY);

        //Sets the line width
        g.setStroke(new BasicStroke(6));

        //Draws the cutty bits
        g.drawLine(x + width / 4, y + height / 3, x + (3 * width) / 4, y);
        g.drawLine(x + (3 * width) / 4, y + height / 3, x + width / 4, y);

        //Sets color
        g.setColor(Color.BLUE);

        //Draws the handel
        g.drawOval(x, y + height / 3, width / 2, (int) (height / 1.4F));
        g.drawOval(x + width / 2, y + height / 3, width / 2, (int) (height / 1.4F));
    }

    /**
     * Draws the stone. I know it looks like a black and white eye.
     *
     * @param g the current GraphicsObject that draws
     * @param x the x pos
     * @param y the y pos
     * @param r the radius of the circle
     */
    private void drawStone(Graphics2D g, int x, int y, int r) {
        //Sets color
        g.setColor(Color.GRAY);

        //draws stone
        g.fillOval(x, y, r, r);

        //Sets color
        g.setColor(Color.DARK_GRAY);

        //draws stone
        g.fillOval(x * 2, (int) (y * 1.5F), r / 2, (int) (r / 1.3F));

        //Sets color
        g.setColor(Color.LIGHT_GRAY);

        //draws stone
        g.fillOval((int) (x * 2.5F), y * 3, r / 4, r / 4);
    }

    /**
     * Check if a button is clicked and acts
     * on it.
     *
     * @param x the x pos of the mouse click
     * @param y the y pos of the mouse click
     */
    private void detectButtonPress(int x, int y) {
        /*
         * Checks which button is pressed on mouse click
         * wont do anything if it's pressed outside of
         * the buttons. Will only do it if the game is
         * running at the moment.
         *
         * It will also act on the click and play a round.
         * */
        if (((x > space && x < space + squareSideLength) && (y > space && y < space + squareSideLength)) && isGameRunning) {
            if (round(Move.ROCK)) {
                pWins++;
            } else {
                cWins++;
            }

            this.repaint();
        } else if (((x > space * 2 + squareSideLength && x < space * 2 + squareSideLength * 2) && (y > space && y < space + squareSideLength)) && isGameRunning) {
            if (round(Move.SCISSORS)) {
                pWins++;
            } else {
                cWins++;
            }

            this.repaint();
        } else if (((x > space * 3 + squareSideLength * 2 && x < space * 3 + squareSideLength * 3) && (y > space && y < space + squareSideLength)) && isGameRunning) {
            if (round(Move.PAPER)) {
                pWins++;
            } else {
                cWins++;
            }

            this.repaint();
        }
    }

    /**
     * Tells the program to highlight the hovered button.
     *
     * @param x the x pos of the mouse position
     * @param y the y pos of the mouse position
     */
    private void hoverEffect(int x, int y) {
        /*
         * Checks if the mouse i hovering over a button.
         * If it is then it will tell the program that
         * and then repaint. Will only do it if the game
         * is running at the moment.
         * */
        if (((x > space && x < space + squareSideLength) && (y > space && y < space + squareSideLength)) && isGameRunning) {
            this.hoveredButtons = new boolean[] {true, false, false};
            this.repaint();
        } else if (((x > space * 2 + squareSideLength && x < space * 2 + squareSideLength * 2) && (y > space && y < space + squareSideLength)) && isGameRunning) {
            this.hoveredButtons = new boolean[] {false, true, false};
            this.repaint();
        } else if (((x > space * 3 + squareSideLength * 2 && x < space * 3 + squareSideLength * 3) && (y > space && y < space + squareSideLength)) && isGameRunning) {
            this.hoveredButtons = new boolean[] {false, false, true};
            this.repaint();
        } else if (this.isGameRunning) {
            this.hoveredButtons = new boolean[] {false, false, false};
            this.repaint();
        }
    }

    @Override
    public void paintComponent(Graphics gOld) {
        //Uses the newer and fancy Graphics lib.
        Graphics2D g = (Graphics2D) gOld;

        //Fills Background
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(), this.getHeight());

        /*
         * If the game should run it runs it,
         * else it will show the start message.
         * */
        if (!isGameRunning) {
            //Sets start text to the screen
            g.setColor(Color.GREEN);
            g.setFont(new Font("TimesRoman", Font.BOLD, 16));

            //Figures the string's width out.
            String text = "Tryck på 'S' för att starta spelet!";
            int width = g.getFontMetrics().stringWidth(text);

            //Draws the actual String
            g.drawString("Tryck på 'S' för att starta spelet!", (this.getWidth() / 2) - (width / 2), this.getHeight() / 2);
        } else {
            //Button calculations
            //Calculates the space
            space = this.getWidth() / 25;

            //Calculates the side length of the squares
            squareSideLength = (this.getWidth() - space * 4) / 3;

            //Draws the buttons
            drawButtons(g);

            //Changes color
            g.setColor(Color.YELLOW);

            //Sets the font
            g.setFont(new Font("TimesRoman", Font.BOLD, 16));

            //Draws the strings
            g.drawString("Player: " + pWins, space, space * 3 + squareSideLength);
            g.drawString("Computer: " + cWins, space, space * 4 + squareSideLength);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        //Sets the game to run if S key is pressed and repaints.
        if (e.getKeyCode() == 83 && !isGameRunning) {
            isGameRunning = true;
            this.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        this.detectButtonPress(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.hoverEffect(e.getX(), e.getY());
    }
}