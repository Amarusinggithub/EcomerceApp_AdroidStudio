package com.example.myecomerceapp.activitys;

import static com.example.myecomerceapp.activitys.MainActivity.getData;
import static com.example.myecomerceapp.fragments.ItemGridViewFragment.itemAdapter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity {
    public SearchView searchView;
    public  List<ItemModel> filtertedList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        searchView=findViewById(R.id.searchview);
    }




    public  void filter(String newText) {

        filtertedList= new ArrayList<>();
        for(ItemModel item: getData("categoryId")){
            if(item.getItemName().toLowerCase().contains(newText.toLowerCase())){
                filtertedList.add(item);
            }
        }itemAdapter.filteredList(filtertedList);

    }
}
