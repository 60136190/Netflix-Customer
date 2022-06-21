package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviettn.R;
import com.example.moviettn.adapters.ListCommentFilmAdapter;
import com.example.moviettn.adapters.SeriesFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.CommentRequest;
import com.example.moviettn.model.request.RatingRequest;
import com.example.moviettn.model.response.CommentResponse;
import com.example.moviettn.model.response.DetailFilmResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.model.response.SeriesFilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBillActivity extends AppCompatActivity {

    TextView tvPrice, tvTitleFilm, tvDataPurchase, tvDirector, tvCategory, tvMode;
    ImageView imgMode, imgFilm, imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);
        initUi();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            String idFilm = (String) b.get("idFilm");
            String price = (String) b.get("price");
            String datePurchase = (String) b.get("datePurchase");
            String modeName = (String) b.get("modeName");
            String urlMode = (String) b.get("urlMode");

            tvPrice.setText(String.valueOf(price+" $"));
            tvDataPurchase.setText(String.valueOf(datePurchase.substring(0,10)));
            tvMode.setText(String.valueOf(modeName));
            Picasso.with(DetailBillActivity.this).load(urlMode).into(imgMode);
            getFilm(idFilm);
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initUi() {
        tvPrice = findViewById(R.id.tv_price);
        tvTitleFilm = findViewById(R.id.tv_title_film);
        tvDataPurchase = findViewById(R.id.tv_date_purchase);
        tvDirector = findViewById(R.id.tv_director);
        tvCategory = findViewById(R.id.tv_category);
        tvMode = findViewById(R.id.tv_mode);
        imgMode = findViewById(R.id.img_mode_of_payment);
        imgFilm = findViewById(R.id.img_film);
        imgBack = findViewById(R.id.img_back);
    }

    private void getFilm(String idFilm) {
        Call<DetailFilmResponse> detailFilmResponseCall = ApiClient.getFilmService().detailFilm(
                StoreUtil.get(DetailBillActivity.this, Contants.accessToken), idFilm);
        detailFilmResponseCall.enqueue(new Callback<DetailFilmResponse>() {
            @Override
            public void onResponse(Call<DetailFilmResponse> call, Response<DetailFilmResponse> response) {
                if (response.isSuccessful()) {
                    tvTitleFilm.setText(String.valueOf(response.body().getData().get(0).getTitle()));
                    Picasso.with(DetailBillActivity.this)
                            .load(String.valueOf(response.body().getData().get(0).getImageFilm().getUrl()))
                            .into(imgFilm);

                    if (response.body().getData().get(0).getCategory().isEmpty()) {
                        tvCategory.setText("");
                    } else {
                        String delim = " •";

                        int i = 0;
                        StringBuilder str = new StringBuilder();
                        while (i < response.body().getData().get(0).getCategory().size()-1) {
                            str.append(response.body().getData().get(0).getCategory().get(i).getName());
                            str.append(delim);
                            i++;
                        }
                        str.append(response.body().getData().get(0).getCategory().get(i).getName());
                        String categorys = str.toString();
                        tvCategory.setText(categorys);

                    }

                    if (response.body().getData().get(0).getDirector().isEmpty()) {
                        tvDirector.setText("");
                    } else {
                        String delim = " •";
                        int i = 0;
                        StringBuilder str = new StringBuilder();
                        while (i < response.body().getData().get(0).getDirector().size()-1) {
                            str.append(response.body().getData().get(0).getDirector().get(i).getName());
                            str.append(delim);
                            i++;
                        }
                        str.append(response.body().getData().get(0).getDirector().get(i).getName());
                        String directors = str.toString();
                        tvDirector.setText(directors);

                    }
                }
            }

            @Override
            public void onFailure(Call<DetailFilmResponse> call, Throwable t) {

            }
        });
    }
}