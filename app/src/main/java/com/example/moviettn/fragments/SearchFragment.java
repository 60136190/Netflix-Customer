package com.example.moviettn.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviettn.R;
import com.example.moviettn.adapters.ListFavoritelFilmAdapter;
import com.example.moviettn.adapters.SearchFilmAdapter;
import com.example.moviettn.adapters.VerticalFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.Film;
import com.example.moviettn.model.response.FilmResponse;
import com.example.moviettn.model.response.ListFavoriteFilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private RecyclerView rcvSearch;
    private SearchFilmAdapter searchFilmAdapter;
    private View view;
    private List<Film> list;
    private EditText edtSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initUi();
        getData();
        // add all country in recycle view
        list = new ArrayList<>();

        rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvSearch.setHasFixedSize(true);
        searchFilmAdapter = new SearchFilmAdapter(getContext(), list);
        rcvSearch.setAdapter(searchFilmAdapter);

        // filter
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        return view;
    }

    private void initUi() {
        rcvSearch = view.findViewById(R.id.rcv_search);
        edtSearch = view.findViewById(R.id.edt_search);
    }

    private void getData() {
        String adult = StoreUtil.get(getContext(), Contants.adult);
        String a ="1";
        if(adult.equals(a)) {
          getFilmAdult();
        }else {
            getFilmKid();
        }
    }

    private void filter(String text) {
        List<Film> filteredList = new ArrayList<>();
        for (Film item : list) {
            if (item.getTitle().toUpperCase().contains(text.toUpperCase())) {
                filteredList.add(item);
            }
        }
        searchFilmAdapter.filterList(filteredList);
    }

    private void getFilmAdult(){
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilm(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                list.addAll(response.body().getData());
                searchFilmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getFilmKid(){
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getFilmKid(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                list.addAll(response.body().getData());
                searchFilmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}