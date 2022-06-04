package com.example.moviettn.tab_layout_home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moviettn.R;
import com.example.moviettn.adapters.AllFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.AllFilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.example.moviettn.utils.TranslateAnimationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabTvShowsFragment extends Fragment {
    RecyclerView rcvAdult;
    AllFilmAdapter testAdapter;
    Button btnToTop;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_tvshows, container, false);
        initUi();
        getAllFilm();
        rcvAdult.setOnTouchListener(new TranslateAnimationUtil(getContext(),btnToTop));
        rcvAdult.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvAdult.setHasFixedSize(true);

        btnToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvAdult.smoothScrollToPosition(0);
            }
        });

        return view;
    }

    private void initUi() {
        rcvAdult = view.findViewById(R.id.rcv_test);
        btnToTop = view.findViewById(R.id.btn_to_top);
    }

    private void getAllFilm(){
        Call<AllFilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilmAdult(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<AllFilmResponse>() {
            @Override
            public void onResponse(Call<AllFilmResponse> call, Response<AllFilmResponse> response) {
                testAdapter = new AllFilmAdapter(getContext(), response.body().getResults());
                rcvAdult.setAdapter(testAdapter);
            }

            @Override
            public void onFailure(Call<AllFilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}