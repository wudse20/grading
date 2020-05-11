package com.te3.main.gui;

import com.te3.main.objects.SearchResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalTime;

/**
 * The frame responsible for searching.
 * */
public class SearchFrame extends JFrame implements WindowListener {

    /** The instance of the MainFrame */
    MainFrame mf;

    /** Tbe instance of the GradePanel*/
    ButtonPanel bp;

    //TextFields
    JTextField txfInput = new JTextField(20);

    //JLists
    JList<SearchResult> resultList = new JList<SearchResult>();

    //JScrollPane
    JScrollPane scrResultList = new JScrollPane(resultList);

    //Buttons
    JButton btnCancel = new JButton("Avbryt");
    JButton btnHelp = new JButton("?");

    //Panels
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    //Layouts
    BorderLayout layout = new BorderLayout();
    BorderLayout pMainLayout = new BorderLayout();

    FlowLayout pBtnLayout = new FlowLayout(FlowLayout.RIGHT);

    /**
     * Creates the SearchFrame
     *
     * @param mf The instance of the MainFrame
     * @param bp The instance of the ButtonPanel
     */
    public SearchFrame(MainFrame mf, ButtonPanel bp) {
        //Sets the title through the super constructor
        super("Sök efter en elev");

        //Sets some properties
        this.setSize(new Dimension(400, 300));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Stores instances
        this.mf = mf;
        this.bp = bp;

        //Adds listeners
        this.addWindowListener(this);
    }

    private void closeWindow() {
        //Sets the status to the ButtonPanel
        bp.setSearchWindowOpened(false);

        //Sends debug message
        System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] SearchFrame: Closing");

        //Disposes this window
        this.dispose();
    }

    /**
     * Invoked the first time a window is made visible.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Invoked when the user attempts to close the window
     * from the window's system menu.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        //Closes the window
        this.closeWindow();
    }

    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowClosed(WindowEvent e) {

    }

    /**
     * Invoked when a window is changed from a normal to a
     * minimized state. For many platforms, a minimized window
     * is displayed as the icon specified in the window's
     * iconImage property.
     *
     * @param e the event to be processed
     * @see Frame#setIconImage
     */
    @Override
    public void windowIconified(WindowEvent e) {

    }

    /**
     * Invoked when a window is changed from a minimized
     * to a normal state.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    /**
     * Invoked when the Window is set to be the active Window. Only a Frame or
     * a Dialog can be the active Window. The native windowing system may
     * denote the active Window or its children with special decorations, such
     * as a highlighted title bar. The active Window is always either the
     * focused Window, or the first Frame or Dialog that is an owner of the
     * focused Window.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowActivated(WindowEvent e) {

    }

    /**
     * Invoked when a Window is no longer the active Window. Only a Frame or a
     * Dialog can be the active Window. The native windowing system may denote
     * the active Window or its children with special decorations, such as a
     * highlighted title bar. The active Window is always either the focused
     * Window, or the first Frame or Dialog that is an owner of the focused
     * Window.
     *
     * @param e the event to be processed
     */
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
