package com.example.moviettn.activities;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviettn.R;
import com.example.moviettn.adapters.ListFavoritelFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.ListFavoriteFilmResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.style.Circle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFavotireActivity extends AppCompatActivity {

    RecyclerView recyclerViewListFavorite;
    ListFavoritelFilmAdapter listFavoritelFilmAdapter;
    ImageView imgBack, imgDeleteAllFilm;
    ProgressBar progressBar;

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

        imgDeleteAllFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ListFavotireActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_confirm_delete);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtribute = window.getAttributes();
                window.setAttributes(windowAtribute);

                ProgressBar progressBar = dialog.findViewById(R.id.spin_kit);
                Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                Button btnDeleteAll = dialog.findViewById(R.id.btn_confirm_delete);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // show dialog
                dialog.show();
                btnDeleteAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        deleteAllFilm();
                    }
                });
            }
        });

    }

    private void initUi() {
        recyclerViewListFavorite = findViewById(R.id.rcv_list_favorite);
        imgBack = findViewById(R.id.img_back);
        imgDeleteAllFilm = findViewById(R.id.img_delete);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
    }

    private void getListFavorite() {
        Call<ListFavoriteFilmResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getListFavoriteFilm(
                StoreUtil.get(ListFavotireActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListFavoriteFilmResponse>() {
            @Override
            public void onResponse(Call<ListFavoriteFilmResponse> call, Response<ListFavoriteFilmResponse> response) {
                listFavoritelFilmAdapter = new ListFavoritelFilmAdapter(ListFavotireActivity.this, response.body().getData());
                recyclerViewListFavorite.setAdapter(listFavoritelFilmAdapter);
            }

            @Override
            public void onFailure(Call<ListFavoriteFilmResponse> call, Throwable t) {
                Toast.makeText(ListFavotireActivity.this, "k ok", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAllFilm(){
        Call<ResponseDTO> listFavoriteFilmResponseCall = ApiClient.getFilmService().deleteAllFilmInListFavorite(
                StoreUtil.get(ListFavotireActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
             if (response.isSuccessful()){
                setProgressBar();
             }
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(ListFavotireActivity.this, "k ok", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setProgressBar() {
        Circle cubeGrid = new Circle();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.VISIBLE);

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = progressBar.getProgress();
                if (current >= progressBar.getMax()) {
                    current = 0;
                }
                progressBar.setProgress(current + 10);
            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.INVISIBLE);
                getListFavorite();
            }

        };
        countDownTimer.start();
    }

}