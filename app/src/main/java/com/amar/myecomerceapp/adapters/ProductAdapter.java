package com.amar.myecomerceapp.adapters;

import static com.amar.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.amar.myecomerceapp.activities.MainActivity.productsFavorited;

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

import com.bumptech.glide.Glide;
import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;
import com.amar.myecomerceapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private final MyProductOnClickListener productOnclickListener;
    private final List<Product> originalList;
    public static List<Product> filteredList;
    private final Context context;

    public ProductAdapter(MyProductOnClickListener productOnclickListener, List<Product> productArrayList, Context context) {
        this.productOnclickListener = productOnclickListener;
        this.originalList = productArrayList;
        this.filteredList = new ArrayList<>(productArrayList); // Initialize filteredList with the original data
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cardview, parent, false);
        return new MyViewHolder(view, productOnclickListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        if (position >= 0 && position < filteredList.size()) {
            Product currentProduct = filteredList.get(position);

            Glide.with(context)
                    .load(currentProduct.getProductImage())
                    .into(holder.productImage);
            holder.productName.setText(currentProduct.getProductName());
            holder.productPrice.setText(currentProduct.getProductPrice());

            if (productsFavorited.contains(currentProduct)) {
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

            holder.favoriteBtn.setOnClickListener(v -> {
                int adapterPosition = holder.getBindingAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < filteredList.size()) {
                    Product clickedProduct = filteredList.get(adapterPosition);
                    if (holder.productAlreadyInFavorites) {
                        productsFavorited.remove(clickedProduct);
                        Glide.with(context)
                                .load(R.drawable.unfavorite)
                                .fitCenter()
                                .into(holder.favoriteBtn);
                        Toast.makeText(context, "This " + clickedProduct.getProductName() + " was removed from favorites.", Toast.LENGTH_SHORT).show();
                        holder.productAlreadyInFavorites = false;
                    } else {
                        productsFavorited.add(clickedProduct);
                        Glide.with(context)
                                .load(R.drawable.favoriteicon2)
                                .fitCenter()
                                .into(holder.favoriteBtn);
                        Toast.makeText(context, clickedProduct.getProductName() + " was added to favorites.", Toast.LENGTH_SHORT).show();
                        holder.productAlreadyInFavorites = true;
                    }
                }
            });

            holder.addToCartBtn.setOnClickListener(v -> {
                int adapterPosition = holder.getBindingAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < filteredList.size()) {
                    Product clickedProduct = filteredList.get(adapterPosition);
                    boolean productAlreadyInCart = false;
                    for (Product productInCart : productsAddedToCart) {
                        if (productInCart.getProductName().equals(clickedProduct.getProductName())) {
                            int newQuantity = productInCart.getProductQuantity() + clickedProduct.getProductQuantity();
                            productInCart.setProductQuantity(newQuantity);
                            Toast.makeText(context, "Quantity of " + clickedProduct.getProductName() + " increased to " + newQuantity, Toast.LENGTH_SHORT).show();
                            productAlreadyInCart = true;
                            break;
                        }
                    }
                    if (!productAlreadyInCart) {
                        productsAddedToCart.add(clickedProduct);
                        Toast.makeText(context, clickedProduct.getProductName() + " was added to cart.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // Logging for index out of bounds
            Log.e("ProductAdapter", "Index " + position + " out of bounds for length " + filteredList.size());
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        ImageView favoriteBtn;
        ImageView addToCartBtn;
        boolean productAlreadyInFavorites;

        public MyViewHolder(@NonNull View itemView, MyProductOnClickListener productOnclickListener, Context context) {
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
                    productOnclickListener.productClicked(position);
                }
            });
        }
    }
}