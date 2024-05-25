package com.amar.myecomerceapp.fragments;


import static com.amar.myecomerceapp.activities.MainActivity.everyProduct;

import static com.amar.myecomerceapp.activities.MainActivity.productInProductViewFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.adapters.ProductAdapter;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;


public class EveryProductRecyclerViewFragment extends Fragment implements MyProductOnClickListener {
RecyclerView recyclerView;
public static ProductAdapter everyProductAdapter;
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
        if (!everyProduct.isEmpty()){
            everyProductAdapter = new ProductAdapter(this, everyProduct,getContext());
            layoutManager=new GridLayoutManager(getContext(),2);
            recyclerView.setAdapter(everyProductAdapter);
            recyclerView.setLayoutManager(layoutManager);
        }

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
        productInProductViewFragment = everyProductAdapter.filteredList.get(position);
        ProductViewFragment productViewFragment = new ProductViewFragment();
        loadFragment(productViewFragment);
    }
}