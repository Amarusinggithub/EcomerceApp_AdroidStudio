package com.example.myecomerceapp.fragments;


import static com.example.myecomerceapp.activities.MainActivity.productInProductViewFragment;
import static com.example.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.example.myecomerceapp.activities.MainActivity.productsFavorited;


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

import com.bumptech.glide.Glide;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.Product;


public class ProductViewFragment extends Fragment {
    Bundle args;
    Button addToCart;
   Product product;
    ImageView addFavoriteBtn;
    ImageView shareBtn;
    ImageView backBtn;
    boolean productAlreadyInFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View productView=inflater.inflate(R.layout.fragment_productview, container, false);
        initializeViewElements(productView);

        return productView;
    }

    private void initializeViewElements(View productView) {
        addToCart= productView.findViewById(R.id.addtocartbtn);
        addFavoriteBtn = productView.findViewById(R.id.favoritebtn);
        shareBtn= productView.findViewById(R.id.sharebtn);
        backBtn= productView.findViewById(R.id.backbtn);


            TextView productNameTextView =  productView.findViewById(R.id.Name);
            TextView productPriceTextView =  productView.findViewById(R.id.Price);
            TextView productDescriptionTextView =  productView.findViewById(R.id.Description);
            ImageView productImageView =  productView.findViewById(R.id.Image);

            if (!productsFavorited.contains(product)) {
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


            productNameTextView.setText(productInProductViewFragment.getProductName());
            productPriceTextView.setText(productInProductViewFragment.getProductPrice());
            productDescriptionTextView.setText(productInProductViewFragment.getProductDescription());
            Glide.with(this)
                    .load(productInProductViewFragment.getProductImage())
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
                productsFavorited.remove(productInProductViewFragment);
                Glide.with(requireContext())
                        .load(R.drawable.unfavorite)
                        .fitCenter()
                        .into(addFavoriteBtn);
                Toast.makeText(getContext(), "This " + productInProductViewFragment.getProductName() + " was removed from favorites. ", Toast.LENGTH_SHORT).show();
                productAlreadyInFavorites = false;
            } else {
                productsFavorited.add(productInProductViewFragment);
                productAlreadyInFavorites = true;
                Glide.with(requireContext())
                        .load(R.drawable.favoriteicon2)
                        .fitCenter()
                        .into(addFavoriteBtn);
                Toast.makeText(getContext(), productInProductViewFragment.getProductName() + " was added to favorites.", Toast.LENGTH_SHORT).show();
            }
        });


        addToCart.setOnClickListener(v -> {
            if (productInProductViewFragment != null) {
                boolean productAlreadyInCart = false;
                for (Product productInCart : productsAddedToCart) {
                    if (productInCart.getProductName().equals(productInProductViewFragment.getProductName())) {
                        int newQuantity = productInCart.getProductQuantity() + productInProductViewFragment.getProductQuantity();
                        productInCart.setProductQuantity(newQuantity);
                        Toast.makeText(getContext(), "Quantity of " + productInProductViewFragment.getProductName() + " increased to " + newQuantity, Toast.LENGTH_SHORT).show();
                        productAlreadyInCart = true;
                        break;
                    }
                }
                if (!productAlreadyInCart) {
                    productsAddedToCart.add(productInProductViewFragment);
                    Toast.makeText(getContext(), productInProductViewFragment.getProductName() + " was added to cart.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("ProductViewFragment", "The product is null");
            }
        });

    }


}