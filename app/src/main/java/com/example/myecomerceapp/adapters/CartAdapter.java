package com.example.myecomerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Product;

import java.util.HashMap;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    private final HashMap<Product,Integer> cartProducts;

    private final MyProductOnClickListener productOnclickListener;

    public CartAdapter(HashMap<Product, Integer> cartProducts, MyProductOnClickListener productOnclickListener) {
        this.cartProducts = cartProducts;
        this.productOnclickListener = productOnclickListener;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_cardview,parent,false);
        return new MyViewHolder(view,productOnclickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        public MyViewHolder(@NonNull View itemView, MyProductOnClickListener productOnclickListener) {
            super(itemView);


            itemView.setOnClickListener(v -> {
                int position=getAdapterPosition();
                productOnclickListener.productClicked(position);
            });
        }
    }
}
