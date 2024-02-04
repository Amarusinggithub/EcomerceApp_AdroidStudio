package com.example.myecomerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myecomerceapp.adapters.ItemAdapter;
import com.example.myecomerceapp.models.ItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemGridViewActivity extends AppCompatActivity implements ItemOnClickInterface {
   public String categoryId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = getIntent().getStringExtra("CategoryID");
        setContentView(R.layout.activity_item_gridview);

        TextView header=findViewById(R.id.Header);
        switch (categoryId){
            case "Fashion":
                header.setText("Fashion");
                break;
            case "Groceries":
                header.setText("Groceries");
                break;
            default:
                header.setText("Category Title");
        }


        GridView fashionGridView = findViewById( R.id.gridView);
        ItemAdapter itemAdapter = new ItemAdapter(this, getData(categoryId));
        fashionGridView.setAdapter(itemAdapter);

    }



    public static List<ItemModel> getData(String categoryId) {
        List<ItemModel>ItemsArrayList = new ArrayList<>();
        if ("Fashion".equals(categoryId)) {
            // add fashion items
            ItemsArrayList.add(new ItemModel(R.drawable.men_suit,"Men Suit","$1,000","All black suit","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.men_clothes_bundle,"Men Clothing Bundle","$5,000","All black suit","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.white_buttondown_shirt,"White Shirt","$500","All black suit","Fashion"));
            ItemsArrayList.add(new ItemModel(R.drawable.shoes,"shoes","$1,000","All black suit","Fashion"));
        } else if ("Groceries".equals(categoryId)) {
            // add groceries items
            ItemsArrayList.add(new ItemModel(R.drawable.milk_img,"Mmilkt","$500","white cows milk","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.cranberryjuice,"Cranberry Juice","$5,00","All black suit","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.doritoz,"Doritoz","$200","Doritoz","Groceries"));
            ItemsArrayList.add(new ItemModel(R.drawable.butter,"Butter","$200","Butter","Groceries"));
        }
        return ItemsArrayList;

    }

    @Override
    public void onItemClicked(int position) {
      ItemModel Item = getData(categoryId).get(position);
      if (Objects.equals(Item.getItemId(), "Fashion")){
          Intent intent=new Intent(this,ItemView.class);
          intent.putExtra("ItemName",Item.getItemName());
          intent.putExtra("ItemPrice",Item.getItemPrice());
          intent.putExtra("ItemDescription",Item.getItemSpecs());
          intent.putExtra("ItemImage",Item.getItemImage());
          startActivity(intent);
      } else if (Objects.equals(Item.getItemId(), "Groceries")) {
          Intent intent=new Intent(this,ItemView.class);
          intent.putExtra("ItemName",Item.getItemName());
          intent.putExtra("ItemPrice",Item.getItemPrice());
          intent.putExtra("ItemDescription",Item.getItemSpecs());
          intent.putExtra("ItemImage",Item.getItemImage());
          startActivity(intent);
      }

    }
}
