package com.example.myecomerceapp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private  int productImage;
    private  String productName;
    private  String productPrice;
    private  String productId;
    private  int productQuantity=1;
    public final String productDescription;

    public Product(int productImage, String productName, String productPrice, String productDescription, String productId) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productId = productId;
        this.productDescription = productDescription;
    }

}
