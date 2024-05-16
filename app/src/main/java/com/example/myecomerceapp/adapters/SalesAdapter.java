package com.example.myecomerceapp.adapters;

import static com.example.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.example.myecomerceapp.activities.MainActivity.productsFavorited;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.interfaces.MySalesOnclickListener;
import com.example.myecomerceapp.models.Product;

import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder>{
    boolean productAlreadyInFavorites = false;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_cardview,parent,false);
        return new MyViewHolder(view,mySalesOnclickListener,context);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesAdapter.MyViewHolder holder, int position) {

        Glide.with(context)
                .load(salesArrayList.get(position).getProductImage())
                .into(holder.productImage);
        holder.productName.setText(salesArrayList.get(position).getProductName());
        holder.productPrice.setText(salesArrayList.get(position).getProductPrice());
        holder.productSalesPrice.setText(salesArrayList.get(position).getProductSalesPrice());


        if (productsFavorited.contains(salesArrayList.get(position))) {

            Glide.with(context)
                    .load(R.drawable.favoriteicon2)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            productAlreadyInFavorites =true;
        }else{

            Glide.with(context)
                    .load(R.drawable.unfavorite)
                    .fitCenter()
                    .into(holder.favoriteBtn);
            productAlreadyInFavorites = false;
        }


        holder.favoriteBtn.setOnClickListener(v -> {
            if (productAlreadyInFavorites) {
                productsFavorited.remove(salesArrayList.get(position));
                Glide.with(context)
                        .load(R.drawable.unfavorite)
                        .fitCenter()
                        .into(holder.favoriteBtn);
                Toast.makeText(context, "This " + salesArrayList.get(position).getProductName() + " was removed from favorites. ", Toast.LENGTH_SHORT).show();
                productAlreadyInFavorites = false;
            } else {
                productsFavorited.add(salesArrayList.get(position));
                productAlreadyInFavorites = true;
                Glide.with(context)
                        .load(R.drawable.favoriteicon2)
                        .fitCenter()
                        .into(holder.favoriteBtn);
                Toast.makeText(context, salesArrayList.get(position).getProductName() + " was added to favorites.", Toast.LENGTH_SHORT).show();
            }
        });


        holder.addToCartBtn.setOnClickListener(v -> {
            boolean productAlreadyInCart = false;
            for (Product productInCart : productsAddedToCart) {
                if (productInCart.getProductName().equals(salesArrayList.get(position).getProductName())) {
                    int newQuantity = productInCart.getProductQuantity() + salesArrayList.get(position).getProductQuantity();
                    productInCart.setProductQuantity(newQuantity);
                    Toast.makeText(context, "Quantity of " + salesArrayList.get(position).getProductName() + " increased to " + newQuantity, Toast.LENGTH_SHORT).show();
                    productAlreadyInCart = true;
                    break;
                }
            }
            if (!productAlreadyInCart) {
                productsAddedToCart.add(salesArrayList.get(position));
                Toast.makeText(context, salesArrayList.get(position).getProductName() + " was added to cart.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return salesArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        ImageView favoriteBtn;
        ImageView addToCartBtn;
        TextView productSalesPrice;

        public MyViewHolder(@NonNull View itemView, MySalesOnclickListener mySalesOnclickListener,Context context) {
            super(itemView);

            productImage=itemView.findViewById(R.id.productImage);
            productName=itemView.findViewById(R.id.productTitle);
            productPrice=itemView.findViewById(R.id.Price);
            favoriteBtn=itemView.findViewById(R.id.favoritebtn);
            addToCartBtn=itemView.findViewById(R.id.addtocartbtn);
            productSalesPrice=itemView.findViewById(R.id.SalesPrice);

            productPrice.setPaintFlags(productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Glide.with(context)
                    .load(R.drawable.addtocart)
                    .fitCenter()
                    .into(addToCartBtn);

            itemView.setOnClickListener(v -> {
                int position=getBindingAdapterPosition();
                mySalesOnclickListener.salesProductClicked(position);
            });
        }
    }

}
