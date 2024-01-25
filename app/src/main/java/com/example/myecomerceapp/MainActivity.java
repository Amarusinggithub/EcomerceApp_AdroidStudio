package com.example.myecomerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private CategoryAdapter categoryAdapter;
     private  ArrayList<CategoryModel> categoryModelArrayList=null;
    private RecyclerView categoryRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryRecycleView=findViewById(R.id.categoriesRecycleView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        categoryRecycleView.setLayoutManager(linearLayoutManager);
        categoryAdapter=new CategoryAdapter(AddCategoriesToArrayList());
        categoryRecycleView.setAdapter(categoryAdapter);
    }

    public ArrayList<CategoryModel> AddCategoriesToArrayList() {
        categoryModelArrayList = new ArrayList<>();
        categoryModelArrayList.add(new CategoryModel("Fashion", R.drawable.fashoin_category_bg));
        categoryModelArrayList.add(new CategoryModel("Groceries", R.drawable.groceries_catecory_bg));
        categoryModelArrayList.add(new CategoryModel("Electronics", R.drawable.electronics_category_bg));
        categoryModelArrayList.add(new CategoryModel("Home Appliances", R.drawable.homeappliances_category_bg));
        categoryModelArrayList.add(new CategoryModel("Pet Supplies", R.drawable.petsupplies_category_bg));
        categoryModelArrayList.add(new CategoryModel("Personal Care", R.drawable.personalcare_category_bg));
        categoryModelArrayList.add(new CategoryModel("Sports", R.drawable.sports_andoutdoor_category_bg));
        return categoryModelArrayList;
    }


}