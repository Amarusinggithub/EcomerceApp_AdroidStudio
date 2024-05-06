package com.example.myecomerceapp.fragments;

import static com.example.myecomerceapp.activities.MainActivity.frameLayout;
import static com.example.myecomerceapp.activities.MainActivity.removeViews;
import static com.example.myecomerceapp.activities.MainActivity.username;
import static com.example.myecomerceapp.fragments.CartFragment.productsAddedToCart;

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
import com.example.myecomerceapp.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class ProductViewFragment extends Fragment {
    Button addToCart;
    Product product;
     User user;
     ImageView favoriteBtn;
    ImageView shareBtn;
    ImageView backBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        removeViews();
        frameLayout.setVisibility(View.VISIBLE);

        View productView=inflater.inflate(R.layout.fragment_productview, container, false);
        addToCart=(productView).findViewById(R.id.addtocartbtn);
        favoriteBtn=(productView).findViewById(R.id.favoritebtn);
        shareBtn=(productView).findViewById(R.id.sharebtn);
        backBtn=(productView).findViewById(R.id.backbtn);

        Bundle args = getArguments();
        if (args != null) {


            // Extract data from the Bundle
            String productId=args.getString("position");
            String productName = args.getString("productName");
            String productPrice = args.getString("productPrice");
            String productDescription = args.getString("productDescription");
            int productImageResource = args.getInt("productImage");

            Glide.with(requireContext())
                    .load(R.drawable.share)
                    .fitCenter()
                    .into(shareBtn);

            Glide.with(requireContext())
                    .load(R.drawable.back)
                    .fitCenter()
                    .into(backBtn);

            if(product==null){
                product=new Product(productImageResource,productName,productPrice,productDescription,productId);


                if (Boolean.TRUE.equals(product.getIsFavorite())) {
                    Glide.with(requireContext())
                            .load(R.drawable.favorite)
                            .fitCenter()
                            .into(favoriteBtn);


                } else {

                    Glide.with(this)
                            .load(R.drawable.unfavorite)
                            .fitCenter()
                            .into(favoriteBtn);
                }

            }else {
                Log.d("ProductVIewFragment","The product is null");
            }

            TextView productNameTextView =  productView.findViewById(R.id.Name);
            TextView productPriceTextView =  productView.findViewById(R.id.Price);
            TextView productDescriptionTextView =  productView.findViewById(R.id.Description);
            ImageView productImageView =  productView.findViewById(R.id.Image);

            productNameTextView.setText(productName);
            productPriceTextView.setText(productPrice);
            productDescriptionTextView.setText(productDescription);
            Glide.with(this)
                    .load(product.getProductImage())
                    .fitCenter()
                    .into(productImageView);


            favoriteBtn.setOnClickListener(v -> {
                if (Boolean.FALSE.equals(product.getIsFavorite())) {

                    Glide.with(requireContext())
                            .load(R.drawable.favorite)
                            .fitCenter()
                            .into(favoriteBtn);

                    product.setIsFavorite(true);
                } else if (Boolean.TRUE.equals(product.getIsFavorite())) {

                    Glide.with(this)
                            .load(R.drawable.unfavorite)
                            .fitCenter()
                            .into(favoriteBtn);

                    product.setIsFavorite(false);

                }
            });


            addToCart.setOnClickListener(v -> {
                if(product!=null){
                    productsAddedToCart.add(product);
                    if(!productsAddedToCart.contains(product)){
                        Toast.makeText(getContext(), " "+product.getProductName()+" "+"was added to cart.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), " THis product is already in your cart",Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Log.d("ProductVIewFragment","The product is null");
                }

            });

        }

        return productView;
    }



}