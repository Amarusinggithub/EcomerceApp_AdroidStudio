package com.amar.myecomerceapp.models;



public class Product {
    private  int productImage;
    private  String productName;
    private  String productPrice;
    private  String productId;
    private  int productQuantity=1;
    private String productDescription;
    private String  productSalesPrice;

    boolean productAlreadyInFavorites=false;



    public Product(int productImage, String productName, String productPrice, String productDescription, String productId) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productId = productId;
        this.productDescription = productDescription;
    }
    public Product(int productImage,int productQuantity ,String productName, String productPrice,String  productSalesPrice, String productDescription, String productId) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productId = productId;
        this.productDescription = productDescription;
        this.productSalesPrice=productSalesPrice;
        this.productQuantity=productQuantity;
    }
    public Product(int productImage , String productName, String productPrice,String  productSalesPrice, String productDescription, String productId) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productId = productId;
        this.productDescription = productDescription;
        this.productSalesPrice=productSalesPrice;
    }


    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
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
}
