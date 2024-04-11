package com.example.myecomerceapp.models;

import java.util.Map;

public class User {

    int userProfileImage;
    String email;

    String userName;
    String password;
    Map<Product,Integer> products;


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



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }
}
