package com.example.myecomerceapp.adapters;

import static com.example.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.example.myecomerceapp.activities.MainActivity.productsFavorited;

import android.content.Context;
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
import com.example.myecomerceapp.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    boolean productAlreadyInFavorites;
    private final MyProductOnClickListener productOnclickListener;
    private final List<Product> productArrayList;
    private final Context context;

    public ProductAdapter(MyProductOnClickListener productOnclickListener, List<Product> productArrayList, Context context) {
        this.productOnclickListener = productOnclickListener;
        this.productArrayList = productArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cardview,parent,false);
        return new MyViewHolder(view, productOnclickListener,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {

        Glide.with(context)
                .load(productArrayList.get(position).getProductImage())
                .into(holder.productImage);
        holder.productName.setText(productArrayList.get(position).getProductName());
        holder.productPrice.setText(productArrayList.get(position).getProductPrice());


            if (productsFavorited.contains(productArrayList.get(position))) {

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
                productsFavorited.remove(productArrayList.get(position));
                Glide.with(context)
                        .load(R.drawable.unfavorite)
                        .fitCenter()
                        .into(holder.favoriteBtn);
                Toast.makeText(context, "This " + productArrayList.get(position).getProductName() + " was removed from favorites. ", Toast.LENGTH_SHORT).show();
                productAlreadyInFavorites = false;
            } else {
                productsFavorited.add(productArrayList.get(position));
                productAlreadyInFavorites = true;
                Glide.with(context)
                        .load(R.drawable.favoriteicon2)
                        .fitCenter()
                        .into(holder.favoriteBtn);
                Toast.makeText(context, productArrayList.get(position).getProductName() + " was added to favorites.", Toast.LENGTH_SHORT).show();
            }
        });


        holder.addToCartBtn.setOnClickListener(v -> {
            boolean productAlreadyInCart = false;
            for (Product productInCart : productsAddedToCart) {
                if (productInCart.getProductName().equals(productArrayList.get(position).getProductName())) {
                    int newQuantity = productInCart.getProductQuantity() + productArrayList.get(position).getProductQuantity();
                    productInCart.setProductQuantity(newQuantity);
                    Toast.makeText(context, "Quantity of " + productArrayList.get(position).getProductName() + " increased to " + newQuantity, Toast.LENGTH_SHORT).show();
                    productAlreadyInCart = true;
                    break;
                }
            }
            if (!productAlreadyInCart) {
                productsAddedToCart.add(productArrayList.get(position));
                Toast.makeText(context, productArrayList.get(position).getProductName() + " was added to cart.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        ImageView favoriteBtn;
        ImageView addToCartBtn;
        public MyViewHolder(@NonNull View itemView, MyProductOnClickListener productOnclickListener,Context context) {
            super(itemView);

            productImage=itemView.findViewById(R.id.productImage);
            productName=itemView.findViewById(R.id.productTitle);
            productPrice=itemView.findViewById(R.id.Price);
            favoriteBtn=itemView.findViewById(R.id.favoritebtn);
            addToCartBtn=itemView.findViewById(R.id.addtocartbtn);

            Glide.with(context)
                    .load(R.drawable.unfavorite)
                    .fitCenter()
                    .into(favoriteBtn);

            Glide.with(context)
                    .load(R.drawable.addtocart)
                    .fitCenter()
                    .into(addToCartBtn);

            itemView.setOnClickListener(v -> {
                int position=getBindingAdapterPosition();
                productOnclickListener.productClicked(position);
            });
        }
    }
}

