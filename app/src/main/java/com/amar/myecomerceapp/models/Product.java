package com.amar.myecomerceapp.models;

public class Product {
    private  int productQuantity=1;
    private String image;
    private  String productName;
    private  String productPrice;
    private  String productId;
    private String productDescription;
    private String  productSalesPrice;
    private String addressDeliveredTo;
    private String dateAndTimeout;
    boolean productAlreadyInFavorites=false;


    public Product() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public String getProductSalesPrice() {
        return productSalesPrice;
    }

    public void setProductSalesPrice(String productSalesPrice) {
        this.productSalesPrice = productSalesPrice;
    }

    public boolean isProductAlreadyInFavorites() {
        return productAlreadyInFavorites;
    }

    public void setProductAlreadyInFavorites(boolean productAlreadyInFavorites) {
        this.productAlreadyInFavorites = productAlreadyInFavorites;
    }

    public String getAddressDeliveredTo() {
        return addressDeliveredTo;
    }

    public void setAddressDeliveredTo(String addressDeliveredTo) {
        this.addressDeliveredTo = addressDeliveredTo;
    }

    public String getDateAndTimeout() {
        return dateAndTimeout;
    }

    public void setDateAndTimeout(String dateAndTimeout) {
        this.dateAndTimeout = dateAndTimeout;
    }
}
