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
import com.example.myecomerceapp.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final MyOnClickInterface myOnClickInterface;
    private final List<Category> categoryArrayList;

    public CategoryAdapter(MyOnClickInterface myOnClickInterface, List<Category> categoryArrayList) {
        this.myOnClickInterface = myOnClickInterface;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryView= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_categoriy_cardview,parent,false);
        return new MyViewHolder(categoryView, myOnClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
    Category category = categoryArrayList.get(position);
    holder.categoryTitle.setText(category.getCategoryTitle());
    holder.categoryImage.setBackgroundResource(category.getCategoryImage());
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle;
        ImageView categoryImage;
        public MyViewHolder(@NonNull View itemView, MyOnClickInterface myOnClickInterface) {
            super(itemView);
            categoryTitle=itemView.findViewById(R.id.categoryTitle);
            categoryImage=itemView.findViewById(R.id.categoryImage);

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
