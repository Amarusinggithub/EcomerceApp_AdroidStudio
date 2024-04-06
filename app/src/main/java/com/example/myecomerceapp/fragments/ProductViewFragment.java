package com.example.myecomerceapp.fragments;

import static com.example.myecomerceapp.activities.MainActivity.frameLayout;
import static com.example.myecomerceapp.activities.MainActivity.removeViews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myecomerceapp.R;


public class ProductViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View productView=inflater.inflate(R.layout.fragment_productview, container, false);

        Bundle args = getArguments();
        if (args != null) {
            // Extract data from the Bundle
            String productName = args.getString("productName");
            String productPrice = args.getString("productPrice");
            String productDescription = args.getString("productDescription");
            int productImageResource = args.getInt("productImage");


            TextView productNameTextView =  productView.findViewById(R.id.Name);
            TextView productPriceTextView =  productView.findViewById(R.id.Price);
            TextView productDescriptionTextView =  productView.findViewById(R.id.Description);
            ImageView productImageView =  productView.findViewById(R.id.Image);

            productNameTextView.setText(productName);
            productPriceTextView.setText(productPrice);
            productDescriptionTextView.setText(productDescription);
            productImageView.setBackgroundResource(productImageResource);
            removeViews();
            frameLayout.setVisibility(View.VISIBLE);
        }

        return productView;

}
}
