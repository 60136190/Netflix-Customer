package com.example.moviettn.tab_layout_new_hot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviettn.R;
import com.example.moviettn.adapters.ComingSoonAdapter;
import com.example.moviettn.adapters.EveyoneWatchingAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.FilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchingFragment extends Fragment {
    private View view;
    private RecyclerView rcv_everyone_watching;
    private EveyoneWatchingAdapter eveyoneWatchingAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_watching, container, false);
        initUi();
        getListComingSoon();
        rcv_everyone_watching.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_everyone_watching.setHasFixedSize(true);

        return view;
    }


    private void initUi() {
        rcv_everyone_watching = view.findViewById(R.id.rcv_everyone_watching);
    }

    private void getListComingSoon() {
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilm(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                eveyoneWatchingAdapter = new EveyoneWatchingAdapter(getContext(), response.body().getData());
                rcv_everyone_watching.setAdapter(eveyoneWatchingAdapter);
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}