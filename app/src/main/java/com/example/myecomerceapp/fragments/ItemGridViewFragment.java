package com.example.myecomerceapp.fragments;


import static com.example.myecomerceapp.activitys.MainActivity.getData;

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

import com.example.myecomerceapp.interfaces.ItemOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.ItemAdapter;
import com.example.myecomerceapp.models.ItemModel;

public class ItemGridViewFragment extends Fragment implements ItemOnClickInterface {
    public static  String categoryId;

    public static ItemAdapter itemAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ItemCridView=inflater.inflate(R.layout.fragment_item_grid_view, container, false);
        GridView itemGridView =ItemCridView.findViewById( R.id.gridView);
        itemAdapter = new ItemAdapter(this, getData(categoryId));
        itemGridView.setNumColumns(2);
        itemGridView.setAdapter(itemAdapter);

        return ItemCridView;
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