package com.example.myecomerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myecomerceapp.interfaces.MyOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.ProductModel;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private final MyOnClickInterface itemGridViewInterface;
    private List<ProductModel> productModelArrayList;

    public ItemAdapter(MyOnClickInterface itemGridViewInterface, List<ProductModel> productModelArrayList) {
        this.itemGridViewInterface = itemGridViewInterface;
        this.productModelArrayList = productModelArrayList;
    }

    @Override
    public int getCount() {
        return productModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_cardview,parent,false);
       ImageView itemImage= convertView.findViewById(R.id.itemImage);
       TextView itemName= convertView.findViewById(R.id.itemTitle);
       TextView itemPrice=convertView.findViewById(R.id.itemPrice);

        ProductModel productModel = productModelArrayList.get(position);
        itemImage.setBackgroundResource(productModel.getProductImage());
        itemName.setText(productModel.getProductName());
        itemPrice.setText(productModel.getProductPrice());
        convertView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                       itemGridViewInterface.onClicked(position);
           }
       });

        return convertView;
    }

    public void filteredList(List <ProductModel> filteredList){
        productModelArrayList =filteredList;
    }
}

