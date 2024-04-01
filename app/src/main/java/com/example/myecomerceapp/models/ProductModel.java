package com.example.myecomerceapp.models;

public class ProductModel {
    private final int productImage;
    private final String productName;
    private final String productPrice;
    private final String productId;

    public final String productDescription;

    public ProductModel(int productImage, String productName, String productPrice, String productDescription, String productId) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productId = productId;
        this.productDescription = productDescription;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public int getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }
}
