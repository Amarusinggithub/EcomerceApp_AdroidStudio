package com.example.myecomerceapp.activities;

import static com.example.myecomerceapp.activities.MainActivity.getProductsData;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity {
    public SearchView searchView;
    public  List<Product> filtertedList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        searchView=findViewById(R.id.searchview);
    }




    public  void filter(String newText) {

        filtertedList= new ArrayList<>();
        for(Product product: getProductsData("categoryId")){
            if(product.getProductName().toLowerCase().contains(newText.toLowerCase())){
                filtertedList.add(product);
            }
        }
       /* productAdapter.filteredList(filtertedList);*/

    }
}
