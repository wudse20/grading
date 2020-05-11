package com.te3.main.objects;

import java.util.ArrayList;

/**
 * A search result in the searching part of the program.
 */
public class SearchResult {
    /** The list of found students with that name */
    private ArrayList<Student> foundStudents;

    /**
     * Sets up the object by defining the found Students ArrayList
     */
    public SearchResult() {
        this.foundStudents = new ArrayList<Student>();
    }

    /**
     * Adds a student to the list.
     *
     * @param s the student to be added
     */
    public void addStudent(Student s) {
        this.foundStudents.add(s);
    }

    /**
     * A getter for the found students.
     *
     * @return the found students
     */
    public ArrayList<Student> getFoundStudents() {
        return this.foundStudents;
    }

    /**
     * Compares a string to this Object
     *
     * @param name the name of the student (not case sensitive)
     * @return if name is equal to the name of the students of this result true, else false. Are there no students it will return false.
     */
    public boolean compare(String name) {
        //If no students the return false
        if (this.foundStudents.size() == 0)
            return false;

        //Returns true if equal else false
        return (name.equalsIgnoreCase(this.foundStudents.get(0).getName()));
    }

    @Override
    public String toString() {
        /*
        * If there are students in the list it will return the name of the first student in the list. Which will be the name of all the students, else if will return: No name found; empty list
        * */
        if (this.foundStudents.size() != 0)
            return this.foundStudents.get(0).getName();
        else
            return "No name found; empty list";
    }
}
