package com.amar.myecomerceapp.models;

import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class User {
    private String email;
    private String username;
    private String password;
    private ArrayList<String> address;
    private ArrayList<Product> productsUserOrdered = new ArrayList<>();
    private ArrayList<Product> productsUserAddedToCart = new ArrayList<>();
    private ArrayList<Product> productsFavorited = new ArrayList<>();

    public User() {
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("username")
    public String getUsername() {
        return username;
    }

    @PropertyName("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @PropertyName("password")
    public String getPassword() {
        return password;
    }

    @PropertyName("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @PropertyName("address")
    public ArrayList<String> getAddress() {
        return address;
    }

    @PropertyName("address")
    public void setAddress(ArrayList<String> address) {
        this.address = address;
    }

    @PropertyName("ordered")
    public ArrayList<Product> getProductsUserOrdered() {
        return productsUserOrdered;
    }

    @PropertyName("ordered")
    public void setProductsUserOrdered(ArrayList<Product> productsUserOrdered) {
        this.productsUserOrdered = productsUserOrdered;
    }

    @PropertyName("cart")
    public ArrayList<Product> getProductsUserAddedToCart() {
        return productsUserAddedToCart;
    }

    @PropertyName("cart")
    public void setProductsUserAddedToCart(ArrayList<Product> productsUserAddedToCart) {
        this.productsUserAddedToCart = productsUserAddedToCart;
    }

    @PropertyName("favorites")
    public ArrayList<Product> getProductsFavorited() {
        return productsFavorited;
    }

    @PropertyName("favorites")
    public void setProductsFavorited(ArrayList<Product> productsFavorited) {
        this.productsFavorited = productsFavorited;
    }
}
