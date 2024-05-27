package com.amar.myecomerceapp.fragments;


import static com.amar.myecomerceapp.activities.MainActivity.productInProductViewFragment;
import static com.amar.myecomerceapp.activities.MainActivity.productsFavorited;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.adapters.FavoriteAdapter;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;
import com.bumptech.glide.Glide;



public class FavoritesFragment extends Fragment implements MyProductOnClickListener {

    RecyclerView recyclerView;
    ImageView backBtn;
    ImageView emptyFavoritesImage;
    GridLayoutManager layoutManager;
    FavoriteAdapter favoritesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_favorites, container, false);
        initializeViewElements(view);
        return  view;
    }

    private void initializeViewElements(View view) {
        backBtn=view.findViewById(R.id.backbtn);

        recyclerView= view.findViewById(R.id.recyclerview);
        emptyFavoritesImage= view.findViewById(R.id.favoritesisemptyimage);

        if (!productsFavorited.isEmpty()){
            setUpFavoritesRecyclerView();
        }else{
            setupFavoritesEmptyView();
        }
        setupBackButton();
    }

    private  void setupFavoritesEmptyView() {
        emptyFavoritesImage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Glide.with(this)
                .load(R.drawable.nofavorites)
                .fitCenter()
                .into( emptyFavoritesImage);
    }

    private void setUpFavoritesRecyclerView() {
        emptyFavoritesImage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        favoritesAdapter= new FavoriteAdapter(productsFavorited,this,getContext());
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoritesAdapter);
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
        fragmentTransaction.addToBackStack("favoritesFragment");
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void productClicked(int position) {
       productInProductViewFragment = productsFavorited.get(position);
        ProductViewFragment productViewFragment = new ProductViewFragment();
        loadFragment(productViewFragment);
    }
}