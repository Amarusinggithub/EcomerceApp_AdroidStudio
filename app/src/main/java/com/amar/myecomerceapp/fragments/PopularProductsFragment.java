package com.amar.myecomerceapp.fragments;


import static com.amar.myecomerceapp.activities.MainActivity.getPopularProductsData;
import static com.amar.myecomerceapp.activities.MainActivity.productInProductViewFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.adapters.ProductAdapter;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;


public class PopularProductsFragment extends Fragment implements MyProductOnClickListener {
     RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_popular_products, container, false);
        recyclerView =view.findViewById(R.id.popularproductrecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ProductAdapter adapter = new ProductAdapter(this, getPopularProductsData(),getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void productClicked(int position) {
       productInProductViewFragment = getPopularProductsData().get(position);
       ProductViewFragment productViewFragment = new ProductViewFragment();
       loadFragment(productViewFragment);
    }

    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}