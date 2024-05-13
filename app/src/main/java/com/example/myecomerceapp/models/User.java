package com.example.myecomerceapp.models;

import java.util.ArrayList;


public class User {
    String email;
    String username;
    String password;
    ArrayList<Product> productsUserOrdered = new ArrayList<>();
    ArrayList<Product> productsUserAddedToCart= new ArrayList<>();
    ArrayList<Product> productsFavorited = new ArrayList<>();

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

    public ArrayList<Product> getProductsUserOrdered() {
        return productsUserOrdered;
    }

    public void setProductsUserOrdered(ArrayList<Product> productsUserOrdered) {
        this.productsUserOrdered = productsUserOrdered;
    }

    public ArrayList<Product> getProductsUserAddedToCart() {
        return productsUserAddedToCart;
    }

    public void setProductsUserAddedToCart(ArrayList<Product> productsUserAddedToCart) {
        this.productsUserAddedToCart = productsUserAddedToCart;
    }

    public ArrayList<Product> getProductsFavorited() {
        return productsFavorited;
    }

    public void setProductsFavorited(ArrayList<Product> productsFavorited) {
        this.productsFavorited = productsFavorited;
    }
}
