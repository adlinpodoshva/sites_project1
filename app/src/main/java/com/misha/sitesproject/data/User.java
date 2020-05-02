package com.misha.sitesproject.data;

// empty constructor for firebase
public class User {
    private String email;
    private String fullName;
    private String phone;

    public User() {

    }

    public User(String email, String fullName, String phone) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }
}
