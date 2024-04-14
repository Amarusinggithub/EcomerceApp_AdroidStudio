package com.example.myecomerceapp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private final String categoryTitle;
    private final int categoryImage;
    private final String categoryId;

    public Category(String categoryTitle , int categoryImage, String categoryId) {
        this.categoryTitle =categoryTitle;
        this.categoryImage = categoryImage;
        this.categoryId=categoryId;
    }

}
