package com.example.moviettn.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
        String titleFilm = film.getTitle();
        String description = film.getDescription();
        String price = String.valueOf(film.getPrice()+" $");
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

        ((ItemViewHolder) holder).imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoFilm(titleFilm,description,price,imageUrl);
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
        private ImageView itemBanner;
        private ImageView imgInfo;
        private TextView itemLimitAge;
        private TextView itemStoryLine;
        private TextView itemTitle;
        private TextView itemDirectors;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemBanner = itemView.findViewById(R.id.img_banner);
            itemTitle = itemView.findViewById(R.id.tv_title_film);
            itemLimitAge = itemView.findViewById(R.id.tv_limit_age);
            itemStoryLine = itemView.findViewById(R.id.tv_storyline);
            itemDirectors = itemView.findViewById(R.id.tv_directors);
            imgInfo = itemView.findViewById(R.id.img_info);
        }
    }

    public void infoFilm(String title, String description
            , String price, String img){
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_film);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        window.setAttributes(windowAtribute);

        TextView tvTitleFilm = dialog.findViewById(R.id.tv_title_film);
        TextView tvDescription = dialog.findViewById(R.id.tv_description);
        TextView tvPrice = dialog.findViewById(R.id.tv_price);
        ImageView imgFilm = dialog.findViewById(R.id.img_film);

        tvTitleFilm.setText(title);
        tvDescription.setText(description);
        tvPrice.setText(price);
        Picasso.with(mContext).load(img).into(imgFilm);

        // show dialog
        dialog.show();
    }

}
