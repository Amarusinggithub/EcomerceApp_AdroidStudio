package com.example.myecomerceapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.Product;

import java.util.ArrayList;


public class CartFragment extends Fragment {
public static ArrayList<Product> productsAddedToCart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cartView = inflater.inflate(R.layout.fragment_cart, container, false);

        return cartView;
    }
}