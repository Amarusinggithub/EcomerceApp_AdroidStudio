package com.example.myecomerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myecomerceapp.ItemOnClickInterface;
import com.example.myecomerceapp.R;
import com.example.myecomerceapp.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final ItemOnClickInterface itemOnClickInterface;
    private final List<CategoryModel> categoryModelArrayList;

    public CategoryAdapter(ItemOnClickInterface itemOnClickInterface, List<CategoryModel> categoryModelArrayList) {
        this.itemOnClickInterface = itemOnClickInterface;
        this.categoryModelArrayList = categoryModelArrayList;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryView= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_categoriy_view,parent,false);
        return new MyViewHolder(categoryView,itemOnClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
    CategoryModel categoryModel=categoryModelArrayList.get(position);
    holder.categoryTitle.setText(categoryModel.getCategoryTitle());
    holder.categoryImage.setBackgroundResource(categoryModel.getCategoryImage());
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle;
        ImageView categoryImage;
        public MyViewHolder(@NonNull View itemView, ItemOnClickInterface itemOnClickInterface) {
            super(itemView);
            categoryTitle=itemView.findViewById(R.id.categoryTitle);
            categoryImage=itemView.findViewById(R.id.categoryImage);

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
