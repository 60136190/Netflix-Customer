package com.example.moviettn.tab_layout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviettn.R;
import com.example.moviettn.adapters.VerticalFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.FilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabHomeFragment extends Fragment {

    private RecyclerView rcvPersonal;
    private RecyclerView rcvTrending;
    private RecyclerView rcvPeriodPieces;
    private VerticalFilmAdapter verticalFilmAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_home, container, false);
        initUi();
        listTopFilm();
        listTrending();
        listPeriodPieces();

        return view;
    }

    private void initUi() {
        rcvPersonal = view.findViewById(R.id.rcv_personal);
        rcvTrending = view.findViewById(R.id.rcv_trending);
        rcvPeriodPieces = view.findViewById(R.id.rcv_periodpieces);
    }

    private void getDataAllFilm() {
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilm(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                verticalFilmAdapter = new VerticalFilmAdapter(getContext(), response.body().getData());
                rcvPersonal.setAdapter(verticalFilmAdapter);
                rcvTrending.setAdapter(verticalFilmAdapter);
                rcvPeriodPieces.setAdapter(verticalFilmAdapter);
                verticalFilmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void listTopFilm() {
        getDataAllFilm();
        rcvPersonal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvPersonal.setHasFixedSize(true);
        rcvPersonal.setAdapter(verticalFilmAdapter);
    }

    private void listTrending() {
        getDataAllFilm();
        rcvTrending.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvTrending.setHasFixedSize(true);
        rcvTrending.setAdapter(verticalFilmAdapter);
    }

    private void listPeriodPieces() {
        getDataAllFilm();
        rcvPeriodPieces.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvPeriodPieces.setHasFixedSize(true);
        rcvPeriodPieces.setAdapter(verticalFilmAdapter);
    }
}