package com.example.myecomerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myecomerceapp.interfaces.MyOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.ProductModel;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {

    private final List<ProductModel> productModels;
    private final MyOnClickInterface myOnClickInterface;

    public BannerAdapter(List<ProductModel> productModels, MyOnClickInterface myOnClickInterface) {
        this.productModels = productModels;
        this.myOnClickInterface = myOnClickInterface;
    }

    @NonNull
    @Override
    public BannerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.specials_recycleview,parent,false);
        return new MyViewHolder(itemView, myOnClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.MyViewHolder holder, int position) {

        ProductModel productModel = productModels.get(position);
        holder.image.setBackgroundResource(productModel.getProductImage());
        /*holder.text.setText(productModel.getItemName());*/
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView text;

        public MyViewHolder(@NonNull View itemView, MyOnClickInterface myOnClickInterface) {
            super(itemView);

            image=itemView.findViewById(R.id.bannerImage);
         /*   text=itemView.findViewById(R.id.bannerTitle);*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myOnClickInterface !=null){
                        int position=getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            myOnClickInterface.onClicked(position);
                        }
                    }
                }
            });

        }
    }
}
