package com.example.moviettn.tab_layout_home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.moviettn.R;
import com.example.moviettn.adapters.AllFilmAdapter;
import com.example.moviettn.adapters.VerticalFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.AllFilmResponse;
import com.example.moviettn.model.response.FilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.example.moviettn.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabMoviesFragment extends Fragment {

    RecyclerView rcvListMovies;
    View view;
    private AllFilmAdapter testAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_movies, container, false);
        initUi();
        setData();

        return view;
    }

    private void initUi() {
        rcvListMovies = view.findViewById(R.id.rcv_list_movie);
    }

    private void getAllFilm(){
        Call<AllFilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilmAdult(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<AllFilmResponse>() {
            @Override
            public void onResponse(Call<AllFilmResponse> call, Response<AllFilmResponse> response) {
                testAdapter = new AllFilmAdapter(getContext(), response.body().getResults());
                rcvListMovies.setAdapter(testAdapter);
            }

            @Override
            public void onFailure(Call<AllFilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setData(){
        String adult = StoreUtil.get(getContext(),Contants.adult);
        String a = "1";
        if (adult.equals(a)){
            rcvListMovies.setVisibility(View.VISIBLE);
            getAllFilm();
            rcvListMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rcvListMovies.setHasFixedSize(true);
        }else{
            rcvListMovies.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}