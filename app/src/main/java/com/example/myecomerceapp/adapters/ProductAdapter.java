package com.example.myecomerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.interfaces.MyOnClickInterface;
import com.example.myecomerceapp.models.ProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private final MyOnClickInterface productOnclickListiner;
    private List<ProductModel> productModelArrayList;

    public ProductAdapter(MyOnClickInterface productOnclickListiner, List<ProductModel> productModelArrayList) {
        this.productOnclickListiner = productOnclickListiner;
        this.productModelArrayList = productModelArrayList;
    }


    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_cardview,parent,false);
        return new MyViewHolder(view,productOnclickListiner);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        ProductModel product=productModelArrayList.get(position);
        holder.productImage.setImageResource(product.getProductImage());
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice());
    }

    @Override
    public int getItemCount() {
        return productModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        public MyViewHolder(@NonNull View itemView,MyOnClickInterface productOnclickListiner) {
            super(itemView);

            productImage=itemView.findViewById(R.id.productImage);
            productName=itemView.findViewById(R.id.productTitle);
            productPrice=itemView.findViewById(R.id.Price);

            itemView.setOnClickListener(v -> {
                int position=getAdapterPosition();
                productOnclickListiner.onClicked(position);
            });
        }
    }
}

