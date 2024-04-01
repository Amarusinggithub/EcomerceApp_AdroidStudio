package com.example.myecomerceapp.activities;

import static com.example.myecomerceapp.activities.MainActivity.getProductsData;
import static com.example.myecomerceapp.fragments.GridViewFragment.itemAdapter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity {
    public SearchView searchView;
    public  List<ProductModel> filtertedList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        searchView=findViewById(R.id.searchview);
    }




    public  void filter(String newText) {

        filtertedList= new ArrayList<>();
        for(ProductModel item: getProductsData("categoryId")){
            if(item.getProductName().toLowerCase().contains(newText.toLowerCase())){
                filtertedList.add(item);
            }
        }itemAdapter.filteredList(filtertedList);

    }
}
