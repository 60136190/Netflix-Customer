package com.example.moviettn.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.model.Category;
import com.example.moviettn.activities.DetailFilmFollowOptionActivity;

import java.util.List;

public class ListCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Category> mCategoryList;

    public ListCategoryAdapter(Context mContext, List<Category> mCategoryList) {
        this.mContext = mContext;
        this.mCategoryList = mCategoryList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Category category = mCategoryList.get(position);
        ((ItemViewHolder) holder).tvCategory.setText(category.getName());
        ((ItemViewHolder) holder).tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailFilmFollowOptionActivity.class);
                intent.putExtra("id_category",category.getId());
                intent.putExtra("name_of_category",category.getName());
                intent.putExtra("option","1");
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mCategoryList != null){
            return mCategoryList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategory;

        public ItemViewHolder( View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }
    }

}
