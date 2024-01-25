package com.example.myecomerceapp;

public class CategoryModel {
    private String catogoryTitle;
    private int categoryImage;

    public CategoryModel(String catogoryTitle ,int categoryImage) {
        this.catogoryTitle=catogoryTitle;
        this.categoryImage = categoryImage;
    }

    public String getCatogoryTitle() {
        return catogoryTitle;
    }

    public int getCategoryImage() {
        return categoryImage;
    }
}
