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
import com.example.moviettn.activities.DetailFilmActivity;
import com.example.moviettn.model.response.DataAllFilm;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<DataAllFilm> testList;

    public DetailFilmAdapter(Context mContext, List<DataAllFilm> testList) {
        this.mContext = mContext;
        this.testList = testList;
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
        DataAllFilm dataAllFilm = testList.get(position);
        String imageUrl = dataAllFilm.getImageFilm().getUrl();
        ((ItemViewHolder) holder).itemTitle.setText(dataAllFilm.getTitle());
        ((ItemViewHolder) holder).itemDes.setText(dataAllFilm.getDescription());
        ((ItemViewHolder) holder).itemLimitAge.setText(dataAllFilm.getAgeLimit().toString());
        Picasso.with(mContext)
                .load(imageUrl).error(R.drawable.backgroundslider).fit().centerInside().into(((ItemViewHolder) holder).itemImage);

        ((ItemViewHolder) holder).constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailFilmActivity.class);
                StoreUtil.save(mContext,Contants.idFilm,dataAllFilm.getId());
                StoreUtil.save(mContext,Contants.price,String.valueOf(dataAllFilm.getPrice()));
                StoreUtil.save(mContext,Contants.titleFilm,String.valueOf(dataAllFilm.getTitle()));
                StoreUtil.save(mContext,Contants.urlFilm,String.valueOf(dataAllFilm.getImageFilm().getUrl()));
                mContext.startActivity(i);

            }
        });

    }


    @Override
    public int getItemCount() {
        if (testList != null){
            return testList.size();
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
