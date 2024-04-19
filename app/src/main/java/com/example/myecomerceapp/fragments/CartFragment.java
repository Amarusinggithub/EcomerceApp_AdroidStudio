package com.example.myecomerceapp.fragments;

import static com.example.myecomerceapp.activities.MainActivity.user;
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
import com.example.myecomerceapp.adapters.CartAdapter;
import com.example.myecomerceapp.adapters.ProductAdapter;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;


public class CartFragment extends Fragment implements MyProductOnClickListener {
    RecyclerView recyclerView;
    public static HashMap<Product,Integer> productsAddedToCart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        productsAddedToCart=user.getProducts();
        // Inflate the layout for this fragment
        View cartView = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView=cartView.findViewById(R.id.recyclerview);
        CartAdapter cartAdapter= new CartAdapter(productsAddedToCart,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartAdapter);
        return cartView;
    }

    @Override
    public void productClicked(int position) {
        // Get all products from the HashMap
        Set<Product> products = productsAddedToCart.keySet();

        // Iterate over the set of products to find the product at the specified position
        Product product = null;
        int currentPosition = 0;
        for (Product p : products) {
            if (currentPosition == position) {
                product = p;
                break;
            }
            currentPosition++;
        }

        if (product != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
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

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment); // R.id.fragment_container is the id of the FrameLayout in your activity's layout XML where you want to replace the fragment
        fragmentTransaction.addToBackStack("ProductDetailsTransaction"); // This allows the user to navigate back to the previous fragment when pressing the back button
        fragmentTransaction.commit();
    }


}