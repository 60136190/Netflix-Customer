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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailFilmActivity;
import com.example.moviettn.model.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VerticalFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Film> mFilmList;

    public VerticalFilmAdapter(Context mContext, List<Film> mFilmList) {
        this.mContext = mContext;
        this.mFilmList = mFilmList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vertical_film,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film film = mFilmList.get(position);
        String imageUrl = film.getImageFilm().getUrl();
        ((ItemViewHolder) holder).itemTitle.setText(film.getTitle());
        ((ItemViewHolder) holder).itemDes.setText(film.getDescription());
        ((ItemViewHolder) holder).itemLimitAge.setText(film.getAgeLimit());
        Picasso.with(mContext)
                .load(imageUrl).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemImage);

        ((ItemViewHolder) holder).constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailFilmActivity.class);
                String strName = film.getId();
                i.putExtra("Id_film", strName);
                mContext.startActivity(i);

            }
        });

    }


    @Override
    public int getItemCount() {
        if (mFilmList != null){
            return mFilmList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        public TextView itemDes;
        public TextView itemTitle;
        public TextView itemLimitAge;
        public ConstraintLayout constraintLayout;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.hot_image);
            itemTitle = itemView.findViewById(R.id.tv_title_film);
            itemDes = itemView.findViewById(R.id.tv_description);
            itemLimitAge = itemView.findViewById(R.id.tv_limit_age);
            constraintLayout = itemView.findViewById(R.id.layout_thethao);
        }
    }

}
