package com.example.myecomerceapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myecomerceapp.ItemOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.CategoryAdapter;
import com.example.myecomerceapp.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CategoryFragment extends Fragment implements ItemOnClickInterface {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View CategorieRecycleView=inflater.inflate(R.layout.fragment_category, container, false);
        RecyclerView categoryRecycleView = CategorieRecycleView.findViewById(R.id.categoriesRecycleView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        categoryRecycleView.setLayoutManager(linearLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getCategoryData());
        categoryRecycleView.setAdapter(categoryAdapter);


        return CategorieRecycleView;
    }

    public List<CategoryModel> getCategoryData() {
        List<CategoryModel> categoryModelArrayList = new ArrayList<>();
        categoryModelArrayList.add(new CategoryModel("PCs and Parts", R.drawable.fashoin_category_bg,"PCs and Parts"));
        categoryModelArrayList.add(new CategoryModel("Smart phones and Tablets", R.drawable.groceries_catecory_bg,"Smartphones and Tablets"));
        categoryModelArrayList.add(new CategoryModel("Audio and Video Equipment", R.drawable.electronics_category_bg,"Audio and Video Equipment"));
        categoryModelArrayList.add(new CategoryModel("Home Appliances", R.drawable.homeappliances_category_bg,"Home Appliances"));
        categoryModelArrayList.add(new CategoryModel("Gaming", R.drawable.petsupplies_category_bg,"Gaming"));
        categoryModelArrayList.add(new CategoryModel("Wearables", R.drawable.personalcare_category_bg,"Wearables"));
        categoryModelArrayList.add(new CategoryModel("Smart Home and Networking", R.drawable.sports_andoutdoor_category_bg,"Smart Home and Networking"));
        return categoryModelArrayList;
    }
    private void loadFragment(Fragment fragment) {

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClicked(int position) {
        CategoryModel category = getCategoryData().get(position);
        ItemGridViewFragment itemGridViewFragment=new ItemGridViewFragment();
        loadFragment(itemGridViewFragment);

    }

}