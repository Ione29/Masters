package com.pp_lab_06;

import java.util.ArrayList;

public class Course {
    public int courseID;
    public int creditPoints;
    public int year;
    public String fullName;
    public ArrayList<Student> students;

    public Course(int vCourseID, int vCreditPoints, int vYear, String vFullName){
        this.courseID       = vCourseID;
        this.creditPoints   = vCreditPoints;
        this.year           = vYear;
        this.fullName       = vFullName;
        this.students       = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "Course: " + this.fullName + ";" +
                "ID: " + this.courseID + ";" +
                "Year: " + this.year + ";" +
                "Credit points: " + this.creditPoints + ";" +
                " ";
    }
}
