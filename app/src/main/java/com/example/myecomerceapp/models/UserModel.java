package com.example.myecomerceapp.models;

public class UserModel {

    int userProfileImage;
    String email;
    String lastName;
    String Firstname;
    String password;

    public UserModel(int userProfileImage, String email, String lastName, String firstname, String password) {
        this.userProfileImage = userProfileImage;
        this.email = email;
        this.lastName = lastName;
        Firstname = firstname;
        this.password = password;
    }

    public int getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(int userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
