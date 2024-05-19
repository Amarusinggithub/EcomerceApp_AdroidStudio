package com.amar.myecomerceapp.models;




public class Category {
    private  String categoryTitle;
    private  int categoryImage;
    private  String categoryId;

    public Category(String categoryTitle , int categoryImage, String categoryId) {
        this.categoryTitle =categoryTitle;
        this.categoryImage = categoryImage;
        this.categoryId=categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
