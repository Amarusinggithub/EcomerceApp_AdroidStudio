package com.example.myecomerceapp;

import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ItemView extends AppCompatActivity {
ImageView itemImage;
TextView itemName,itemPrice,itemDescription,itemQuantity;
Button buyBtn;
ImageButton minusBtn,plusBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemview);

        itemImage=findViewById(R.id.itemImage);
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
        itemDescription.setText(description);
    }
}
