package com.amar.myecomerceapp.fragments;




import static com.amar.myecomerceapp.activities.MainActivity.productsAddedToCart;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.adapters.CartAdapter;

import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;
import com.amar.myecomerceapp.models.Product;


import java.text.NumberFormat;
import java.util.Locale;


public class CartFragment extends Fragment implements MyProductOnClickListener {
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CartAdapter cartAdapter;
    Button checkOutBtn;
    ImageView emptyCartImage;
    TextView totalText;
    public static TextView totalNumber;
    View line;

    @Override
    public View onCreateView(@NonNull LayoutInflater  inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initializeViewElements(view);
        return view;
    }

    private void initializeViewElements(View view) {

        checkOutBtn= view.findViewById(R.id.checkoutbtn);
        recyclerView= view.findViewById(R.id.recyclerview);
        emptyCartImage= view.findViewById(R.id.cartisemptyimage);
        totalText= view.findViewById(R.id.totaltext);
        totalNumber= view.findViewById(R.id.totalnumber);
        line= view.findViewById(R.id.line);


        if (!productsAddedToCart.isEmpty()){
            setUpCartRecyclerView();
        }else{
            setupCartEmptyView();
        }



    }



    private void setupCartEmptyView() {
        emptyCartImage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        checkOutBtn.setVisibility(View.GONE);
        totalText.setVisibility(View.GONE);
        totalNumber.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        Glide.with(this)
                .load(R.drawable.emptycart)
                .fitCenter()
                .into(emptyCartImage);
    }



    private void setupCheckOutBtn() {
        checkOutBtn.setOnClickListener(v -> {

        });
    }

    private void setUpCartRecyclerView() {
        emptyCartImage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        checkOutBtn.setVisibility(View.VISIBLE);
        totalText.setVisibility(View.VISIBLE);
        totalNumber.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        cartAdapter= new CartAdapter(productsAddedToCart,this,getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartAdapter);
        setupCheckOutBtn();

        totalNumber.setText( calculateTotalFormatted());
    }

    private int calculateTotal() {
        int total = 0;
        for (Product product : productsAddedToCart) {
            String[] priceParts = product.getProductPrice().split("\\$");
            String priceWithoutDollarSign = priceParts[1].replaceAll("[^\\d.]", "");
            double price = Double.parseDouble(priceWithoutDollarSign);
            int quantity = product.getProductQuantity();
            total += (int) (price * quantity * 100);
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


    @Override
    public void productClicked(int position) {
        Product product=productsAddedToCart.get(position);
        if (product != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putString("productName", product.getProductName());
            bundle.putString("proPrice", product.getProductPrice());
            bundle.putString("productDescription", product.getProductDescription());
            bundle.putString("position", product.getProductId());
            bundle.putInt("productImage", product.getProductImage());
            ProductViewFragment productViewFragment = new ProductViewFragment();
            productViewFragment.setArguments(bundle);
            loadFragment(productViewFragment);

        }else {
            Log.d(TAG,"The product is null");
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack("cartFragment");
        fragmentTransaction.commit();
    }

}