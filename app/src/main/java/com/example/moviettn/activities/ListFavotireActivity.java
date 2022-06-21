package com.example.moviettn.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviettn.R;
import com.example.moviettn.adapters.ListFavoritelFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.ListFavoriteFilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFavotireActivity extends AppCompatActivity {

    RecyclerView recyclerViewListFavorite;
    ListFavoritelFilmAdapter listFavoritelFilmAdapter;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_favotire);
        initUi();
        getListFavorite();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ListFavotireActivity.this, 3);
        recyclerViewListFavorite.setLayoutManager(gridLayoutManager);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initUi() {
        recyclerViewListFavorite = findViewById(R.id.rcv_list_favorite);
        imgBack = findViewById(R.id.img_back);
    }

    private void getListFavorite() {
        Call<ListFavoriteFilmResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getListFavoriteFilm(
                StoreUtil.get(ListFavotireActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListFavoriteFilmResponse>() {
            @Override
            public void onResponse(Call<ListFavoriteFilmResponse> call, Response<ListFavoriteFilmResponse> response) {
                listFavoritelFilmAdapter = new ListFavoritelFilmAdapter(ListFavotireActivity.this, response.body().getData());
                recyclerViewListFavorite.setAdapter(listFavoritelFilmAdapter);
                listFavoritelFilmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListFavoriteFilmResponse> call, Throwable t) {
                Toast.makeText(ListFavotireActivity.this, "k ok", Toast.LENGTH_SHORT).show();
            }
        });
    }
}