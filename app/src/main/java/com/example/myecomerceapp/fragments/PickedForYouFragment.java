package com.example.myecomerceapp.fragments;


import static com.example.myecomerceapp.activities.MainActivity.PICKED_FOR_YOU_PRODUCTS;
import static com.example.myecomerceapp.activities.MainActivity.getProductsData;
import static com.example.myecomerceapp.activities.MainActivity.removeViews;

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


public class PickedForYouFragment extends Fragment implements MyProductOnClickListener {

    private RecyclerView pickedForYouProductsRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_picked_for_you, container, false);
        pickedForYouProductsRecyclerview=view.findViewById(R.id.pickedforyourecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        pickedForYouProductsRecyclerview.setLayoutManager(linearLayoutManager);
        ProductAdapter popularProductAdapter = new ProductAdapter(this, getProductsData(PICKED_FOR_YOU_PRODUCTS),getContext());
        pickedForYouProductsRecyclerview.setAdapter(popularProductAdapter);

        return view ;
    }

    @Override
    public void productClicked(int position) {

        Product product = getProductsData(PICKED_FOR_YOU_PRODUCTS).get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("productName", product.getProductName());
        bundle.putString("productPrice", product.getProductPrice());
        bundle.putString("productDescription", product.getProductDescription());
        bundle.putString("position", product.getProductId());
        bundle.putInt("productImage", product.getProductImage());
        ProductViewFragment productViewFragment = new ProductViewFragment();
        productViewFragment.setArguments(bundle);
        loadFragment(productViewFragment);
    }

    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
        removeViews();
    }
}