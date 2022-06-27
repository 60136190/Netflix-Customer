package com.example.moviettn.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailFilmActivity;
import com.example.moviettn.model.Film;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

    public class SearchFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context mContext;
        List<Film> mFilmList;

        public SearchFilmAdapter(Context mContext, List<Film> mFilmList) {
            this.mContext = mContext;
            this.mFilmList = mFilmList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_search_film,parent,false);
            return new ItemViewHolder(view);
        }


        public void filterList(List<Film> filteredList) {
            mFilmList = filteredList;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Film film = mFilmList.get(position);
            String imageUrl = film.getImageFilm().getUrl();
            ((ItemViewHolder) holder).tvNameOfFilm.setText(film.getTitle());

            Picasso.with(mContext)
                    .load(imageUrl).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder)holder).imgFilm);

            ((ItemViewHolder) holder).imgPlay.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Intent i = new Intent(mContext, DetailFilmActivity.class);
                    StoreUtil.save(mContext, Contants.idFilm,film.getId());
                    StoreUtil.save(mContext,Contants.price,String.valueOf(film.getPrice()));
                    StoreUtil.save(mContext,Contants.titleFilm,String.valueOf(film.getTitle()));
                    StoreUtil.save(mContext,Contants.urlFilm,String.valueOf(film.getImageFilm().getUrl()));
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
            public ImageView imgFilm;
            public ImageView imgPlay;
            public TextView tvNameOfFilm;

            public ItemViewHolder( View itemView) {
                super(itemView);
                imgFilm = itemView.findViewById(R.id.img_film);
                imgPlay = itemView.findViewById(R.id.img_play);
                tvNameOfFilm = itemView.findViewById(R.id.tv_title_film);
            }
        }

    }
