package com.example.myecomerceapp;

public class CategoryModel {
    private final String categoryTitle;
    private final int categoryImage;

    public CategoryModel(String categoryTitle ,int categoryImage) {
        this.categoryTitle =categoryTitle;
        this.categoryImage = categoryImage;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public int getCategoryImage() {
        return categoryImage;
    }
}
