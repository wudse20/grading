package com.te3.main.gui;

import com.te3.main.exceptions.IllegalNameException;
import com.te3.main.objects.Course;
import com.te3.main.objects.SchoolClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;

public class LinkCoursesFrame extends JFrame implements WindowListener {

    //Instances
    MainSchoolClassPanel mscp;
    MainFrame mf;

    //CourseLists
    private ArrayList<Course> notAddedCourses = new ArrayList<Course>();
    private ArrayList<Course> addedCourses;

    //JLists
    JList<Course> jListNotAddedCourses = new JList<Course>();
    JList<Course> jListAddedCourses = new JList<Course>();

    //Lables
    JLabel lblAddedCourses = new JLabel("Tillagda Kurser:");
    JLabel lblNotAddedCourses = new JLabel("Ej Tillagda Kurser:");
    JLabel lblSpacer1 = new JLabel(" ");
    JLabel lblSpacer2 = new JLabel("    ");
    JLabel lblSpacer3 = new JLabel("    ");
    JLabel lblSpacer4 = new JLabel(" ");

    //ScrollPanes
    JScrollPane scrNotAddedCourses = new JScrollPane(jListNotAddedCourses);
    JScrollPane scrAddedCourses = new JScrollPane(jListAddedCourses);

    //Buttons
    JButton btnHelp = new JButton("?");
    JButton btnCancel = new JButton("Avbryt");
    JButton btnApply = new JButton("Verkställ");

    //Panels
    JPanel pLists = new JPanel();
    JPanel pLables = new JPanel();
    JPanel pListAndLables = new JPanel();
    JPanel pMain = new JPanel();
    JPanel pButtons = new JPanel();

    //Layouts
    BorderLayout layout = new BorderLayout();
    BorderLayout pMainLayout = new BorderLayout();
    BorderLayout pListLayout = new BorderLayout();
    BorderLayout pLablesLayout = new BorderLayout();
    BorderLayout pListAndLablesLayout = new BorderLayout();

    FlowLayout pButtonsLayout = new FlowLayout(FlowLayout.RIGHT);

    /**
     * @param mscp the MainSchoolClassPanel's instance
     * @param addedCourses the already linked courses, {@code null} if no courses added.
     */
    public LinkCoursesFrame(MainSchoolClassPanel mscp, ArrayList<Course> addedCourses, MainFrame mf) {
        //Sets the title through the super constructor
        super("Länka Kurser");

        //Stores the instances
        this.mscp = mscp;
        this.addedCourses = (addedCourses == null) ? new ArrayList<Course>() : addedCourses;
        this.mf = mf;

        //Sets some properties
        this.setSize(new Dimension(600, 450));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(layout);

        //Grabs the data
        this.grabDataFromMainFrame();

        //Fills the lists
        this.fillCourseList();

        //Sets properties of the JLists
        jListNotAddedCourses.setPreferredSize(new Dimension(250, 100));
        jListAddedCourses.setPreferredSize(new Dimension(250, 100));

        //Adds listeners
        btnCancel.addActionListener(e -> this.btnCancelPressed());
        btnHelp.addActionListener(e -> new HelpFrame("Title", "Text", HelpFrame.DEFAULT_HEIGHT, HelpFrame.DEFAULT_HEIGHT));
        btnApply.addActionListener(e -> this.btnApplyPressed());

        jListAddedCourses.addListSelectionListener(e -> {
            //Stores the index
            int index = jListAddedCourses.getSelectedIndex();

            //Checks that an actual item is selected
            if (index != -1) {
                //Moves the item
                this.moveItem(this.addedCourses, this.notAddedCourses, this.addedCourses.get(index));

                //Updates the GUI
                this.fillCourseList();
            }
        });

        jListNotAddedCourses.addListSelectionListener(e -> {
            //Stores the index
            int index = jListNotAddedCourses.getSelectedIndex();

            //Checks that an actual item is selected
            if (index != -1) {
                //Moves the item
                this.moveItem(this.notAddedCourses, this.addedCourses, this.notAddedCourses.get(index));

                //Updates the GUI
                this.fillCourseList();
            }
        });

        //pLables
        //Sets layout manager
        pLables.setLayout(pLablesLayout);

        //Adds components
        pLables.add(lblNotAddedCourses, BorderLayout.LINE_START);
        pLables.add(lblAddedCourses, BorderLayout.LINE_END);

        //pLists
        //Sets layout manager
        pLists.setLayout(pListLayout);

        //adds components
        pLists.add(scrNotAddedCourses, BorderLayout.LINE_START);
        pLists.add(scrAddedCourses, BorderLayout.LINE_END);

        //pListAndLables
        //Sets layout manager
        pListAndLables.setLayout(pListAndLablesLayout);

        //Adds components
        pListAndLables.add(pLables, BorderLayout.PAGE_START);
        pListAndLables.add(pLists, BorderLayout.CENTER);

        //pButtons
        //Sets the layout manager
        pButtons.setLayout(pButtonsLayout);

        //Adds components
        pButtons.add(btnHelp);
        pButtons.add(btnCancel);
        pButtons.add(btnApply);

        //pMain
        //Sets layout manager
        pMain.setLayout(pMainLayout);

        //Adds components
        pMain.add(pListAndLables, BorderLayout.CENTER);
        pMain.add(pButtons, BorderLayout.PAGE_END);

        //Adds components to the frame
        this.add(lblSpacer1, BorderLayout.PAGE_START);
        this.add(lblSpacer2, BorderLayout.LINE_START);
        this.add(pMain, BorderLayout.CENTER);
        this.add(lblSpacer3, BorderLayout.LINE_END);
        this.add(lblSpacer4, BorderLayout.PAGE_END);
    }

