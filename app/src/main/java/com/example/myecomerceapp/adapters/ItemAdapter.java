package com.example.myecomerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myecomerceapp.ItemOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.ItemModel;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private final ItemOnClickInterface itemGridViewInterface;
    private final List<ItemModel> itemModelArrayList;

    public ItemAdapter(ItemOnClickInterface itemGridViewInterface, List<ItemModel> itemModelArrayList) {
        this.itemGridViewInterface = itemGridViewInterface;
        this.itemModelArrayList = itemModelArrayList;
    }

    @Override
    public int getCount() {
        return itemModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemModelArrayList.get(position);
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

        ItemModel itemModel=itemModelArrayList.get(position);
        itemImage.setBackgroundResource(itemModel.getItemImage());
        itemName.setText(itemModel.getItemName());
        itemPrice.setText(itemModel.getItemPrice());
        convertView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                       itemGridViewInterface.onItemClicked(position);
           }
       });

        return convertView;
    }
}

