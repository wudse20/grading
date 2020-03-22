package com.te3.main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.te3.main.objects.Criteria;
import com.te3.main.objects.Task;

public class MainAssignmentPanel extends JPanel {

    /**
     * Generated
     */
    private static final long serialVersionUID = 3651008258198927088L;

    ArrayList<Criteria> courseCriteria;
    ArrayList<Criteria> addedCriteria;

    BorderLayout layout = new BorderLayout();

    MainFrame mf;

    Task t;

    JLabel lblCriteria = new JLabel("Välj Kunskapskrav: ");
    JLabel lblSpacer = new JLabel(" ");

    JList<Criteria> listCourseCriteria = new JList<Criteria>();
    JList<Criteria> listAddedCriteria = new JList<Criteria>();

    JScrollPane scrCourseCriteria = new JScrollPane(listCourseCriteria);
    JScrollPane scrAddedCriteria = new JScrollPane(listAddedCriteria);

    /**
     * For adding
     *
     * @param mf the instance of the MainFrame
     */
    public MainAssignmentPanel(MainFrame mf) {
        this.mf = mf;

        this.courseCriteria = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(0).getCourses()
                .get(mf.getCurrentlySelectedCourseIndex()).getCourseCriteria();
        this.addedCriteria = new ArrayList<Criteria>();

        this.refreshLists();
        this.setProperties();
        this.addComponents();
    }

    /**
     * For editing
     * */
    public MainAssignmentPanel(MainFrame mf, Task t) {
        this.mf = mf;
        this.t = t;

        this.addedCriteria = t.getCriteria();
        this.courseCriteria = this.getRestOfCourseCriteria();

        this.refreshLists();
        this.setProperties();
        this.addComponents();
    }

    private void setProperties() {
        listAddedCriteria.setPreferredSize(new Dimension(250, 100));
        listCourseCriteria.setPreferredSize(new Dimension(250, 100));
        
        this.setLayout(layout);

        listAddedCriteria.getSelectionModel().addListSelectionListener((e) -> {
            updateCriteriaLists(false, listAddedCriteria.getSelectedIndex());
        });

        listCourseCriteria.getSelectionModel().addListSelectionListener((e) -> {
            updateCriteriaLists(true, listCourseCriteria.getSelectedIndex());
        });
    }

    private void addComponents() {
        this.add(lblCriteria, BorderLayout.PAGE_START);
        this.add(scrCourseCriteria, BorderLayout.LINE_START);
        this.add(scrAddedCriteria, BorderLayout.LINE_END);
        this.add(lblSpacer, BorderLayout.PAGE_END);
    }

    /**
     * Updates the criteria lists in the GUI
     *
     * @param isCourseCriteria <br>
     *                         if {@code true} -> the selection happened in the course criteria list <br>
     *                         if {@code false} -> the selection happened in the added criteria list
     * @param index            the selected index in the list, must be positive, else it returns.
     */
    private void updateCriteriaLists(boolean isCourseCriteria, int index) {
        if (index == -1) {
            return;
        }

        if (isCourseCriteria) {
            this.updateLists(this.courseCriteria, this.addedCriteria, this.courseCriteria.get(index));
        } else {
            this.updateLists(this.addedCriteria, this.courseCriteria, this.addedCriteria.get(index));
        }

        this.refreshLists();
    }

    /**
     * Moves an element, e, form arrList1 to arrList2.
     *
     * @param arrList1 the first array list, the one that's is the original host.
     * @param arrList2 the second array list, the one that gets the element.
     * @param e        the element that's being moved.
     */
    private <E> void updateLists(ArrayList<E> arrList1, ArrayList<E> arrList2, E e) {
        arrList2.add(e);
        int index = arrList1.indexOf(e);
        arrList1.remove(index);
    }

    /**
     * Refreshes the list with the current content of the ArrayLists.
     * */
    private void refreshLists() {
        Criteria[] addedCriteria = new Criteria[this.addedCriteria.size()];
        Criteria[] courseCriteria = new Criteria[this.courseCriteria.size()];

        listAddedCriteria.setListData(this.addedCriteria.toArray(addedCriteria));
        listCourseCriteria.setListData(this.courseCriteria.toArray(courseCriteria));
    }

    /**
     * Adds the not added criteria to the course.
     *
     * @return <br>
     *         if all the course criteria is added to the assignment, then it returns an empty ArrayList <br>
     *         else it will return the rest of the course criteria.
     * */
    private ArrayList<Criteria> getRestOfCourseCriteria() {
        ArrayList<Criteria> allCourseCriteria = mf.getMainData().getClasses().get(mf.getCurrentlySelectedClassIndex()).getStudents().get(0).getCourses().get(mf.getCurrentlySelectedCourseIndex()).getCourseCriteria();
        ArrayList<Criteria> rest = new ArrayList<Criteria>();

        if (allCourseCriteria.size() == addedCriteria.size()) {
            return new ArrayList<Criteria>();
        }

        for (Criteria c : allCourseCriteria) {
            if (!(addedCriteria.contains(c))) {
                rest.add(c);
            }
        }

        return rest;
    }

    public ArrayList<Criteria> getAddedCriteria() {
        return this.addedCriteria;
    }
}
