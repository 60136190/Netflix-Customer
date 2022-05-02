package com.example.moviettn.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailFilmActivity;
import com.example.moviettn.activities.DetailVideoActivity;
import com.example.moviettn.model.DetailFilm;
import com.example.moviettn.model.Film;
import com.example.moviettn.model.SeriesFilm;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeriesFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<SeriesFilm> mDetailFilms;

    public SeriesFilmAdapter(Context mContext, List<SeriesFilm> mDetailFilms) {
        this.mContext = mContext;
        this.mDetailFilms = mDetailFilms;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_series_film,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SeriesFilm detailFilm = mDetailFilms.get(position);
        String imgFilm = detailFilm.getUrlImage();
        String urlVideo = detailFilm.getUrlVideo();
        int episode = detailFilm.getEpisode();
        ((ItemViewHolder) holder).tvSeries.setText(String.valueOf(detailFilm.getEpisode()));
        Picasso.with(mContext)
                .load(imgFilm).error(R.drawable.error).fit().centerInside().into(((ItemViewHolder) holder).imgFilm);

        ((ItemViewHolder) holder).ctSeriesFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailVideoActivity.class);
                i.putExtra("video", urlVideo);
                i.putExtra("episode", episode);
                mContext.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (mDetailFilms != null){
            return mDetailFilms.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFilm;
        public TextView tvSeries;
        public ConstraintLayout ctSeriesFilm;

        public ItemViewHolder( View itemView) {
            super(itemView);
            imgFilm = itemView.findViewById(R.id.img_film);
            tvSeries = itemView.findViewById(R.id.tv_series_film);
            ctSeriesFilm = itemView.findViewById(R.id.ct_series_film);
        }
    }

}
