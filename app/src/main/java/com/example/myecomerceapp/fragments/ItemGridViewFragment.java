package com.example.myecomerceapp.fragments;


import android.content.Intent;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

        GridView itemGridView =ItemCridView.findViewById( R.id.gridView);
        ItemAdapter itemAdapter = new ItemAdapter(this, getData(categoryId));
        itemGridView.setNumColumns(2);
        itemGridView.setAdapter(itemAdapter);

        return ItemCridView;
    }

    public static List<ItemModel> getData(String categoryId) {
        List<ItemModel>ItemsArrayList = new ArrayList<>();
        if ("Fashion".equals(categoryId)) {
            // add fashion items
            ItemsArrayList.add(new ItemModel(R.drawable.men_suit,"Men Suit","$1,000","All black suit","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.men_clothes_bundle,"Men Clothing Bundle","$5,000","All black suit","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.white_buttondown_shirt,"White Shirt","$500","All black suit","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.shoes,"shoes","$1,000","All black suit","Fashion"));
        } else if ("Groceries".equals(categoryId)) {
            // add groceries items
            ItemsArrayList.add(new ItemModel(R.drawable.milk_img,"Milk","$500","white cows milk","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.cranberryjuice,"Cranberry Juice","$500","Cranberry Juice","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
        }
        return ItemsArrayList;

    }

    private  void loadFragment(Fragment fragment) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClicked(int position) {
        ItemModel item = getData(categoryId).get(position);
        Bundle bundle = new Bundle();
        bundle.putString("ItemName", item.getItemName());
        bundle.putString("ItemPrice", item.getItemPrice());
        bundle.putString("ItemDescription", item.getItemSpecs());
        bundle.putInt("ItemImage", item.getItemImage());
        ItemViewFragment itemViewFragment = new ItemViewFragment();
        itemViewFragment.setArguments(bundle);
        loadFragment(itemViewFragment);
    }

}