package com.example.myecomerceapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myecomerceapp.ItemOnClickInterface;
import com.example.myecomerceapp.MainActivity;
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
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        categoryRecycleView.setLayoutManager(gridLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getCategoryData());
        categoryRecycleView.setAdapter(categoryAdapter);
        return categorieRecycleView;
    }

    public static List<CategoryModel> getCategoryData() {
        List<CategoryModel> categoryModelArrayList = new ArrayList<>();
        categoryModelArrayList.add(new CategoryModel("Fashion", R.drawable.fashion_category_bg,"Fashion"));
        categoryModelArrayList.add(new CategoryModel("Groceries", R.drawable.groceries_category_bg,"Groceries"));
        categoryModelArrayList.add(new CategoryModel("Electronics", R.drawable.electronics_category_bg,"Electronics"));
        categoryModelArrayList.add(new CategoryModel("Home Appliances", R.drawable.homeappliances_category_bg,"Home Appliances"));
       /* categoryModelArrayList.add(new CategoryModel("Personal Care", R.drawable.personalcare_category_bg,"Personal Care"));
        categoryModelArrayList.add(new CategoryModel("Sports And Adventure", R.drawable.sports_andoutdoors_category_bg,"Sports And Adventure"));*/
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
        itemGridViewFragment.categoryId=categoryModel.getCategoryId();
        loadFragment(itemGridViewFragment);
    }

}