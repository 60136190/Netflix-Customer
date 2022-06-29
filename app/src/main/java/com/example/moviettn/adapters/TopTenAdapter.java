package com.example.moviettn.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailFilmActivity;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.Film;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.style.Circle;
import com.squareup.picasso.Picasso;
//import com.steelkiwi.library.SlidingSquareLoaderView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopTenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Film> mFilmList;

    public TopTenAdapter(Context mContext, List<Film> mFilmList) {
        this.mContext = mContext;
        this.mFilmList = mFilmList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_ten,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Film film = mFilmList.get(position);
            String imageUrl = film.getImageFilm().getUrl();
            ((ItemViewHolder) holder).itemTitle.setText(film.getTitle());
            ((ItemViewHolder) holder).itemNo.setText("0" + String.valueOf(position + 1));
            ((ItemViewHolder) holder).itemStoryLine.setText(film.getDescription());
            ((ItemViewHolder) holder).itemLimitAge.setText(film.getAgeLimit() + "+");
            ((ItemViewHolder) holder).itemAddMyList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().addFavoriteFilm(
                            StoreUtil.get(v.getContext(), Contants.accessToken), film.getId());
                    responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                        @Override
                        public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                            if (response.isSuccessful()) {
                                Circle cubeGrid = new Circle();
                                ((ItemViewHolder) holder).progressBar.setIndeterminateDrawable(cubeGrid);
                                ((ItemViewHolder) holder).progressBar.setVisibility(View.VISIBLE);

                                CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        int current = ((ItemViewHolder) holder).progressBar.getProgress();
                                        if (current >= ((ItemViewHolder) holder).progressBar.getMax()) {
                                            current = 0;
                                        }
                                        ((ItemViewHolder) holder).progressBar.setProgress(current + 10);

                                    }

                                    @Override
                                    public void onFinish() {
                                        ((ItemViewHolder) holder).progressBar.setVisibility(View.INVISIBLE);
                                    }

                                };
                                countDownTimer.start();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseDTO> call, Throwable t) {
                        }
                    });
                }
            });
            ((ItemViewHolder) holder).itemPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, DetailFilmActivity.class);
                    String strName = film.getId();
                    i.putExtra("Id_film", strName);
                    mContext.startActivity(i);
                }
            });

            if (film.getDirector().isEmpty()) {
                ((ItemViewHolder) holder).itemDirectors.setText("");
            } else {
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
        private ImageView itemAddMyList;
        private ImageView itemPlay;
        private TextView itemSeriesFilm;
        private ImageView itemLogo;
        private TextView itemNo;
        private TextView itemLimitAge;
        private TextView itemStoryLine;
        private TextView itemTitle;
        private TextView itemDirectors;
        ProgressBar progressBar;
//        private SlidingSquareLoaderView slidingSquareLoaderView;

        public ItemViewHolder( View itemView) {
            super(itemView);
            itemBanner = itemView.findViewById(R.id.img_banner);
            itemNo = itemView.findViewById(R.id.tv_no);
            itemAddMyList = itemView.findViewById(R.id.img_my_list);
            itemPlay = itemView.findViewById(R.id.img_play);
            itemTitle = itemView.findViewById(R.id.tv_title_film);
            itemLimitAge = itemView.findViewById(R.id.tv_limit_age);
            itemStoryLine = itemView.findViewById(R.id.tv_storyline);
            itemDirectors = itemView.findViewById(R.id.tv_directors);
            progressBar = itemView.findViewById(R.id.spin_kit);
//            itemSeriesFilm = itemView.findViewById(R.id.tv_series_film);
            itemLogo = itemView.findViewById(R.id.img_logo_netflix);
//            slidingSquareLoaderView = itemView.findViewById(R.id.progress_item_add_list);
        }
    }

}
