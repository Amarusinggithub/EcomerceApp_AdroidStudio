package com.example.myecomerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myecomerceapp.interfaces.ItemOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.ItemModel;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {

    private final List<ItemModel> itemModels;
    private final ItemOnClickInterface itemOnClickInterface;

    public BannerAdapter(List<ItemModel> itemModels, ItemOnClickInterface itemOnClickInterface) {
        this.itemModels = itemModels;
        this.itemOnClickInterface = itemOnClickInterface;
    }

    @NonNull
    @Override
    public BannerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_recycleview,parent,false);
        return new MyViewHolder(itemView,itemOnClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.MyViewHolder holder, int position) {

        ItemModel itemModel=itemModels.get(position);
        holder.image.setBackgroundResource(itemModel.getItemImage());
        /*holder.text.setText(itemModel.getItemName());*/
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView text;

        public MyViewHolder(@NonNull View itemView,ItemOnClickInterface itemOnClickInterface) {
            super(itemView);

            image=itemView.findViewById(R.id.bannerImage);
         /*   text=itemView.findViewById(R.id.bannerTitle);*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemOnClickInterface!=null){
                        int position=getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            itemOnClickInterface.onItemClicked(position);
                        }
                    }
                }
            });

        }
    }
}
