package com.example.moviettn.tab_layout_new_hot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviettn.R;
import com.example.moviettn.adapters.EveyoneWatchingAdapter;
import com.example.moviettn.adapters.TopTenAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.FilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top10Fragment extends Fragment {
    private View view;
    private RecyclerView rcvTopTen;
    private TopTenAdapter topTenAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_top10, container, false);
        initUi();
        getListTopTen();
        rcvTopTen.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvTopTen.setHasFixedSize(true);
        return view;
    }

    private void initUi() {
        rcvTopTen = view.findViewById(R.id.rcv_top_ten);
    }

    private void getListTopTen() {
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilm(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                topTenAdapter = new TopTenAdapter(getContext(), response.body().getData());
                rcvTopTen.setAdapter(topTenAdapter);
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}