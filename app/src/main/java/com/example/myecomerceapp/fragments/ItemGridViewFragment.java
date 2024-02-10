package com.example.myecomerceapp.fragments;


import android.content.Intent;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.myecomerceapp.ItemOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.ItemAdapter;
import com.example.myecomerceapp.models.ItemModel;


import java.util.Objects;

public class ItemGridViewFragment extends Fragment implements ItemOnClickInterface {
    public String categoryId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ItemCridView=inflater.inflate(R.layout.fragment_item_grid_view, container, false);

        GridView fashionGridView =ItemCridView.findViewById( R.id.gridView);
        ItemAdapter itemAdapter = new ItemAdapter(this, getData(categoryId));
        fashionGridView.setAdapter(itemAdapter);


        return ItemCridView;
    }

    public static List<ItemModel> getData(String categoryId) {
        List<ItemModel>ItemsArrayList = new ArrayList<>();

        return ItemsArrayList;

    }


    @Override
    public void onItemClicked(int position) {


    }
}