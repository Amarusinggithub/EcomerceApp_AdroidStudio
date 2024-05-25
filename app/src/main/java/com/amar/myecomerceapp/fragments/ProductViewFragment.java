package com.amar.myecomerceapp.fragments;


import static com.amar.myecomerceapp.activities.MainActivity.STRIPE_PUBLISH_KEY;
import static com.amar.myecomerceapp.activities.MainActivity.STRIPE_SECRET_KEY;
import static com.amar.myecomerceapp.activities.MainActivity.everyProduct;
import static com.amar.myecomerceapp.activities.MainActivity.productInProductViewFragment;
import static com.amar.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.amar.myecomerceapp.activities.MainActivity.productsFavorited;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.models.Product;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductViewFragment extends Fragment {
    private static final String TAG = "ProductViewFragment";

    private Button addToCart;
    private Button buyBtn;
    private ImageView addFavoriteBtn;
    private ImageView shareBtn;
    private ImageView backBtn;
    private boolean productAlreadyInFavorites;
    private PaymentSheet paymentSheet;
    private String customerId;
    private String ephemeralKey;
    private String clientSecret;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productview, container, false);
        initializeViewElements(view);
        return view;
    }

    private void initializeViewElements(View view) {
        addToCart = view.findViewById(R.id.addtocartbtn);
        addFavoriteBtn = view.findViewById(R.id.favoritebtn);
        shareBtn = view.findViewById(R.id.sharebtn);
        backBtn = view.findViewById(R.id.backbtn);
        buyBtn = view.findViewById(R.id.buyBtn);

        TextView productNameTextView = view.findViewById(R.id.Name);
        TextView productPriceTextView = view.findViewById(R.id.Price);
        TextView productDescriptionTextView = view.findViewById(R.id.Description);
        ImageView productImageView = view.findViewById(R.id.Image);

        PaymentConfiguration.init(requireContext(), STRIPE_PUBLISH_KEY);

        paymentSheet = new PaymentSheet(this, this::onPaymentResult);

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        createStripeCustomer(requestQueue);

        setupProductDetails(productNameTextView, productPriceTextView, productDescriptionTextView, productImageView);
        setupButtons();

        Glide.with(requireContext())
                .load(R.drawable.share)
                .fitCenter()
                .into(shareBtn);

        Glide.with(requireContext())
                .load(R.drawable.back)
                .fitCenter()
                .into(backBtn);

        backBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void createStripeCustomer(RequestQueue requestQueue) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        customerId = object.getString("id");
                        Toast.makeText(requireContext(), "Customer ID: " + customerId, Toast.LENGTH_SHORT).show();
                        createEphemeralKey(requestQueue, customerId);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing customer response", e);
                    }
                }, error -> Log.e(TAG, "Error creating customer", error)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + STRIPE_SECRET_KEY);
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void createEphemeralKey(RequestQueue requestQueue, String customerId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        ephemeralKey = object.getString("id");
                        Toast.makeText(requireContext(), "Ephemeral Key: " + ephemeralKey, Toast.LENGTH_SHORT).show();
                        createPaymentIntent(requestQueue, customerId, ephemeralKey);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing ephemeral key response", e);
                    }
                }, error -> Log.e(TAG, "Error creating ephemeral key", error)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + STRIPE_SECRET_KEY);
                headers.put("Stripe-Version", "2022-08-01");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerId);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void createPaymentIntent(RequestQueue requestQueue, String customerId, String ephemeralKey) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        clientSecret = object.getString("client_secret");
                        Toast.makeText(requireContext(), "Client Secret: " + clientSecret, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing payment intent response", e);
                    }
                }, error -> Log.e(TAG, "Error creating payment intent", error)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + STRIPE_SECRET_KEY);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerId);
                params.put("amount", "1000");
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(requireContext(), "Payment Completed", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Log.e(TAG, "Payment Failed: " + ((PaymentSheetResult.Failed) paymentSheetResult).getError().getMessage());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.d(TAG, "Payment Canceled");
        }
    }

    private void setupProductDetails(TextView productNameTextView, TextView productPriceTextView, TextView productDescriptionTextView, ImageView productImageView) {
        Product product = everyProduct.get(getProductInPosition());
        productNameTextView.setText(product.getProductName());
        productPriceTextView.setText(product.getProductPrice());
        productDescriptionTextView.setText(product.getProductDescription());
        Glide.with(this)
                .load(product.getImage())
                .fitCenter()
                .into(productImageView);

        if (productsFavorited.contains(product)) {
            productAlreadyInFavorites = true;
            Glide.with(requireContext())
                    .load(R.drawable.favoriteicon2)
                    .fitCenter()
                    .into(addFavoriteBtn);
        } else {
            productAlreadyInFavorites = false;
            Glide.with(requireContext())
                    .load(R.drawable.unfavorite)
                    .fitCenter()
                    .into(addFavoriteBtn);
        }
    }

    private void setupButtons() {
        addFavoriteBtn.setOnClickListener(v -> {
            Product product = everyProduct.get(getProductInPosition());
            if (productAlreadyInFavorites) {
                productsFavorited.remove(product);
                Glide.with(requireContext())
                        .load(R.drawable.unfavorite)
                        .fitCenter()
                        .into(addFavoriteBtn);
                Toast.makeText(getContext(), product.getProductName() + " was removed from favorites.", Toast.LENGTH_SHORT).show();
                productAlreadyInFavorites = false;
            } else {
                productsFavorited.add(product);
                Glide.with(requireContext())
                        .load(R.drawable.favoriteicon2)
                        .fitCenter()
                        .into(addFavoriteBtn);
                Toast.makeText(getContext(), product.getProductName() + " was added to favorites.", Toast.LENGTH_SHORT).show();
                productAlreadyInFavorites = true;
            }
        });

        addToCart.setOnClickListener(v -> {
            Product product = everyProduct.get(getProductInPosition());
            if (product != null) {
                boolean productAlreadyInCart = false;
                for (Product productInCart : productsAddedToCart) {
                    if (productInCart.getProductName().equals(product.getProductName())) {
                        int newQuantity = productInCart.getProductQuantity() + product.getProductQuantity();
                        productInCart.setProductQuantity(newQuantity);
                        Toast.makeText(getContext(), "Quantity of " + product.getProductName() + " increased to " + newQuantity, Toast.LENGTH_SHORT).show();
                        productAlreadyInCart = true;
                        break;
                    }
                }
                if (!productAlreadyInCart) {
                    productsAddedToCart.add(product);
                    Toast.makeText(getContext(), product.getProductName() + " was added to cart.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "The product is null");
            }
        });

        buyBtn.setOnClickListener(v -> paymentFlow());
    }

    private void paymentFlow() {
        if (clientSecret != null && customerId != null && ephemeralKey != null) {
            paymentSheet.presentWithPaymentIntent(
                    clientSecret,
                    new PaymentSheet.Configuration("Swift Cart",
                            new PaymentSheet.CustomerConfiguration(customerId, ephemeralKey))
            );
        } else {
            Toast.makeText(requireContext(), "Unable to initiate payment", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("ProductViewFragment");
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public int getProductInPosition() {
        int position = 0;
        for (Product productInEveryThing : everyProduct) {
            if (Objects.equals(productInEveryThing.getProductName(), productInProductViewFragment.getProductName())) {
                position = everyProduct.indexOf(productInEveryThing);
                break;
            }
        }
        return position;
    }
}
