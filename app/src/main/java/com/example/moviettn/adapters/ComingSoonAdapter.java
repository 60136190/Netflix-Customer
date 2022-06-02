package com.example.moviettn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.model.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComingSoonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Film> mFilmList;

    public ComingSoonAdapter(Context mContext, List<Film> mFilmList) {
        this.mContext = mContext;
        this.mFilmList = mFilmList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coming_soon,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film film = mFilmList.get(position);
        String imageUrl = film.getImageFilm().getUrl();
        ((ItemViewHolder) holder).itemTitle.setText(film.getTitle());
        ((ItemViewHolder) holder).itemStoryLine.setText(film.getDescription());
        ((ItemViewHolder) holder).itemLimitAge.setText(film.getAgeLimit()+"+");

        if(film.getDirector().isEmpty()){
            ((ItemViewHolder) holder).itemDirectors.setText("");
        }else {
            String delim = " •";
            int i = 0;
            StringBuilder str = new StringBuilder();
            while (i < film.getDirector().size()-1) {
                str.append(film.getDirector().get(i).getName());
                str.append(delim);
                i++;
            }
            str.append(film.getDirector().get(i).getName());
            String directors = str.toString();

            ((ItemViewHolder) holder).itemDirectors.setText(directors);
        }

        Picasso.with(mContext)
                .load(imageUrl).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemBanner);

    }


    @Override
    public int getItemCount() {
        if (mFilmList != null){
            return mFilmList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemBanner;
        private TextView itemDate;
        private TextView itemMonth;
        private TextView itemLimitAge;
        private TextView itemStoryLine;
        private TextView itemTitle;
        private TextView itemDirectors;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemBanner = itemView.findViewById(R.id.img_banner);
            itemTitle = itemView.findViewById(R.id.tv_title_film);
            itemDate = itemView.findViewById(R.id.tv_date);
            itemMonth = itemView.findViewById(R.id.tv_month);
            itemLimitAge = itemView.findViewById(R.id.tv_limit_age);
            itemStoryLine = itemView.findViewById(R.id.tv_storyline);
            itemDirectors = itemView.findViewById(R.id.tv_directors);
        }
    }

}