    private void btnApplyPressed() {
        //Handles errors
        //If there are no courses added, then debug message, message to user and it skips the rest of the method.
        if (addedCourses.size() == 0) {
            //Debug message:
            System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] LinkCoursesFrame: No added courses, skipping rest of the operations...");

            //Message to user
            JOptionPane.showMessageDialog(this, "Du har inga kurser valda; vill du länka kurser, öppna rutan igen och välj dem!", "Inga valda kurser!", JOptionPane.INFORMATION_MESSAGE);

            //Closes the frame
            this.dispose();

            //Returns
            return;
        }

        //The list that it will set with the new courses
        //These courses will be cloned.
        ArrayList<Course> courses = new ArrayList<Course>();

        //Loops through the added courses
        for (int i = 0; i < addedCourses.size(); i++) {
            try {
                //Adds a new course that's a blank clone the course to the list.
                courses.add(new Course(addedCourses.get(i).getName(), addedCourses.get(i).getCourseCriteria(), addedCourses.get(i).getNewCourseTask()));

                //Debug message
                System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] LinkCoursesFrame: Added a blank Course to the list with name: " + courses.get(i).getName());
            } catch (IllegalNameException e) {
                e.printStackTrace();
            }
        }

        //Sets MainSchoolClassPanel's courses to courses
        this.mscp.setLinkedCourses(courses);

        //Message to user
        JOptionPane.showMessageDialog(this, "Du har uppdaterat klassens kurser!", "Uppdatering", JOptionPane.INFORMATION_MESSAGE);

        //Debug message:
        System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] LinkCoursesFrame: has updated MainSchoolClassPanel");

        //Closes the frame
        this.dispose();
    }

    /**
     * Moves the items between the different ArrayLists
     *
     * @param host the list that gives an item
     * @param receiver the list that gets an item
     * @param item the items that's being moved
     */
    private void moveItem(ArrayList<Course> host, ArrayList<Course> receiver, Course item) {
        //Removes the item from the host
        host.remove(item);

        //Adds the item to the receiver
        receiver.add(item);

        //Debug message:
        System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] LinkCoursesFrame: Move an item");
    }

    /**
     * Grabs data from MainFrame
     */
    private void grabDataFromMainFrame() {
        //Gets all the courses and stores them in a list.
        ArrayList<SchoolClass> classes = mf.getMainData().getClasses();

        //Loops through the classes
        for (var i = 0; i < classes.size(); i++) {
            //Gets the courses
            var courses = classes.get(i).getStudents().get(0).getCourses();

            for (var j = 0; j < courses.size(); j++) {
                //Gets the course
                Course c = null;
                try {
                    c = new Course(courses.get(j).getName(), courses.get(j).getCourseCriteria(), courses.get(j).getCourseTasks());
                } catch (IllegalNameException e) {
                    e.printStackTrace();
                }

                //Adds the courses to the list if they aren't in the added courses list
                if (!this.addedCourses.contains(c)) {
                    //Adds the course
                    this.notAddedCourses.add(c);

                    //Debug message:
                    System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] LinkedCoursesFrame: Added course: " + c.getName() + ", to not added courses.");
                } else {
                    //Debug message:
                    System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] LinkedCoursesFrame: " + c.getName() + " is in the added courses list.");
                }
            }
        }
    }

    /**
     * Fills the JLists, with the information
     */
    private void fillCourseList() {
        //Sets the list data
        Course[] added = new Course[this.addedCourses.size()];
        Course[] notAdded = new Course[this.notAddedCourses.size()];
        jListAddedCourses.setListData(this.addedCourses.toArray(added));
        jListNotAddedCourses.setListData(this.notAddedCourses.toArray(notAdded));
    }

    /**
     * Called when the cancel button is pressed
     */
    private void btnCancelPressed() {
        //Double checks with the user.
        int ans = JOptionPane.showConfirmDialog(this, "Är du säker på att du vill avbryta?", "Avbryt", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (ans == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }


    /**
     * Getter for added courses
     *
     * @return the added courses
     */
    public ArrayList<Course> getAddedCourses() {
        return this.addedCourses;
    }

    /**
     * Overrides {@link JFrame#dispose() this method}<br>
     *
     * This will also tell the program that this window is closed.
     */
    @Override
    public void dispose() {
        //Tells the program that the window is closed
        mscp.setCourseLinkerOpened(false);

        //Closes the window
        super.dispose();
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
        this.dispose();
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
