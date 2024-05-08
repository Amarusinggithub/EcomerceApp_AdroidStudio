package com.example.myecomerceapp.adapters;

import static com.example.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.example.myecomerceapp.fragments.CartFragment.totalNumber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    private final ArrayList<Product> cartProducts;

    private final MyProductOnClickListener productOnclickListener;

    private final Context context;
    private int newQuantity;

    public CartAdapter(ArrayList<Product> cartProducts, MyProductOnClickListener productOnclickListener, Context context) {
        this.cartProducts = cartProducts;
        this.productOnclickListener = productOnclickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_cardview,parent,false);
        return new MyViewHolder(view,productOnclickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        // Get product from the list
        Product product = cartProducts.get(position);

        if (product != null) {

            Glide.with(context)
                    .load(product.getProductImage())
                    .into(holder.image);

            holder.name.setText(product.getProductName());
            holder.quantity.setText(String.valueOf(product.getProductQuantity()));
            holder.price.setText(product.getProductPrice());

            holder.minus.setOnClickListener(v -> {
                 newQuantity = product.getProductQuantity() - 1;
                if (newQuantity >= 1) {
                    product.setProductQuantity(newQuantity);
                    holder.quantity.setText(String.valueOf( product.getProductQuantity()));
                    totalNumber.setText(calculateTotalFormatted());
                }
            });

            holder.plus.setOnClickListener(v -> {
                newQuantity = product.getProductQuantity() + 1;
                product.setProductQuantity(newQuantity);
                holder.quantity.setText(String.valueOf(newQuantity));
                totalNumber.setText(calculateTotalFormatted());
            });
        }
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    private int calculateTotal() {
        int total = 0;
        for (Product product : productsAddedToCart) {
            String[] priceParts = product.getProductPrice().split("\\$");
            String priceWithoutDollarSign = priceParts[1].replaceAll("[^\\d.]", "");
            double price = Double.parseDouble(priceWithoutDollarSign);

            total += (int) (price * newQuantity * 100);
        }
        return total;
    }


    private String formatTotal(int total) {

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

        double amount = total / 100.0;

        return format.format(amount);
    }

    private String calculateTotalFormatted() {
        int total = calculateTotal();
        return formatTotal(total);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,price,quantity;
        Button minus,plus;

        public MyViewHolder(@NonNull View itemView, MyProductOnClickListener productOnclickListener) {
            super(itemView);
            image=itemView.findViewById(R.id.productImage);
            name=itemView.findViewById(R.id.productName);
            price=itemView.findViewById(R.id.productPrice);
            quantity=itemView.findViewById(R.id.productQuantity);
            minus=itemView.findViewById(R.id.minusBtn);
            plus=itemView.findViewById(R.id.plusBtn);



            itemView.setOnClickListener(v -> {
                int position=getAdapterPosition();
                productOnclickListener.productClicked(position);
            });
        }
    }
}
