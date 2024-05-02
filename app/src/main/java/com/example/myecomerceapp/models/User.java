package com.example.myecomerceapp.models;

import java.util.ArrayList;


public class User {
    String email;
    String username;
    String password;
    ArrayList<Product> productsUserBought= new ArrayList<>();
    ArrayList<Product> productsUserAddedToCart= new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Product> getProductsUserBought() {
        return productsUserBought;
    }

    public void setProductsUserBought(ArrayList<Product> productsUserBought) {
        this.productsUserBought = productsUserBought;
    }

    public ArrayList<Product> getProductsUserAddedToCart() {
        return productsUserAddedToCart;
    }

    public void setProductsUserAddedToCart(ArrayList<Product> productsUserAddedToCart) {
        this.productsUserAddedToCart = productsUserAddedToCart;
    }
}
