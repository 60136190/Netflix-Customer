package com.example.moviettn.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailFilmActivity;
import com.example.moviettn.model.Favorite;
import com.example.moviettn.model.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListFavoritelFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<Favorite> mFavoriteList;

    public ListFavoritelFilmAdapter(Context mContext, List<Favorite> mFavoriteList) {
        this.mContext = mContext;
        this.mFavoriteList = mFavoriteList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_favorite, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Favorite favorite = mFavoriteList.get(position);
        if (favorite.getFilm() == null) {

        } else {
            String imgFilm = favorite.getFilm().getImageFilm().getUrl();
            Picasso.with(mContext)
                    .load(imgFilm).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemImage);

            ((ItemViewHolder) holder).itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, DetailFilmActivity.class);
                    String strName = favorite.getFilm().getId();
                    i.putExtra("Id_film", strName);
                    mContext.startActivity(i);

                }
            });

        }
    }


    @Override
    public int getItemCount() {
        if (mFavoriteList != null) {
            return mFavoriteList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.img_film);
        }
    }

}
