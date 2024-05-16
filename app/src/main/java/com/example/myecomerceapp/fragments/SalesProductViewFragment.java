package com.example.myecomerceapp.fragments;

import static com.example.myecomerceapp.activities.MainActivity.EVERY_PRODUCT;
import static com.example.myecomerceapp.activities.MainActivity.getProductsData;
import static com.example.myecomerceapp.activities.MainActivity.productInProductViewFragment;
import static com.example.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.example.myecomerceapp.activities.MainActivity.productsFavorited;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.Product;

import java.util.Objects;


public class SalesProductViewFragment extends Fragment {
    Button addToCart;
    Product product;
    ImageView addFavoriteBtn;
    ImageView shareBtn;
    ImageView backBtn;
    boolean productAlreadyInFavorites;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sales_product_view, container, false);
        initializeViewElements(view);
        return view;
    }
    private void initializeViewElements(View productView) {
        addToCart= productView.findViewById(R.id.addtocartbtn);
        addFavoriteBtn = productView.findViewById(R.id.favoritebtn);
        shareBtn= productView.findViewById(R.id.sharebtn);
        backBtn= productView.findViewById(R.id.backbtn);


        TextView productNameTextView =  productView.findViewById(R.id.Name);
        TextView productPriceTextView =  productView.findViewById(R.id.Price);
        TextView productSalesPriceTextView =  productView.findViewById(R.id.SalesPrice);
        TextView productDescriptionTextView =  productView.findViewById(R.id.Description);
        ImageView productImageView =  productView.findViewById(R.id.Image);

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
        productSalesPriceTextView.setText(getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductSalesPrice());
        productPriceTextView.setText(getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductPrice());
        productDescriptionTextView.setText(getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductDescription());
        Glide.with(this)
                .load(getProductsData(EVERY_PRODUCT).get(getProductInPosition()).getProductImage())
                .fitCenter()
                .into(productImageView);
        productPriceTextView.setPaintFlags(productPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

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
            if (getProductsData(EVERY_PRODUCT).get(getProductInPosition()) != null) {
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