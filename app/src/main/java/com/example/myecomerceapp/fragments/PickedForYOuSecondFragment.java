package com.example.myecomerceapp.fragments;

import static com.example.myecomerceapp.activities.MainActivity.getPickedForYouProductsData;
import static com.example.myecomerceapp.activities.MainActivity.productInProductViewFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.ProductAdapter;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;


public class PickedForYOuSecondFragment extends Fragment implements MyProductOnClickListener {
    ImageView backBtn;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    GridLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_picked_for_y_ou_second, container, false);
        initializeViewElements(view);
        return view;
    }

    private void initializeViewElements(View view) {
        backBtn=view.findViewById(R.id.backbtn);

        recyclerView= view.findViewById(R.id.recyclerview);
        setUpRecyclerView();
        setupBackButton();
    }
    private void setUpRecyclerView() {
        adapter= new ProductAdapter(this,getPickedForYouProductsData(),getContext());
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
    private void setupBackButton() {
        Glide.with(this)
                .load(R.drawable.whiteback)
                .fitCenter()
                .into(backBtn);

        backBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }
    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void productClicked(int position) {
        productInProductViewFragment = getPickedForYouProductsData().get(position);
        ProductViewFragment productViewFragment = new ProductViewFragment();
        loadFragment(productViewFragment);
    }
}