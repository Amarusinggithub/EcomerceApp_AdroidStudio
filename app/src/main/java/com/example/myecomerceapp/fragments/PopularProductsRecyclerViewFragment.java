package com.example.myecomerceapp.fragments;

import static com.example.myecomerceapp.activities.MainActivity.POPULAR_PRODUCTS;
import static com.example.myecomerceapp.activities.MainActivity.frameLayout;
import static com.example.myecomerceapp.activities.MainActivity.getProductsData;
import static com.example.myecomerceapp.activities.MainActivity.removeViews;
import static com.example.myecomerceapp.fragments.ProductRecyclerViewFragment.categoryId;

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
        ProductAdapter popularProductAdapter = new ProductAdapter(this, getProductsData(POPULAR_PRODUCTS));
        popularProductsRecyclerview.setAdapter(popularProductAdapter);
        return view;
    }


    @Override
    public void productClicked(int position) {

        Product product = getProductsData(POPULAR_PRODUCTS).get(position);
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

    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
        removeViews();
    }
}