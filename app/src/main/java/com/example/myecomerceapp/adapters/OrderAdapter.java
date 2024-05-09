package com.example.myecomerceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.interfaces.MyOrderOnClickListener;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Product;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{

    private final ArrayList<Product> OrderedProducts;

    private final MyOrderOnClickListener myOrderOnClickListener;

    private final Context context;
  int newQuantity;


    public OrderAdapter(ArrayList<Product> orderedProducts, MyOrderOnClickListener myOrderOnClickListener, Context context) {
        OrderedProducts = orderedProducts;
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

    }

    @Override
    public int getItemCount() {
        return OrderedProducts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,price,quantity;


        public MyViewHolder(@NonNull View itemView, MyOrderOnClickListener orderOnclickListener) {
            super(itemView);
            image=itemView.findViewById(R.id.productImage);
            name=itemView.findViewById(R.id.productName);
            price=itemView.findViewById(R.id.productPrice);
            quantity=itemView.findViewById(R.id.productQuantity);




            itemView.setOnClickListener(v -> {
                int position=getAdapterPosition();
                orderOnclickListener.orderClicked(position);
            });
        }
    }
}
