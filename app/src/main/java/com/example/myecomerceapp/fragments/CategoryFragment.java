package com.example.myecomerceapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myecomerceapp.interfaces.ItemOnClickInterface;
import com.example.myecomerceapp.activitys.MainActivity;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.CategoryAdapter;
import com.example.myecomerceapp.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment implements ItemOnClickInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View categorieRecycleView=inflater.inflate(R.layout.fragment_category, container, false);
        RecyclerView categoryRecycleView = categorieRecycleView.findViewById(R.id.categoriesRecycleView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(linearLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getCategoryData());
        categoryRecycleView.setAdapter(categoryAdapter);
        return categorieRecycleView;
    }

    public static List<CategoryModel> getCategoryData() {
        List<CategoryModel> categoryModelArrayList = new ArrayList<>();
        categoryModelArrayList.add(new CategoryModel("Phones", R.drawable.smartphones_category_icon,"SmartPhones"));
        categoryModelArrayList.add(new CategoryModel("Laptop", R.drawable.laptop_category_icon,"Laptop"));
        categoryModelArrayList.add(new CategoryModel("Games", R.drawable.games_category_icon,"Games"));
        categoryModelArrayList.add(new CategoryModel("Consoles",R.drawable.gamingconsoles_category_icon ,"Gaming Consoles"));
        categoryModelArrayList.add(new CategoryModel("Appliances",R.drawable.homeappliance_category_icon,"Home Appliances"));
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
        MainActivity.removeBannerRecyclerView();
        CategoryModel categoryModel=getCategoryData().get(position);
        ItemGridViewFragment itemGridViewFragment=new ItemGridViewFragment();
        ItemGridViewFragment.categoryId=categoryModel.getCategoryId();
        loadFragment(itemGridViewFragment);
    }

}