package com.example.myecomerceapp.fragments;




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
import android.widget.Toast;

import com.example.myecomerceapp.R;
import com.example.myecomerceapp.adapters.CartAdapter;

import com.example.myecomerceapp.interfaces.MyProductOnClickListener;
import com.example.myecomerceapp.models.Product;
import com.example.myecomerceapp.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class CartFragment extends Fragment implements MyProductOnClickListener {
    RecyclerView recyclerView;
    public static ArrayList<Product> productsAddedToCart;
    String username;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        if(bundle!=null){
            username=bundle.getString("username");
            getUserFromDatabase();
        }


        if(user!=null){
            productsAddedToCart=user.getProducts();
        }

        // Inflate the layout for this fragment
        View cartView = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView=cartView.findViewById(R.id.recyclerview);
        CartAdapter cartAdapter= new CartAdapter(productsAddedToCart,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartAdapter);
        return cartView;
    }

    @Override
    public void productClicked(int position) {
        // Get all products from the HashMap
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
            Log.d("CartFragment","The product is null");
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment); // R.id.fragment_container is the id of the FrameLayout in your activity's layout XML where you want to replace the fragment
        fragmentTransaction.addToBackStack("ProductDetailsTransaction"); // This allows the user to navigate back to the previous fragment when pressing the back button
        fragmentTransaction.commit();
    }

    private void getUserFromDatabase() {


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference parentReference = firebaseDatabase.getReference("MyDatabase");
        DatabaseReference usersReference = parentReference.child("users");
        Query checkUser = usersReference.orderByChild("username").equalTo(username);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        if(Objects.equals(Objects.requireNonNull(userSnapshot.getValue(User.class)).getEmail(), username)){
                            user=userSnapshot.getValue(User.class);
                            return;
                        }

                    }

                } else {
                    Toast.makeText(getContext(), "Email not found. Please try again or create an account.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error retrieving user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}