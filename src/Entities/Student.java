package Entities;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Student extends Person {
    SimpleIntegerProperty studentId;
    SimpleDoubleProperty gpa;

    public Student(int StudentId, Double Gpa, String firstName, String LastName) {
        super(firstName, LastName);
        studentId = new SimpleIntegerProperty(StudentId);
        gpa = new SimpleDoubleProperty(Gpa);
    }

    public int getStudentId() {
        return studentId.get();
    }
    public Double getGpa() {
        return gpa.get();
    }

    @Override
    public String toString() {
        return "Student ID: " + this.getStudentId() + " | " + super.toString() + " | GPA: " + this.getGpa();
    }

    protected void setGpa(Double gpa) { this.gpa.set(gpa);}
    protected void setStudentId(int studentId) { this.studentId.set(studentId); }
}
