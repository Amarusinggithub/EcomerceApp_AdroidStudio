package com.example.myecomerceapp.fragments;



import static com.example.myecomerceapp.activities.MainActivity.getCategory;
import static com.example.myecomerceapp.activities.MainActivity.getProductsData;




import android.os.Bundle;

import androidx.cardview.widget.CardView;
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
import com.example.myecomerceapp.adapters.CategoryAdapter;
import com.example.myecomerceapp.adapters.ProductAdapter;
import com.example.myecomerceapp.interfaces.MyCategoryOnClickListener;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Category;
import com.example.myecomerceapp.models.Product;

public class ProductRecyclerViewFragment extends Fragment implements MyProductOnClickListener, MyCategoryOnClickListener {
    public static  String categoryId;
    RecyclerView categoryRecycleView;
     ImageView favoriteIcon;
      CardView favoritesCd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_products_recyclerview, container, false);
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
    }


    private  void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("products recyclerview");
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

    @Override
    public void categoryClicked(int position) {
        categoryRecycleView.setVisibility(View.VISIBLE);
        Category category = getCategory().get(position);
        ProductRecyclerViewFragment productRecyclerViewFragment =new ProductRecyclerViewFragment();
        categoryId= category.getCategoryId();
        loadFragment(productRecyclerViewFragment);
    }
}