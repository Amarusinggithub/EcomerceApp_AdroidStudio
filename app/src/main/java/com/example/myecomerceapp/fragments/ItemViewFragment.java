package com.example.myecomerceapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myecomerceapp.R;

public class ItemViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
       /* itemImage=findViewById(R.id.itemImage);
        itemName=findViewById(R.id.itemName);
        itemPrice=findViewById(R.id.itemPrice);
        itemDescription=findViewById(R.id.itemDescription);
        itemQuantity=findViewById(R.id.itemQuantity);
        buyBtn=findViewById(R.id.buyBtn);
        minusBtn=findViewById(R.id.minusBtn);
        plusBtn=findViewById(R.id.plussBtn);

        int image=getIntent().getIntExtra("ItemImage",R.drawable.ic_launcher_background);
        String name=getIntent().getStringExtra("ItemName");
        String price=getIntent().getStringExtra("ItemPrice");
        String description=getIntent().getStringExtra("ItemDecription");

        itemImage.setBackgroundResource(image);
        itemName.setText(name);
        itemPrice.setText(price);
        itemDescription.setText(description);*/
    }
}
