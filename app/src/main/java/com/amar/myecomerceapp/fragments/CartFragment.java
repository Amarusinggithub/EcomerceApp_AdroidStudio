package com.amar.myecomerceapp.fragments;

import static com.amar.myecomerceapp.activities.MainActivity.STRIPE_PUBLISH_KEY;
import static com.amar.myecomerceapp.activities.MainActivity.STRIPE_SECRET_KEY;
import static com.amar.myecomerceapp.activities.MainActivity.productInProductViewFragment;
import static com.amar.myecomerceapp.activities.MainActivity.productsAddedToCart;
import static com.amar.myecomerceapp.activities.MainActivity.productsUserOrdered;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.adapters.CartAdapter;
import com.amar.myecomerceapp.interfaces.MyProductOnClickListener;
import com.amar.myecomerceapp.models.Product;
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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartFragment extends Fragment implements MyProductOnClickListener {
    private static final String TAG = "CartFragment";
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CartAdapter cartAdapter;
    Button checkOutBtn;
    ImageView emptyCartImage;
    TextView totalText;
    public static TextView totalNumber;
    View line;
    private PaymentSheet paymentSheet;
    private String customerId;
    private String ephemeralKey;
    private String clientSecret;
    private String address;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initializeViewElements(view);
        return view;
    }

    private void initializeViewElements(View view) {
        checkOutBtn = view.findViewById(R.id.checkoutbtn);
        recyclerView = view.findViewById(R.id.recyclerview);
        emptyCartImage = view.findViewById(R.id.cartisemptyimage);
        totalText = view.findViewById(R.id.totaltext);
        totalNumber = view.findViewById(R.id.totalnumber);
        line = view.findViewById(R.id.line);

        PaymentConfiguration.init(requireContext(), STRIPE_PUBLISH_KEY);

        paymentSheet = new PaymentSheet(this, this::onPaymentResult);

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        createStripeCustomer(requestQueue);

        if (!productsAddedToCart.isEmpty()) {
            setUpCartRecyclerView();
        } else {
            setupCartEmptyView();
        }

        checkOutBtn.setOnClickListener(v -> {
            if (clientSecret != null && customerId != null && ephemeralKey != null) {
                showAddressDialog();
            } else {
                Log.e(TAG, "Unable to initiate payment. Missing required data.");
            }
        });
    }

    private void createStripeCustomer(RequestQueue requestQueue) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                response -> {
                    if (isAdded()) {
                        try {
                            JSONObject object = new JSONObject(response);
                            customerId = object.getString("id");
                            createEphemeralKey(requestQueue, customerId);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing customer response", e);
                        }
                    }
                }, error -> {
            if (isAdded()) {
                Log.e(TAG, "Error creating customer", error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
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
                    if (isAdded()) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ephemeralKey = object.getString("secret");
                            createPaymentIntent(requestQueue, customerId);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing ephemeral key response", e);
                        }
                    }
                }, error -> {
            if (isAdded()) {
                Log.e(TAG, "Error creating ephemeral key", error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + STRIPE_SECRET_KEY);
                headers.put("Stripe-Version", "2024-04-10");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerId);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void createPaymentIntent(RequestQueue requestQueue, String customerId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                response -> {
                    if (isAdded()) {
                        try {
                            JSONObject object = new JSONObject(response);
                            clientSecret = object.getString("client_secret");
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing payment intent response", e);
                        }
                    }
                }, error -> {
            if (isAdded()) {
                Log.e(TAG, "Error creating payment intent", error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + STRIPE_SECRET_KEY);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerId);
                params.put("amount", String.valueOf(calculateTotal()));
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (isAdded()) {
            if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
                for (Product productInCart : productsAddedToCart) {
                    String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    productInCart.setAddressDeliveredTo(address);
                    productInCart.setDateAndTimeout(currentDateTime);
                    productsUserOrdered.add(productInCart);
                    productsAddedToCart.remove(productInCart);

                }

                loadFragment(new OrderRecieptFragment());
                Toast.makeText(requireContext(), "Payment Completed", Toast.LENGTH_SHORT).show();
            } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
                Log.e(TAG, "Payment Failed: " + ((PaymentSheetResult.Failed) paymentSheetResult).getError().getMessage());
            } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
                Log.d(TAG, "Payment Canceled");
            }
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

    private void setUpCartRecyclerView() {
        emptyCartImage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        checkOutBtn.setVisibility(View.VISIBLE);
        totalText.setVisibility(View.VISIBLE);
        totalNumber.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        cartAdapter = new CartAdapter(productsAddedToCart, this, getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartAdapter);
        totalNumber.setText(calculateTotalFormatted());
    }

    private int calculateTotal() {
        int total = 0;
        for (Product product : productsAddedToCart) {
            String[] priceParts = product.getProductPrice().split("\\$");
            String priceWithoutDollarSign = priceParts[1].replaceAll("[^\\d.]", "").replace(",", "");
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
        productInProductViewFragment = productsAddedToCart.get(position);
        ProductViewFragment productViewFragment = new ProductViewFragment();
        loadFragment(productViewFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack("cartFragment");
        fragmentTransaction.commit();
    }

    private void showAddressDialog() {
        Context context = requireContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter Shipping Address");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            address = input.getText().toString();
            if (!address.isEmpty()) {
                paymentFlow();
            } else {
                Toast.makeText(context, "Address cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
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
}
