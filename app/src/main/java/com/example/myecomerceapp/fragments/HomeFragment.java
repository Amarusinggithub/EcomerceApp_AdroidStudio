package com.example.myecomerceapp.fragments;


import static com.example.myecomerceapp.activities.MainActivity.getCategory;
import static com.example.myecomerceapp.activities.MainActivity.getProductsData;
import static com.example.myecomerceapp.fragments.ProductRecyclerViewFragment.categoryId;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.CategoryAdapter;
import com.example.myecomerceapp.interfaces.MyCategoryOnClickListener;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Category;
import com.example.myecomerceapp.models.Product;




public class HomeFragment extends Fragment implements MyCategoryOnClickListener, MyProductOnClickListener {
     ImageView cashbackImage;
     ImageView favoriteIcon;

     ScrollView scrollView;
     FrameLayout pickedForYouFrameLayout;
     LinearLayout pickedForYouLinearLayout;
     SearchView searchView;
     FrameLayout frameLayout;
     CardView displayBanner;
     RecyclerView categoryRecycleView;
     LinearLayout popularProductsLinearLayout;
    CardView favoritesCd;
   FrameLayout popularProductsFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        favoritesCd=view.findViewById(R.id.favoritecd);
        cashbackImage=view.findViewById(R.id.cashbackimage);
        favoriteIcon =view.findViewById(R.id.favorites);
        scrollView=view.findViewById(R.id.scrollview);
        popularProductsFrameLayout=view.findViewById(R.id.popularproductframelayout);
        searchView=view.findViewById(R.id.searchview);
        frameLayout = view.findViewById(R.id.frameLayout);
        displayBanner =view. findViewById(R.id.displayBanner);
        categoryRecycleView =view. findViewById(R.id.categoriesRecycleView);
        popularProductsLinearLayout = view.findViewById(R.id.popularproductsLL);
        pickedForYouFrameLayout=view.findViewById(R.id.pickedforyouproductframelayout);
        pickedForYouLinearLayout=view.findViewById(R.id.pickedforyouproductsLL);



        Glide.with(this)
                .load(R.drawable.cashback)
                .fitCenter()
                .into(cashbackImage);

        Glide.with(this)
                .load(R.drawable.favoriteicon2)
                .fitCenter()
                .into(favoriteIcon);
        setupCategoryRecyclerView();
        loadPopularProductsFragment(new PopularProductsRecyclerViewFragment());
        loadPickedForYouProductsFragment(new PickedForYouFragment());

        favoritesCd.setOnClickListener(v -> loadFragment(new FavoritesFragment()));

    }
    private void setupCategoryRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecycleView.setLayoutManager(linearLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getCategory(),getContext());
        categoryRecycleView.setAdapter(categoryAdapter);
    }

    public void loadPopularProductsFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.popularproductframelayout,fragment);
        fragmentTransaction.commit();

    }
    public void loadPickedForYouProductsFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.pickedforyouproductframelayout,fragment);
        fragmentTransaction.commit();
    }
    public void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void categoryClicked(int position) {
        Category category = getCategory().get(position);
        ProductRecyclerViewFragment productRecyclerViewFragment =new ProductRecyclerViewFragment();
        categoryId= category.getCategoryId();
        loadFragment(productRecyclerViewFragment);
    }

    @Override
    public void productClicked(int position) {
        Product product = getProductsData(categoryId).get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("productName", product.getProductName());
        bundle.putString("productPrice", product.getProductPrice());
        bundle.putString("productDescription", product.getProductDescription());
        bundle.putString("position", product.getProductId());
        bundle.putInt("productImage", product.getProductImage());
        ProductViewFragment productViewFragment = new ProductViewFragment();
        productViewFragment.setArguments(bundle);
        loadFragment(productViewFragment);
    }
}