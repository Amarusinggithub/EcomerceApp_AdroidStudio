package com.amar.myecomerceapp.adapters;

import static com.amar.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.amar.myecomerceapp.activities.MainActivity.productsFavorited;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;
import com.amar.myecomerceapp.models.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.MyViewHolder>{
    private final MyProductOnClickListener productOnclickListener;
    private final List<Product> originalList;
    public static List<Product> filteredList;
    private final Context context;

    public PopularProductAdapter(MyProductOnClickListener productOnclickListener, List<Product> productArrayList, Context context) {
        this.productOnclickListener = productOnclickListener;
        this.originalList = productArrayList;
        this.filteredList = new ArrayList<>(productArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public PopularProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cardview, parent, false);
        return new PopularProductAdapter.MyViewHolder(view, productOnclickListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductAdapter.MyViewHolder holder, int position) {
        if (position >= 0 && position < filteredList.size()) {
            Product currentProduct = filteredList.get(position);

            Glide.with(context)
                    .load(currentProduct.getProductImage())
                    .into(holder.productImage);

            holder.productName.setText(currentProduct.getProductName());
            holder.productPrice.setText(currentProduct.getProductPrice());

            updateFavoriteIcon(holder, currentProduct);

            holder.favoriteBtn.setOnClickListener(v -> toggleFavorite(holder, currentProduct));
            holder.addToCartBtn.setOnClickListener(v -> addToCart(currentProduct));

        } else {
            Log.e("ProductAdapter", "Index " + position + " out of bounds for length " + filteredList.size());
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        if (query.isEmpty()) {
            filteredList = new ArrayList<>(originalList);
        } else {
            List<Product> filtered = new ArrayList<>();
            for (Product product : originalList) {
                if (product.getProductName().toLowerCase().contains(query.toLowerCase())) {
                    filtered.add(product);
                }
            }
            filteredList = filtered;
        }
        notifyDataSetChanged();
    }

    private void updateFavoriteIcon(PopularProductAdapter.MyViewHolder holder, Product product) {
        if (productsFavorited.contains(product)) {
            Glide.with(context)
                    .load(R.drawable.favoriteicon2)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            holder.productAlreadyInFavorites = true;
        } else {
            Glide.with(context)
                    .load(R.drawable.unfavorite)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            holder.productAlreadyInFavorites = false;
        }
    }

    private void toggleFavorite(PopularProductAdapter.MyViewHolder holder, Product product) {
        if (holder.productAlreadyInFavorites) {
            productsFavorited.remove(product);
            Glide.with(context)
                    .load(R.drawable.unfavorite)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            Toast.makeText(context, "This " + product.getProductName() + " was removed from favorites.", Toast.LENGTH_SHORT).show();
            holder.productAlreadyInFavorites = false;
        } else {
            productsFavorited.add(product);
            Glide.with(context)
                    .load(R.drawable.favoriteicon2)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            Toast.makeText(context, product.getProductName() + " was added to favorites.", Toast.LENGTH_SHORT).show();
            holder.productAlreadyInFavorites = true;
        }
    }

    private void addToCart(Product product) {
        boolean productAlreadyInCart = false;
        for (Product productInCart : productsAddedToCart) {
            if (productInCart.getProductName().equals(product.getProductName())) {
                int newQuantity = productInCart.getProductQuantity() + product.getProductQuantity();
                productInCart.setProductQuantity(newQuantity);
                Toast.makeText(context, "Quantity of " + product.getProductName() + " increased to " + newQuantity, Toast.LENGTH_SHORT).show();
                productAlreadyInCart = true;
                break;
            }
        }
        if (!productAlreadyInCart) {
            productsAddedToCart.add(product);
            Toast.makeText(context, product.getProductName() + " was added to cart.", Toast.LENGTH_SHORT).show();
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
       ImageView productImage;
       TextView productName;
       TextView productPrice;
       ImageView favoriteBtn;
       ImageView addToCartBtn;
       boolean productAlreadyInFavorites;
       public MyViewHolder(@NonNull View itemView,MyProductOnClickListener productOnClickListener,Context context) {
           super(itemView);

           productImage = itemView.findViewById(R.id.productImage);
           productName = itemView.findViewById(R.id.productTitle);
           productPrice = itemView.findViewById(R.id.Price);
           favoriteBtn = itemView.findViewById(R.id.favoritebtn);
           addToCartBtn = itemView.findViewById(R.id.addtocartbtn);

           Glide.with(context)
                   .load(R.drawable.addtocart)
                   .fitCenter()
                   .into(addToCartBtn);

           itemView.setOnClickListener(v -> {
               int position = getBindingAdapterPosition();
               if (position != RecyclerView.NO_POSITION) {
                   productOnClickListener.productClicked(position);
               }
           });
       }
       }
   }

