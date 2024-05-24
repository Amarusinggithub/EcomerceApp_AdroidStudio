package com.amar.myecomerceapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amar.myecomerceapp.R;
import com.bumptech.glide.Glide;

import java.util.Objects;


public class OrderRecieptFragment extends Fragment {
ImageView checkOut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_reciept, container, false);
        initializeViewElements(view);
        return view;
    }

    private void initializeViewElements(View view) {
        checkOut=view.findViewById(R.id.checkout);
        Glide.with(requireContext())
                .load(R.drawable.check)
                .fitCenter()
                .into(checkOut);
    }
}