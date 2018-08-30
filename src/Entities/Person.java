package Entities;

import javafx.beans.property.SimpleStringProperty;

public abstract class Person {
    SimpleStringProperty firstName;
    SimpleStringProperty lastName;

    public Person(String FirstName, String LastName) {
        firstName = new SimpleStringProperty(FirstName);
        lastName = new SimpleStringProperty(LastName);
    }
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public String toString() {return "First Name: " + this.getFirstName() + " | Last Name: " + this.getLastName(); }

    protected void setFirstName(String firstName) { this.firstName.set(firstName); }
    protected void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

}
