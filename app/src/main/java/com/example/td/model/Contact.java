package com.example.td.model;

import java.io.Serializable;

public class Contact implements Serializable {
    private static int globalId = 0;

    private int id;
    private String firstName;
    private String lastName;
    private String phonePortable;
    private String phoneHome;
    private String location;
    private String email;

    public Contact(String firstName,
                   String lastName) {
        this(firstName, lastName, "phone", "home", "email", "address");
    }

    public Contact(String firstName,
                   String lastName,
                   String phonePortable,
                   String phoneHome,
                   String email,
                   String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phonePortable = phonePortable;
        this.phoneHome = phoneHome;
        this.email = email;
        this.location = location;
        this.id = globalId++;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhonePortable() {
        return phonePortable;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }
}
