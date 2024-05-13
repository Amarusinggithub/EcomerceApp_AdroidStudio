package com.example.myecomerceapp.fragments;

import static com.example.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.example.myecomerceapp.activities.MainActivity.productsUserOrdered;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.OrderAdapter;
import com.example.myecomerceapp.interfaces.MyOrderOnClickListener;



public class OrdersFragment extends Fragment implements MyOrderOnClickListener {

    OrderAdapter orderAdapter;
    RecyclerView recyclerView;
    ImageView emptyOrderImage;
    LinearLayoutManager linearLayoutManager;
    ImageView backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_orders, container, false);
        initializeViewElements(view);
        return view;
    }

    private void initializeViewElements(View view) {
        backBtn=view.findViewById(R.id.backbtn);

        recyclerView= view.findViewById(R.id.recyclerview);
        emptyOrderImage= view.findViewById(R.id.orderisemptyimage);

        if (!productsUserOrdered.isEmpty()){
            setUpOrderRecyclerView();
        }else{
            setupOrderEmptyView();
        }
        setupBackButton();
    }

    private void setupBackButton() {
        Glide.with(this)
                .load(R.drawable.whiteback)
                .fitCenter()
                .into(backBtn);

        backBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void setupOrderEmptyView() {
        emptyOrderImage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Glide.with(this)
                .load(R.drawable.no_orders2)
                .fitCenter()
                .into(emptyOrderImage);
    }


    private void setUpOrderRecyclerView() {
        emptyOrderImage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        orderAdapter= new OrderAdapter(productsUserOrdered,this,getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderAdapter);

    }


    @Override
    public void orderClicked(int position) {

    }
}