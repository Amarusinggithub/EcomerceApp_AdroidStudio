package com.amar.myecomerceapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.interfaces.MyOrderOnClickListener;
import com.amar.myecomerceapp.models.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{
    ArrayList<Product> orderedProducts;
    MyOrderOnClickListener myOrderOnClickListener;
    Context context;

    public OrderAdapter(ArrayList<Product> orderedProducts, MyOrderOnClickListener myOrderOnClickListener, Context context) {
        this.orderedProducts = orderedProducts;
        this.myOrderOnClickListener = myOrderOnClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_cardview,parent,false);
        return new MyViewHolder(view, myOrderOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        if (position >= 0 && position < orderedProducts.size()) {
            Product currentProduct = orderedProducts.get(position);
            Glide.with(context)
                    .load(currentProduct.getImage())
                    .fitCenter()
                    .into(holder.image);
            holder.name.setText(currentProduct.getProductName());
            holder.quantity.setText(String.valueOf(currentProduct.getProductQuantity()));
            holder.price.setText(currentProduct.getProductPrice());
            holder.dateAndTime.setText(currentProduct.getDateAndTimeout());
            holder.address.setText(currentProduct.getAddressDeliveredTo());
        } else {
            Log.e("OrderAdapter", "Index " + position + " out of bounds for length " + orderedProducts.size());
        }
    }

    @Override
    public int getItemCount() {
        return orderedProducts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView price;
        TextView quantity;
        TextView address;
        TextView dateAndTime;


        public MyViewHolder(@NonNull View itemView, MyOrderOnClickListener orderOnclickListener) {
            super(itemView);
            image=itemView.findViewById(R.id.productImage);
            name=itemView.findViewById(R.id.productName);
            price=itemView.findViewById(R.id.productPrice);
            quantity=itemView.findViewById(R.id.productQuantity);
            address=itemView.findViewById(R.id.productAddress);
            dateAndTime=itemView.findViewById(R.id.productDateAndTime);


            itemView.setOnClickListener(v -> {
                int position=getBindingAdapterPosition();
                orderOnclickListener.orderClicked(position);
            });
        }
    }
}
