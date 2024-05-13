package com.example.myecomerceapp.fragments;


import static com.example.myecomerceapp.activities.MainActivity.getPopularProductsData;
import static com.example.myecomerceapp.activities.MainActivity.productInProductViewFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.ProductAdapter;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Product;


public class PopularProductsRecyclerViewFragment extends Fragment implements MyProductOnClickListener {
     RecyclerView popularProductsRecyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_popular_products_recycler_view, container, false);
        popularProductsRecyclerview=view.findViewById(R.id.popularproductrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularProductsRecyclerview.setLayoutManager(linearLayoutManager);
        ProductAdapter popularProductAdapter = new ProductAdapter(this, getPopularProductsData(),getContext());
        popularProductsRecyclerview.setAdapter(popularProductAdapter);
        return view;
    }


    @Override
    public void productClicked(int position) {

       productInProductViewFragment = getPopularProductsData().get(position);
       ProductViewFragment productViewFragment = new ProductViewFragment();
       loadFragment(productViewFragment);
    }

    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}