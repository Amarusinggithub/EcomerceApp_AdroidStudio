package com.amar.myecomerceapp.adapters;

import static com.amar.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.amar.myecomerceapp.activities.MainActivity.productsFavorited;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
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
import com.amar.myecomerceapp.interfaces.MySalesOnclickListener;
import com.amar.myecomerceapp.models.Product;

import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder> {
    private final MySalesOnclickListener mySalesOnclickListener;
    private final List<Product> salesArrayList;
    private final Context context;

    public SalesAdapter(MySalesOnclickListener mySalesOnclickListener, List<Product> salesArrayList, Context context) {
        this.mySalesOnclickListener = mySalesOnclickListener;
        this.salesArrayList = salesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SalesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_cardview, parent, false);
        return new MyViewHolder(view, mySalesOnclickListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesAdapter.MyViewHolder holder, int position) {

        if (position >= 0 && position < salesArrayList.size()) {
            Product currentProduct = salesArrayList.get(position);

            Glide.with(context)
                    .load(currentProduct.getImage())
                    .into(holder.productImage);

            holder.productName.setText(currentProduct.getProductName());
            holder.productPrice.setText(currentProduct.getProductPrice());
            holder.productSalesPrice.setText(currentProduct.getProductSalesPrice());

            updateFavoriteIcon(holder, currentProduct);

            holder.favoriteBtn.setOnClickListener(v -> toggleFavorite(holder, currentProduct));
            holder.addToCartBtn.setOnClickListener(v -> addToCart(currentProduct));

        } else {
            Log.e("ProductAdapter", "Index " + position + " out of bounds for length " + salesArrayList.size());
        }
    }

    @Override
    public int getItemCount() {
        return salesArrayList.size();
    }

    private void updateFavoriteIcon(SalesAdapter.MyViewHolder holder, Product product) {
        if (productsFavorited.contains(product)) {
            Glide.with(context)
                    .load(R.drawable.favoriteicon2)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            product.setProductAlreadyInFavorites(true);
        } else {
            Glide.with(context)
                    .load(R.drawable.unfavorite)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            product.setProductAlreadyInFavorites(false);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void toggleFavorite(SalesAdapter.MyViewHolder holder, Product product) {
        if (product.isProductAlreadyInFavorites()) {
            productsFavorited.remove(product);
            Glide.with(context)
                    .load(R.drawable.unfavorite)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            Toast.makeText(context, "This " + product.getProductName() + " was removed from favorites.", Toast.LENGTH_SHORT).show();
            product.setProductAlreadyInFavorites(false);
        } else {
            productsFavorited.add(product);
            Glide.with(context)
                    .load(R.drawable.favoriteicon2)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            Toast.makeText(context, product.getProductName() + " was added to favorites.", Toast.LENGTH_SHORT).show();
            product.setProductAlreadyInFavorites(true);
        }
        notifyDataSetChanged();
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        ImageView favoriteBtn;
        ImageView addToCartBtn;
        TextView productSalesPrice;


        public MyViewHolder(@NonNull View itemView, MySalesOnclickListener mySalesOnclickListener, Context context) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.Price);
            favoriteBtn = itemView.findViewById(R.id.favoritebtn);
            addToCartBtn = itemView.findViewById(R.id.addtocartbtn);
            productSalesPrice = itemView.findViewById(R.id.SalesPrice);

            productPrice.setPaintFlags(productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Glide.with(context)
                    .load(R.drawable.addtocart)
                    .fitCenter()
                    .into(addToCartBtn);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mySalesOnclickListener.salesProductClicked(position);
                }
            });
        }
    }
}
