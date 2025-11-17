package com.pp_lab_06;

import java.util.ArrayList;

public class Student {
    public int studentID;
    public String name;
    public String group;
    public ArrayList<Course> courses;

    public Student(int vStudentID, String vName, String vGroup){
        this.studentID  = vStudentID;
        this.name       = vName;
        this.group      = vGroup;
        this.courses    = new ArrayList<>();
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        
        // Student info
        output.append("Student's Name: ").append(name)
                .append("(ID: )").append(studentID)
                .append(", Group: ").append(group)
                .append(")\n");

        //Courses info
        output.append("Courses: \n");

        if(courses.isEmpty())
            output.append("This student is not enrolled in any course.");
        else
            for(Course course : courses)
                output.append("- ").append(course.toString()).append("\n");
        
        output.append("\n");
        return output.toString();
    }
}
