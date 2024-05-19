package com.amar.myecomerceapp.fragments;


import static com.amar.myecomerceapp.activities.MainActivity.getCategory;
import static com.amar.myecomerceapp.activities.MainActivity.getProductsData;
import static com.amar.myecomerceapp.activities.MainActivity.productInProductViewFragment;
import static com.amar.myecomerceapp.adapters.ProductAdapter.filteredList;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.adapters.CategoryAdapter;
import com.amar.myecomerceapp.adapters.ProductAdapter;
import com.amar.myecomerceapp.interfaces.MyCategoryOnClickListener;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;
import com.amar.myecomerceapp.models.Category;

public class ProductRecyclerViewFragment extends Fragment implements MyProductOnClickListener, MyCategoryOnClickListener {
    public static  String categoryId;
    RecyclerView categoryRecycleView;
     ImageView favoriteIcon;
      CardView favoritesCd;

    EditText searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_products_recyclerview, container, false);
        searchView=view.findViewById(R.id.searchview);
        setupCategoryRecyclerView(view);
        setUpProductRecyclerView(view);
        return view;
    }

    private void setUpProductRecyclerView(View view) {
        favoritesCd=view.findViewById(R.id.favoritecd);
        RecyclerView productRecyclerView = view.findViewById( R.id.recyclerview);
        favoriteIcon=view.findViewById(R.id.favorites);
        Glide.with(this)
                .load(R.drawable.favoriteicon2)
                .fitCenter()
                .into(favoriteIcon);
        ProductAdapter productAdapter = new ProductAdapter(this, getProductsData(categoryId),getContext());
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        productRecyclerView.setLayoutManager(layoutManager);
        productRecyclerView.setAdapter(productAdapter);

        favoritesCd.setOnClickListener(v -> loadFragment(new FavoritesFragment()));

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && productAdapter!= null) {
                    productAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("productsRecyclerview");
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void setupCategoryRecyclerView(View view) {
        categoryRecycleView=view.findViewById(R.id.categoriesRecycleView);
        categoryRecycleView.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecycleView.setLayoutManager(linearLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getCategory(),getContext());
        categoryRecycleView.setAdapter(categoryAdapter);
    }

    @Override
    public void productClicked(int position) {
       productInProductViewFragment = filteredList.get(position);
        loadFragment(new ProductViewFragment());
    }

    @Override
    public void categoryClicked(int position) {
        categoryRecycleView.setVisibility(View.VISIBLE);
        Category category = getCategory().get(position);
        categoryId= category.getCategoryId();
        loadFragment(new ProductRecyclerViewFragment());
    }
}