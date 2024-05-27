package com.amar.myecomerceapp.activities;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.fragments.AccountFragment;
import com.amar.myecomerceapp.fragments.CartFragment;
import com.amar.myecomerceapp.fragments.HomeFragment;
import com.amar.myecomerceapp.fragments.OrdersFragment;
import com.amar.myecomerceapp.models.Category;
import com.amar.myecomerceapp.models.Product;
import com.amar.myecomerceapp.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;




public class MainActivity extends AppCompatActivity {
    public static final String STRIPE_SECRET_KEY = "sk_test_51PEvksP13euDVuhqWG2M6aI09MAAI9tKJwiJxjuCF4YRX6nRsbkEPRtD4cRM8LujTGKfApzYvTJsezSeW4Ckl8Vx00khuc6HHr";
    public static final String STRIPE_PUBLISH_KEY = "pk_test_51PEvksP13euDVuhqdujVacuTKlz9tSOoBW2CwTlUhgkn6xgUwDgoYFgMR7eYFFGQUFGWVlJz4yRlndv65mBtLB1p00ICZgRXGU";
    public static final String LAPTOP = "Laptop";
    public static final String PHONES = "Phones";
    public static final String GAMES = "Games";
    public static final String CONSOLES = "Consoles";
    public static final String APPLIANCES = "Appliances";
    private static final String TAG = "MainActivity";
    public static ArrayList<Product> productsAddedToCart;
    public static ArrayList<Product> productsUserOrdered;
    public static ArrayList<Product> productsFavorited;
    public static ArrayList<Product> popularProductsData;
    public static ArrayList<Product> pickedForYouProductsData;
    public static ArrayList<Product> salesProductData;
    public static ArrayList<Product> everyProduct;
    private static MainActivity instance;
    String userDocumentID;
    public static User user;
    public static Product productInProductViewFragment;
    BottomNavigationView bottomNavigationView;
    String email;
    String username;

    public static Product product;
    public static FirebaseFirestore  db;

    public static void finishActivity() {
      instance.finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        getUserFromDatabase();

    }

    private void getUserFromDatabase() {
        email = getIntent().getStringExtra("email");

        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Log.d(TAG, document.getId() + " => " + document.getData());
                            user = document.toObject(User.class);
                            if (user.getEmail().equals(email)) {
                                username = user.getUsername();
                                userDocumentID=document.getId();
                                productsFavorited=user.getProductsFavorited();
                                productsUserOrdered=user.getProductsUserOrdered();
                                productsAddedToCart=user.getProductsUserAddedToCart();
                                getEveryProductFromDatabase();
                                return;
                            }
                        }
                        Log.d(TAG, "No user found with email: " + email);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    private void initializeViews() {
        bottomNavigationView = findViewById(R.id.bottomnav);
        setupBottomNavigationView();
        popularProductsData=getPopularProductsData();
        pickedForYouProductsData=getPickedForYouProductsData();
        salesProductData=getSalesProductsData();

        productsAddedToCart = user.getProductsUserAddedToCart();
        productsUserOrdered = user.getProductsUserOrdered();
        productsFavorited = user.getProductsFavorited();
        instance=this;

    }
    private void getEveryProductFromDatabase() {
        everyProduct =new ArrayList<>();
        db.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            product = document.toObject(Product.class);
                            everyProduct.add(product);
                        }
                        if(!everyProduct.isEmpty()){
                            initializeViews();
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    private void setupBottomNavigationView() {
        loadFragment(new HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                loadFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.orders) {
                loadFragment(new OrdersFragment());
            } else if (item.getItemId() == R.id.account) {

                loadFragment(new AccountFragment());

            } else if (item.getItemId() == R.id.cart) {
                loadFragment(new CartFragment());
            }
            return true;
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        /*fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public static List<Category> getCategory() {
        List<Category> categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(PHONES, R.drawable.smartphones_category_icon, PHONES));
        categoryArrayList.add(new Category(LAPTOP, R.drawable.laptop_category_icon, LAPTOP));
        categoryArrayList.add(new Category(GAMES, R.drawable.games_category_icon, GAMES));
        categoryArrayList.add(new Category(CONSOLES, R.drawable.gamingconsoles_category_icon, CONSOLES));
        categoryArrayList.add(new Category(APPLIANCES, R.drawable.homeappliance_category_icon, APPLIANCES));
        return categoryArrayList;
    }

    public static List<Product> getProductsData(String id) {
        List<Product> productsArrayList = new ArrayList<>();
        for (Product productInEveryproduct : everyProduct) {
            if (Objects.equals(productInEveryproduct.getProductId(), id)) {
                productsArrayList.add(productInEveryproduct);
            }
        }
        return productsArrayList;
    }

    public static ArrayList<Product> getPopularProductsData() {
        ArrayList<Product> productsArrayList = new ArrayList<>();
       ArrayList<Product>  allProducts= everyProduct;
        Collections.shuffle(allProducts, new Random());
        int numberOfProductsToAdd = Math.min(20,allProducts.size());
        for (int i = 0; i < numberOfProductsToAdd; i++) {
            productsArrayList.add(allProducts.get(i));
        }
        return productsArrayList;
    }

    public static ArrayList<Product> getSalesProductsData() {
        ArrayList<Product> productsArrayList = new ArrayList<>();
        ArrayList<Product>  allProducts= everyProduct;
        Collections.shuffle(allProducts, new Random());

        int numberOfProductsToAdd = Math.min(20,allProducts.size());
        for (int i = 0; i < numberOfProductsToAdd; i++) {
            productsArrayList.add(allProducts.get(i));
        }
        return productsArrayList;
    }

    public static ArrayList<Product> getPickedForYouProductsData() {
       ArrayList<Product> productsArrayList = new ArrayList<>();
       ArrayList<Product>  allProducts= everyProduct;
        Collections.shuffle(allProducts, new Random());

        int numberOfProductsToAdd = Math.min(20,allProducts.size());
        for (int i = 0; i < numberOfProductsToAdd; i++) {
            productsArrayList.add(allProducts.get(i));
        }
        return productsArrayList;
    }


    @Override
    protected void onPause() {
        super.onPause();

        DocumentReference docRef = db.collection("users").document(userDocumentID);

        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("cart", productsAddedToCart);
        updatedData.put("ordered", productsUserOrdered);
        updatedData.put("favorites", productsFavorited);

        docRef.update(updatedData)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Document successfully updated!"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error updating document", e));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DocumentReference docRef = db.collection("users").document(userDocumentID);

        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("cart", productsAddedToCart);
        updatedData.put("ordered", productsUserOrdered);
        updatedData.put("favorites", productsFavorited);

        docRef.update(updatedData)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Document successfully updated!"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error updating document", e));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getUserFromDatabase();
    }


}
