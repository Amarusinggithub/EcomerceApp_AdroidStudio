package com.example.myecomerceapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myecomerceapp.activitys.MainActivity;
import com.example.myecomerceapp.R;

public class ItemViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View itemView=inflater.inflate(R.layout.fragment_itemview, container, false);

        Bundle args = getArguments();
        if (args != null) {
            // Extract data from the Bundle
            String itemName = args.getString("ItemName");
            String itemPrice = args.getString("ItemPrice");
            String itemDescription = args.getString("ItemDescription");
            int itemImageResource = args.getInt("ItemImage");


            TextView itemNameTextView = itemView.findViewById(R.id.itemName);
            TextView itemPriceTextView = itemView.findViewById(R.id.itemPrice);
            TextView itemDescriptionTextView = itemView.findViewById(R.id.itemDescription);
            ImageView itemImageView = itemView.findViewById(R.id.itemImage);

            itemNameTextView.setText(itemName);
            itemPriceTextView.setText(itemPrice);
            itemDescriptionTextView.setText(itemDescription);
            itemImageView.setBackgroundResource(itemImageResource);
        }

        return itemView;

}
}
