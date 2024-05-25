package com.amar.myecomerceapp.fragments;

import static com.amar.myecomerceapp.activities.MainActivity.pickedForYouProductsData;
import static com.amar.myecomerceapp.activities.MainActivity.productInProductViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.adapters.PickedForYouAdapter;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;

public class PickedForYouFragment extends Fragment implements MyProductOnClickListener {
    RecyclerView pickedForYouProductsRecyclerview;
    PickedForYouAdapter pickedForYOuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_picked_for_you, container, false);
        pickedForYouProductsRecyclerview = view.findViewById(R.id.pickedforyourecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        pickedForYouProductsRecyclerview.setLayoutManager(linearLayoutManager);
        pickedForYOuAdapter = new PickedForYouAdapter(this, pickedForYouProductsData, getContext());
        pickedForYouProductsRecyclerview.setAdapter(pickedForYOuAdapter);
        return view;
    }

    @Override
    public void productClicked(int position) {
        productInProductViewFragment = pickedForYouProductsData.get(position);
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
