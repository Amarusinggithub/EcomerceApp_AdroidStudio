package com.amar.myecomerceapp.fragments;


import static com.amar.myecomerceapp.activities.MainActivity.getCategory;
import static com.amar.myecomerceapp.activities.MainActivity.productInProductViewFragment;
import static com.amar.myecomerceapp.adapters.ProductAdapter.filteredList;
import static com.amar.myecomerceapp.fragments.EveryProductRecyclerViewFragment.adapter;
import static com.amar.myecomerceapp.fragments.ProductRecyclerViewFragment.categoryId;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.adapters.CategoryAdapter;
import com.amar.myecomerceapp.interfaces.MyCategoryOnClickListener;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;
import com.amar.myecomerceapp.models.Category;


public class HomeFragment extends Fragment implements MyCategoryOnClickListener, MyProductOnClickListener {
    ImageView cashbackImage;
    ImageView favoriteIcon;
    ScrollView scrollView;
    FrameLayout pickedForYouFrameLayout;
    LinearLayout pickedForYouLinearLayout;
    EditText searchView;
    FrameLayout frameLayout;
    CardView salesBanner;
    RecyclerView categoryRecycleView;
    LinearLayout popularProductsLinearLayout;
    CardView favoritesCd;
    FrameLayout popularProductsFrameLayout;
    TextView seeAllPopularProducts;
    TextView seeAllPickedForYou;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews(view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeViews(View view) {
        seeAllPickedForYou=view.findViewById(R.id.seeallforpickedforyou);
        seeAllPopularProducts=view.findViewById(R.id.seeallforpopularproductstv);
        favoritesCd=view.findViewById(R.id.favoritecd);
        cashbackImage=view.findViewById(R.id.cashbackimage);
        favoriteIcon =view.findViewById(R.id.favorites);
        scrollView=view.findViewById(R.id.scrollview);
        popularProductsFrameLayout=view.findViewById(R.id.popularproductframelayout);
        searchView=view.findViewById(R.id.searchview);
        frameLayout = view.findViewById(R.id.recyclerviewcontainer);
        salesBanner =view. findViewById(R.id.salesBanner);
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
        loadPopularProductsFragment(new PopularProductsFragment());
        loadPickedForYouProductsFragment(new PickedForYouFragment());


        favoritesCd.setOnClickListener(v -> loadFragment(new FavoritesFragment()));
        seeAllPopularProducts.setOnClickListener(v -> loadFragment( new PopularProductsSecondFragment()));
        seeAllPickedForYou.setOnClickListener(v -> loadFragment(new PickedForYOuSecondFragment()));
        salesBanner.setOnClickListener(v -> loadFragment(new SalesFragment()));

        searchView.setOnTouchListener((v, event) -> {
            scrollView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
            loadSearchFragment(new EveryProductRecyclerViewFragment());
            return false;
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null&&adapter!=null){
                    adapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.add(R.id.popularproductframelayout,fragment);
        fragmentTransaction.commit();

    }

    public void loadPickedForYouProductsFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("HomeFragment");
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

    public void loadSearchFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("HomeFragment");
        fragmentTransaction.replace(R.id.recyclerviewcontainer, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void categoryClicked(int position) {
        Category category = getCategory().get(position);
        categoryId= category.getCategoryId();
        loadFragment(new ProductRecyclerViewFragment());
    }


    @Override
    public void productClicked(int position) {
        productInProductViewFragment = filteredList.get(position);
        loadFragment(new ProductViewFragment());
    }
}