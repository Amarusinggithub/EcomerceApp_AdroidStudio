package com.amar.myecomerceapp.fragments;

import static com.amar.myecomerceapp.activities.MainActivity.getPickedForYouProductsData;
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

public class PickedForYouFragment extends Fragment implements MyProductOnClickListener {
    RecyclerView pickedForYouProductsRecyclerview;
    ProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_picked_for_you, container, false);
        pickedForYouProductsRecyclerview = view.findViewById(R.id.pickedforyourecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        pickedForYouProductsRecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(this, getPickedForYouProductsData(), getContext());
        pickedForYouProductsRecyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    public void productClicked(int position) {
        productInProductViewFragment = getPickedForYouProductsData().get(position);
        ProductViewFragment productViewFragment = new ProductViewFragment();
        loadFragment(productViewFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
