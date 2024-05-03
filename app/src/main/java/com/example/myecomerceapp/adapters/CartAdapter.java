package com.example.myecomerceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Product;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    private final ArrayList<Product> cartProducts;

    private final MyProductOnClickListener productOnclickListener;

    private final Context context;

    public CartAdapter(ArrayList<Product> cartProducts, MyProductOnClickListener productOnclickListener, Context context) {
        this.cartProducts = cartProducts;
        this.productOnclickListener = productOnclickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cardview,parent,false);
        return new MyViewHolder(view,productOnclickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        // Get all products from the HashMap
        Product product =cartProducts.get(position);


        if (product!=null){


            Glide.with(context)
                    .load(product.getProductImage())
                    .into(holder.image);
            holder.name.setText(product.getProductName());
            holder.quantity.setText(product.getProductQuantity());
            holder.price.setText(product.getProductPrice());

        }
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,price,quantity;

        public MyViewHolder(@NonNull View itemView, MyProductOnClickListener productOnclickListener) {
            super(itemView);
            image=itemView.findViewById(R.id.productImage);
            name=itemView.findViewById(R.id.productName);
            price=itemView.findViewById(R.id.productPrice);
            quantity=itemView.findViewById(R.id.productQuantity);



            itemView.setOnClickListener(v -> {
                int position=getAdapterPosition();
                productOnclickListener.productClicked(position);
            });
        }
    }
}
