package com.example.moviettn.tab_layout_home;

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
import com.example.moviettn.model.test.ResponseTest;
import com.example.moviettn.model.test.TestAdapter;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabTvShowsFragment extends Fragment {
    RecyclerView rcvTest;
    TestAdapter testAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_tvshows, container, false);
        initUi();
        getAllFilm();
        rcvTest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvTest.setHasFixedSize(true);
        rcvTest.setAdapter(testAdapter);

        return view;
    }

    private void initUi() {
        rcvTest = view.findViewById(R.id.rcv_test);
    }

    private void getAllFilm(){
        Call<ResponseTest> responseDTOCall = ApiClient.getFilmService().getAllFilmTest(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<ResponseTest>() {
            @Override
            public void onResponse(Call<ResponseTest> call, Response<ResponseTest> response) {
                testAdapter = new TestAdapter(getContext(), response.body().getData());
                rcvTest.setAdapter(testAdapter);
            }

            @Override
            public void onFailure(Call<ResponseTest> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}