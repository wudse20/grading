package com.te3.main.gui;

import com.te3.main.enums.State;
import com.te3.main.objects.SearchResult;

import javax.swing.*;
import java.awt.*;


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

    public OverviewFrame(SearchResult result) {
        //Sets some properties
        this.setSize(new Dimension(750, 750));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Stores the instance
        this.result = result;

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
}
