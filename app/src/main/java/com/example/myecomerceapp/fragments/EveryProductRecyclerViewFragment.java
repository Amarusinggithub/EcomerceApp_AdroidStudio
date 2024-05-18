package com.example.myecomerceapp.fragments;

import static com.example.myecomerceapp.activities.MainActivity.EVERY_PRODUCT;
import static com.example.myecomerceapp.activities.MainActivity.getProductsData;
import static com.example.myecomerceapp.activities.MainActivity.productInProductViewFragment;
import static com.example.myecomerceapp.activities.MainActivity.productsFavorited;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.ProductAdapter;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;


public class EveryProductRecyclerViewFragment extends Fragment implements MyProductOnClickListener {
RecyclerView recyclerView;
ProductAdapter adapter;
GridLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_every_product_recycler_view, container, false);
        initializeView(view);
        return view ;
    }

    private void initializeView(View view) {
        recyclerView=view.findViewById(R.id.everyproductrecyclerview);
        adapter= new ProductAdapter(this,getProductsData(EVERY_PRODUCT),getContext());
        layoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void productClicked(int position) {
        productInProductViewFragment = getProductsData(EVERY_PRODUCT).get(position);
        ProductViewFragment productViewFragment = new ProductViewFragment();
        loadFragment(productViewFragment);
    }
}