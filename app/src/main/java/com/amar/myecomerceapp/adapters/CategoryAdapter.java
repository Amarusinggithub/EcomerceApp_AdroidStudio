package com.amar.myecomerceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.amar.myecomerceapp.interfaces.MyCategoryOnClickListener;
import com.amar.myecomerceapp.R;
import com.amar.myecomerceapp.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final MyCategoryOnClickListener myCategoryOnClickListener;
    private final List<Category> categoryArrayList;

    private final Context context;

    public CategoryAdapter(MyCategoryOnClickListener myCategoryOnClickListener, List<Category> categoryArrayList, Context context) {
        this.myCategoryOnClickListener = myCategoryOnClickListener;
        this.categoryArrayList = categoryArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryView= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoriy_cardview,parent,false);
        return new MyViewHolder(categoryView, myCategoryOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
    Category category = categoryArrayList.get(position);
    holder.categoryTitle.setText(category.getCategoryTitle());
        Glide.with(context)
                .load(category.getCategoryImage())
                .into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle;
        ImageView categoryImage;
        public MyViewHolder(@NonNull View itemView, MyCategoryOnClickListener myCategoryOnClickListener) {
            super(itemView);
            categoryTitle=itemView.findViewById(R.id.categoryTitle);
            categoryImage=itemView.findViewById(R.id.categoryImage);

            itemView.setOnClickListener(v -> {
                if (myCategoryOnClickListener !=null){
                    int position=getAdapterPosition();
                    if (position!= RecyclerView.NO_POSITION){
                        myCategoryOnClickListener.categoryClicked(position);
                    }
                }
            });
        }
    }
}
