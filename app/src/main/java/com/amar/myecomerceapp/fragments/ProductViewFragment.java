package com.amar.myecomerceapp.fragments;


import static com.amar.myecomerceapp.activities.MainActivity.EVERY_PRODUCT;
import static com.amar.myecomerceapp.activities.MainActivity.getProductsData;
import static com.amar.myecomerceapp.activities.MainActivity.productInProductViewFragment;
import static com.amar.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.amar.myecomerceapp.activities.MainActivity.productsFavorited;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.models.Product;

import java.util.Objects;


public class ProductViewFragment extends Fragment {
    Button addToCart;
    Button buyBtn;
    ImageView addFavoriteBtn;
    ImageView shareBtn;
    ImageView backBtn;
    boolean productAlreadyInFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_productview, container, false);
        initializeViewElements(view);

        return view;
    }

    private void initializeViewElements(View view) {
        addToCart= view.findViewById(R.id.addtocartbtn);
        addFavoriteBtn = view.findViewById(R.id.favoritebtn);
        shareBtn= view.findViewById(R.id.sharebtn);
        backBtn= view.findViewById(R.id.backbtn);
        buyBtn=view.findViewById(R.id.buyBtn);


            TextView productNameTextView =  view.findViewById(R.id.Name);
            TextView productPriceTextView =  view.findViewById(R.id.Price);
            TextView productDescriptionTextView =  view.findViewById(R.id.Description);
            ImageView productImageView =  view.findViewById(R.id.Image);



            if (!productsFavorited.contains(getProductsData(EVERY_PRODUCT).get(getProductInPosition()))) {
                productAlreadyInFavorites = false;
                Glide.with(requireContext())
                        .load(R.drawable.unfavorite)
                        .fitCenter()
                        .into(addFavoriteBtn);
            }else{
                Glide.with(requireContext())
                        .load(R.drawable.favoriteicon2)
                        .fitCenter()
                        .into(addFavoriteBtn);
                productAlreadyInFavorites =true;
            }

            productNameTextView.setText(getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductName());
            productPriceTextView.setText(getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductPrice());
            productDescriptionTextView.setText(getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductDescription());
            Glide.with(this)
                    .load(getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductImage())
                    .fitCenter()
                    .into(productImageView);


        Glide.with(requireContext())
                .load(R.drawable.share)
                .fitCenter()
                .into(shareBtn);

        Glide.with(requireContext())
                .load(R.drawable.back)
                .fitCenter()
                .into(backBtn);

        backBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        addFavoriteBtn.setOnClickListener(v -> {
            if (productAlreadyInFavorites) {
                productsFavorited.remove(getProductsData(EVERY_PRODUCT).get(getProductInPosition()));
                Glide.with(requireContext())
                        .load(R.drawable.unfavorite)
                        .fitCenter()
                        .into(addFavoriteBtn);
                Toast.makeText(getContext(), "This " + getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductName() + " was removed from favorites. ", Toast.LENGTH_SHORT).show();
                productAlreadyInFavorites = false;
            } else {
                productsFavorited.add(getProductsData(EVERY_PRODUCT).get(getProductInPosition()));
                productAlreadyInFavorites = true;
                Glide.with(requireContext())
                        .load(R.drawable.favoriteicon2)
                        .fitCenter()
                        .into(addFavoriteBtn);
                Toast.makeText(getContext(), getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductName() + " was added to favorites.", Toast.LENGTH_SHORT).show();
            }
        });


        addToCart.setOnClickListener(v -> {
            if (productInProductViewFragment != null) {
                boolean productAlreadyInCart = false;
                for (Product productInCart : productsAddedToCart) {
                    if (productInCart.getProductName().equals(getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductName())) {
                        int newQuantity = productInCart.getProductQuantity() + getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductQuantity();
                        productInCart.setProductQuantity(newQuantity);
                        Toast.makeText(getContext(), "Quantity of " + getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductName() + " increased to " + newQuantity, Toast.LENGTH_SHORT).show();
                        productAlreadyInCart = true;
                        break;
                    }
                }
                if (!productAlreadyInCart) {
                    productsAddedToCart.add(getProductsData(EVERY_PRODUCT).get(getProductInPosition()));
                    Toast.makeText(getContext(), getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductName() + " was added to cart.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("ProductViewFragment", "The product is null");
            }
        });

        buyBtn.setOnClickListener(v -> {

        });

    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("ProductViewFragment");
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public int getProductInPosition(){
        int position=0;
        for(Product productInEveryThing: getProductsData(EVERY_PRODUCT)){
            if(Objects.equals(productInEveryThing.getProductName(), productInProductViewFragment.getProductName())){
                position=getProductsData(EVERY_PRODUCT).indexOf(productInEveryThing);
            }
        }
        return position;
    }


}