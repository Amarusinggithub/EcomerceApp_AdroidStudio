package com.example.myecomerceapp.fragments;


import static com.example.myecomerceapp.activities.MainActivity.getProductsData;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myecomerceapp.interfaces.MyOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.ProductAdapter;
import com.example.myecomerceapp.models.ProductModel;

import java.io.Serializable;

public class ProductRecyclerViewFragment extends Fragment implements MyOnClickInterface {
    public static  String categoryId;

    public static  ProductAdapter productAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_products_recyclerview, container, false);
        RecyclerView productRecyclerView =view.findViewById( R.id.recyclerview);
        productAdapter = new ProductAdapter(this, getProductsData(categoryId));
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        productRecyclerView.setLayoutManager(layoutManager);
        productRecyclerView.setAdapter(productAdapter);

        return view;
    }


    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClicked(int position) {
        ProductModel product = getProductsData(categoryId).get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("productName", product.getProductName());
        bundle.putString("proPrice", product.getProductPrice());
        bundle.putString("productDescription", product.getProductDescription());
        bundle.putString("position", product.getProductId());
        bundle.putInt("productImage", product.getProductImage());
        ProductViewFragment productViewFragment = new ProductViewFragment();
        productViewFragment.setArguments(bundle);
        loadFragment(productViewFragment);
    }

}