package com.te3.main.gui;

import com.te3.main.enums.State;
import com.te3.main.objects.Course;
import com.te3.main.objects.SearchResult;
import com.te3.main.objects.Task;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;


public class OverviewFrame extends JFrame {

    /** The state of the information shown, to be feed to the grade panels */
    private State s = State.CLASS_COURSE_STUDENT;

    /** The result of the search */
    private SearchResult result;

    //RadioButtons
    JRadioButton radioClassCourseStudent = new JRadioButton("Kurs vy");
    JRadioButton radioClassCourseStudentTask = new JRadioButton("Uppgifts vy");

    //ButtonGroup
    ButtonGroup bgViewSelectors = new ButtonGroup();

    //Buttons
    JButton btnClose = new JButton("Stäng");
    JButton btnHelp = new JButton("?");

    //Lables
    JLabel lblName = new JLabel();
    JLabel lblSpacer1 = new JLabel("<html><p>&emsp;&emsp;</p></html>");
    JLabel lblSpacer2 = new JLabel(" ");
    JLabel lblSpacer3 = new JLabel("   ");
    JLabel lblSpacer4 = new JLabel(" ");

    //Panels
    JPanel mainPanel = new JPanel();
    JPanel inputPanel = new JPanel();
    JPanel gradesPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel buttonPanel2 = new JPanel();

    //Layouts
    BorderLayout layout = new BorderLayout();
    BorderLayout pMainLayout = new BorderLayout();

    FlowLayout pInputLayout = new FlowLayout(FlowLayout.CENTER);
    FlowLayout pButtonsLayout = new FlowLayout(FlowLayout.RIGHT);

    BoxLayout pGradesLayout = new BoxLayout(gradesPanel, BoxLayout.Y_AXIS);
    BoxLayout pButtons2Layout = new BoxLayout(buttonPanel2, BoxLayout.Y_AXIS);

    //ScrollPanes
    JScrollPane scrGrades = new JScrollPane(gradesPanel);

    //Instances
    MainFrame mf;

    public OverviewFrame(SearchResult result, MainFrame mf) {
        //Sets some properties
        this.setSize(new Dimension(1200, 750));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Stores the instances
        this.result = result;
        this.mf = mf;

        //Sets the title of the frame
        this.setTitle(result.toString());

        //Sets text of the label
        lblName.setText(result.toString());

        //Adds the radio buttons to the button group
        bgViewSelectors.add(radioClassCourseStudent);
        bgViewSelectors.add(radioClassCourseStudentTask);

        //Sets default radio settings
        radioClassCourseStudent.setSelected(true);
        radioClassCourseStudentTask.setSelected(false);

        //Sets the font of label
        lblName.setFont(new Font(Font.DIALOG, Font.BOLD, 30));

        //Sets the font of the radio buttons
        radioClassCourseStudent.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        radioClassCourseStudentTask.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));

        //Adds listeners
        btnClose.addActionListener(e -> this.dispose());

        radioClassCourseStudent.addActionListener(e -> this.s = State.CLASS_COURSE_STUDENT);
        radioClassCourseStudentTask.addActionListener(e -> this.s = State.CLASS_COURSE_STUDENT_TASK);

        //Handling Grades Layout
        this.handleGradePanel(s, this.result);

        //inputPanel
        //Sets the layout
        inputPanel.setLayout(pInputLayout);

        //Adds components
        inputPanel.add(lblName);
        inputPanel.add(lblSpacer1);
        inputPanel.add(radioClassCourseStudent);
        inputPanel.add(radioClassCourseStudentTask);

        //buttonPanel
        //Sets the layout
        buttonPanel.setLayout(pButtonsLayout);

        //Adds components
        buttonPanel.add(btnHelp);
        buttonPanel.add(btnClose);

        //buttonPanel2
        //Sets the layout
        buttonPanel2.setLayout(pButtons2Layout);

        //Adds components
        buttonPanel2.add(lblSpacer4);
        buttonPanel2.add(buttonPanel);

        //Main Panel
        //Sets the layout
        mainPanel.setLayout(pMainLayout);

        //Adds Components
        mainPanel.add(inputPanel, BorderLayout.PAGE_START);
        mainPanel.add(scrGrades, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        //The Frame
        //Sets the layout manager
        this.setLayout(layout);

        //Adds the components
        this.add(lblSpacer2, BorderLayout.LINE_START);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(lblSpacer3, BorderLayout.LINE_END);
    }

    /**
     * @param s The state of this GUI
     * @param res The search result
     */
    private void handleGradePanel(State s, SearchResult res) {
        //Removes the old components
        for (Component c : gradesPanel.getComponents())
            gradesPanel.remove(c);

        //Sets the layout of the panel
        gradesPanel.setLayout(pGradesLayout);

        //The count of courses and tasks
        int courseCount = 0, taskCount = 0;

        //All the courses in one list
        ArrayList<Course> courses = new ArrayList<Course>();

        //Loops through the found students
        for (int i = 0; i < res.getFoundStudents().size(); i++) {
            //Adds the amount of courses, i.e. the size of the courses list of each entry.
            courseCount += res.getFoundStudents().get(i).getCourses().size();

            //Adds the courses to the list
            for (int j = 0; j < res.getFoundStudents().get(i).getCourses().size(); j++) {
                //Adds the courses
                courses.add(res.getFoundStudents().get(i).getCourses().get(j));

                //Counts the tasks
                taskCount += res.getFoundStudents().get(i).getCourses().get(j).getCourseTasks().size();
            }
        }

        //Debug message:
        System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] OverviewFrame: The amount of courses is: " + courseCount);

        //Debug message:
        System.out.println("[" + ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour()) + ":" + ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute() : LocalTime.now().getMinute())+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond(): LocalTime.now().getSecond())+ "] OverviewFrame: The amount of tasks is: " + taskCount);

        if (s.equals(State.CLASS_COURSE_STUDENT)) {
            //Creates arrays of the different components.
            GradesPanel[] gps = new GradesPanel[courseCount];
            JLabel[] lblInfos = new JLabel[courseCount];

            //Loops through and adds components
            for (int i = 0; i < courseCount; i++) {
                //Defines the label
                lblInfos[i] = new JLabel();

                //Sets the text
                lblInfos[i].setText("Kurs: " + courses.get(i).getName());

                //Sets font
                lblInfos[i].setFont(new Font(Font.DIALOG, Font.BOLD, 35));

                //Defines the grade panel
                gps[i] = new GradesPanel(mf, true, courses.get(i).getCourseTasks(), null, res.getFoundStudents().get(0));

                //Sets the state
                gps[i].setState(s);

                //Updates the GUI
                gps[i].update(s, true);

                //Adds the components to the frame
                gradesPanel.add(lblInfos[i]);
                gradesPanel.add(gps[i]);
            }
        }
    }
}
