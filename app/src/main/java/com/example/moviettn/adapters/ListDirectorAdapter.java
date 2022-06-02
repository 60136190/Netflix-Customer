package com.example.moviettn.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailFilmFollowOptionActivity;
import com.example.moviettn.model.Director;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListDirectorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Director> mDirectorList;

    public ListDirectorAdapter(Context mContext, List<Director> mDirectorList) {
        this.mContext = mContext;
        this.mDirectorList = mDirectorList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_director,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Director director = mDirectorList.get(position);
        String title = director.getName();
        String imgDirector = director.getImage().getUrl();

        ((ItemViewHolder) holder).itemNameOfDirector.setText(title);
        Picasso.with(mContext)
                .load(imgDirector).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemUrl);
        ((ItemViewHolder) holder).itemCtLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailFilmFollowOptionActivity.class);
                intent.putExtra("id_director",director.getId());
                intent.putExtra("name_of_director",director.getName());
                intent.putExtra("option","0");
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mDirectorList != null){
            return mDirectorList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemUrl;
        private TextView itemNameOfDirector;
        private ConstraintLayout itemCtLayout;


        public ItemViewHolder( View itemView) {
            super(itemView);
            itemUrl = itemView.findViewById(R.id.img_director);
            itemNameOfDirector = itemView.findViewById(R.id.tv_name_of_director);
            itemCtLayout = itemView.findViewById(R.id.ct_director);
        }
    }

}