package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.moviettn.R;
import com.example.moviettn.adapters.VerticalFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.FilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFilmFollowOptionActivity extends AppCompatActivity {
    private VerticalFilmAdapter verticalFilmAdapter;
    private TextView tvCategory;
    private RecyclerView rcvCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailFilmFollowOptionActivity.this, 3);
        intiUi();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b!= null){
            if(b.get("option").equals("1")){
                String idCategory = (String) b.get("id_category");
                String name = (String) b.get("name_of_category");
                tvCategory.setText(name);
                getListFilmFollowCategory(idCategory);
                rcvCategory.setLayoutManager(gridLayoutManager);
            }

            if(b.get("option").equals("0")){
                String idDirector = (String) b.get("id_director");
                String nameOfDirector = (String) b.get("name_of_director");
                tvCategory.setText(nameOfDirector);
                getListFilmFollowDirector(idDirector);
                rcvCategory.setLayoutManager(gridLayoutManager);
            }

        }
    }

    private void intiUi() {
        tvCategory = findViewById(R.id.tv_category);
        rcvCategory = findViewById(R.id.rcv_detail_category);
    }

    private void getListFilmFollowCategory(String idCategory){
        Call<FilmResponse> listDirectorResponseCall = ApiClient.getFilmService().getFilmFollowCategory(
                StoreUtil.get(DetailFilmFollowOptionActivity.this, Contants.accessToken),idCategory);
        listDirectorResponseCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                if (response.isSuccessful()){
                    verticalFilmAdapter = new VerticalFilmAdapter(DetailFilmFollowOptionActivity.this, response.body().getData());
                    rcvCategory.setAdapter(verticalFilmAdapter);
                }
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getListFilmFollowDirector(String idDirector){
        Call<FilmResponse> listDirectorResponseCall = ApiClient.getFilmService().getFilmFollowDirector(
                StoreUtil.get(DetailFilmFollowOptionActivity.this, Contants.accessToken),idDirector);
        listDirectorResponseCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                if (response.isSuccessful()){
                    verticalFilmAdapter = new VerticalFilmAdapter(DetailFilmFollowOptionActivity.this, response.body().getData());
                    rcvCategory.setAdapter(verticalFilmAdapter);
                }
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}