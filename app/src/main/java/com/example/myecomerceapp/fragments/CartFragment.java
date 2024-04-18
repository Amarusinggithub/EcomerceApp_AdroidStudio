package com.example.myecomerceapp.fragments;

import static com.example.myecomerceapp.activities.MainActivity.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.Product;

import java.util.ArrayList;
import java.util.HashMap;


public class CartFragment extends Fragment {
public static HashMap<Product,Integer> productsAddedToCart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        productsAddedToCart=user.getProducts();
        // Inflate the layout for this fragment
        View cartView = inflater.inflate(R.layout.fragment_cart, container, false);

        return cartView;
    }
}