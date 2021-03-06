package com.example.moviettn.tab_layout_new_hot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviettn.R;
import com.example.moviettn.activities.ListFavotireActivity;
import com.example.moviettn.adapters.ComingSoonAdapter;
import com.example.moviettn.adapters.ListFavoritelFilmAdapter;
import com.example.moviettn.adapters.SearchFilmAdapter;
import com.example.moviettn.adapters.VerticalFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.FilmResponse;
import com.example.moviettn.model.response.ListFavoriteFilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComingSoonFragment extends Fragment {

    private RecyclerView rcvComingSoon;
    private ComingSoonAdapter comingSoonAdapter;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coming_soon, container, false);
        initUi();
        getListComingSoon();
        rcvComingSoon.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvComingSoon.setHasFixedSize(true);
        return view;
    }

    private void initUi() {
        rcvComingSoon = view.findViewById(R.id.rcv_coming_soon);
    }

    private void getListComingSoon() {
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilm(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                comingSoonAdapter = new ComingSoonAdapter(getContext(), response.body().getData());
                rcvComingSoon.setAdapter(comingSoonAdapter);
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}