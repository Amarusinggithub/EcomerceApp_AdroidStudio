package com.example.myecomerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myecomerceapp.adapters.CategoryAdapter;
import com.example.myecomerceapp.models.CategoryModel;
import com.example.myecomerceapp.models.ItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoriesActivity extends AppCompatActivity implements  ItemOnClickInterface  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView categoryRecycleView = findViewById(R.id.categoriesRecycleView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        categoryRecycleView.setLayoutManager(linearLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getCategoryData());
        categoryRecycleView.setAdapter(categoryAdapter);
    }

    public List<CategoryModel> getCategoryData() {
        List<CategoryModel> categoryModelArrayList = new ArrayList<>();
        categoryModelArrayList.add(new CategoryModel("Fashion", R.drawable.fashoin_category_bg,"Fashion"));
        categoryModelArrayList.add(new CategoryModel("Groceries", R.drawable.groceries_catecory_bg,"Groceries"));
        categoryModelArrayList.add(new CategoryModel("Electronics", R.drawable.electronics_category_bg,"Electronics"));
        categoryModelArrayList.add(new CategoryModel("Home Appliances", R.drawable.homeappliances_category_bg,"Home Appliances"));
        categoryModelArrayList.add(new CategoryModel("Pet Supplies", R.drawable.petsupplies_category_bg,"Pet Supplies"));
        categoryModelArrayList.add(new CategoryModel("Personal Care", R.drawable.personalcare_category_bg,"Personal Care"));
        categoryModelArrayList.add(new CategoryModel("Sports", R.drawable.sports_andoutdoor_category_bg,"Sports"));
        return categoryModelArrayList;
    }


    @Override
    public void onItemClicked(int position) {
        CategoryModel category = getCategoryData().get(position);
        Intent intent = new Intent(CategoriesActivity.this, ItemGridViewActivity.class);
        intent.putExtra("CategoryID", category.getCategoryId());
        startActivity(intent);
    }
}