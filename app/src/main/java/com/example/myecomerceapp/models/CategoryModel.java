package com.example.myecomerceapp.models;

public class CategoryModel {
    private final String categoryTitle;
    private final int categoryImage;
    private  String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public CategoryModel(String categoryTitle , int categoryImage, String categoryId) {
        this.categoryTitle =categoryTitle;
        this.categoryImage = categoryImage;
        this.categoryId=categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public int getCategoryImage() {
        return categoryImage;
    }
}
