package com.amar.myecomerceapp.adapters;

import static com.amar.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.amar.myecomerceapp.fragments.CartFragment.totalNumber;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;
import com.amar.myecomerceapp.models.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_cardview, parent, false);
        return new MyViewHolder(view, productOnclickListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {

        if (position >= 0 && position < cartProducts.size()) {
            Product product = cartProducts.get(position);

            Glide.with(context)
                    .load(product.getImage())
                    .into(holder.image);

            Glide.with(context)
                    .load(R.drawable.close)
                    .fitCenter()
                    .into(holder.close);

            holder.name.setText(product.getProductName());
            holder.quantity.setText(String.valueOf(product.getProductQuantity()));
            holder.price.setText(product.getProductPrice());

            holder.minus.setOnClickListener(v -> {
                int currentQuantity = product.getProductQuantity();
                newQuantity = currentQuantity - 1;
                if (newQuantity >= 1) {
                    product.setProductQuantity(newQuantity);
                    holder.quantity.setText(String.valueOf(newQuantity));
                    totalNumber.setText(calculateTotalFormatted());
                }
            });

            holder.plus.setOnClickListener(v -> {
                int currentQuantity = product.getProductQuantity();
                newQuantity = currentQuantity + 1;
                product.setProductQuantity(newQuantity);
                holder.quantity.setText(String.valueOf(newQuantity));
                totalNumber.setText(calculateTotalFormatted());
            });

            holder.close.setOnClickListener(v -> {
                productsAddedToCart.remove(product);
                notifyItemRemoved(position);
                totalNumber.setText(calculateTotalFormatted());
            });
        } else {
            // Logging for index out of bounds
            Log.e("CartAdapter", "Index " + position + " out of bounds for length " + cartProducts.size());
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
            total += (int) (price * product.getProductQuantity() * 100);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image, close;
        TextView name, price, quantity;
        Button minus;
        Button plus;

        public MyViewHolder(@NonNull View itemView, MyProductOnClickListener productOnclickListener, Context context) {
            super(itemView);
            image = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            quantity = itemView.findViewById(R.id.productQuantity);
            minus = itemView.findViewById(R.id.minusBtn);
            plus = itemView.findViewById(R.id.plusBtn);
            close = itemView.findViewById(R.id.close);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    productOnclickListener.productClicked(position);
                }
            });
        }
    }

}
