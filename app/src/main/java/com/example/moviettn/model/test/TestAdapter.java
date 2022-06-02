package com.example.moviettn.model.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.activities.DetailVideoActivity;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.SeriesFilm;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Datum> datumList;


    public TestAdapter(Context mContext, List<Datum> datumList) {
        this.mContext = mContext;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Datum datum = datumList.get(position);
        ((ItemViewHolder)holder).tvTitle.setText(datum.getCountryProduction());
        int a = position;

        Call<ResponseTest> responseDTOCall = ApiClient.getFilmService().getAllFilmTest(
                StoreUtil.get(mContext, Contants.accessToken));
        responseDTOCall.enqueue(new Callback<ResponseTest>() {
            @Override
            public void onResponse(Call<ResponseTest> call, Response<ResponseTest> response) {
                DetailAdapter adapter;
                adapter = new DetailAdapter(mContext, response.body().getData().get(a).getCategory());
                ((ItemViewHolder)holder).rcv_detail.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseTest> call, Throwable t) {
                t.printStackTrace();
            }
        });

        ((ItemViewHolder)holder).rcv_detail.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        ((ItemViewHolder)holder).rcv_detail.setHasFixedSize(true);


    }


    @Override
    public int getItemCount() {
        if (datumList != null){
            return datumList.size();
        }
        return 0;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        RecyclerView rcv_detail;

        public ItemViewHolder( View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rcv_detail = itemView.findViewById(R.id.rcv_film);
        }
    }

}
