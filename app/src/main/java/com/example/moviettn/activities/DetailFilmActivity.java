package com.example.moviettn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.moviettn.R;
import com.example.moviettn.adapters.ListCommentFilmAdapter;
import com.example.moviettn.adapters.SeriesFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.CommentRequest;
import com.example.moviettn.model.request.RatingRequest;
import com.example.moviettn.model.response.CommentResponse;
import com.example.moviettn.model.response.DetailFilmResponse;
import com.example.moviettn.model.response.ListFavoriteFilmResponse;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.model.response.SeriesFilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFilmActivity extends AppCompatActivity {

    private ImageView imgShare;
    private ImageView imgSendComment;
    private EditText edtComment;
    private ImageView imgUser;
    private ImageView imgFilm;
    private ImageView imgPlay;
    private CheckBox checkFavorite;
    private TextView tvDirector;
    private TextView tvNameOfFilm;
    private TextView tvDescription;
    private TextView tvDate;
    private TextView tvCategory;
    private TextView tvTime;
    private TextView tvCountry;
    private RatingBar ratingBar;
    private RecyclerView recyclerViewComment;
    private RecyclerView rcvSeriesFilm;
    private ListCommentFilmAdapter listCommentFilmAdapter;
    private SeriesFilmAdapter seriesFilmAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        initUi();
        getImageUser();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            String idFilm = (String) b.get("Id_film");
            Call<DetailFilmResponse> detailFilmResponseCall = ApiClient.getFilmService().detailFilm(
                    StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
            detailFilmResponseCall.enqueue(new Callback<DetailFilmResponse>() {
                @Override
                public void onResponse(Call<DetailFilmResponse> call, Response<DetailFilmResponse> response) {
                    if (response.isSuccessful()) {
                        tvNameOfFilm.setText(response.body().getData().get(0).getTitle());
                        tvDescription.setText(response.body().getData().get(0).getDescription());
                        for (int i = 0; i < response.body().getData().get(0).getDirector().size(); i++) {
                            tvDirector.setText(response.body().getData().get(0).getDirector().get(i).getName());
                        }

                        String strImgFilm = response.body().getData().get(0).getImageFilm().getUrl();
                        Picasso.with(DetailFilmActivity.this)
                                .load(strImgFilm).error(R.drawable.backgroundslider).fit().centerInside().into(imgFilm);
                        String rating = response.body().getAvgScore();
                        String string = response.body().getData().get(0).getYearProduction();
                        String[] parts = string.split("T");
                        String part1 = parts[0]; // 004

                        tvDate.setText(part1);
                        tvCategory.setText(response.body().getData().get(0).getCategory().get(0).getName());
                        tvTime.setText(response.body().getData().get(0).getFilmLength());
                        tvCountry.setText(response.body().getData().get(0).getCountryProduction());
                        ratingBar.setRating(Float.parseFloat(rating));

                    }
                }

                @Override
                public void onFailure(Call<DetailFilmResponse> call, Throwable t) {

                }
            });

            // add favorite film
            checkFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().addFavoriteFilm(
                                StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
                        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                            }
                        });
                    } else {
                        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().deleteFavoriteFilm(
                                StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
                        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {

                            }
                        });
                    }
                }
            });

            // rating film
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar rt, float rating, boolean fromUser) {
                    RatingRequest ratingRequest = new RatingRequest(rating);
                    Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().addRatingFilm(
                            StoreUtil.get(DetailFilmActivity.this, Contants.accessToken)
                            , idFilm, ratingRequest);
                    responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                        @Override
                        public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseDTO> call, Throwable t) {
                        }
                    });
                }
            });

            // comment film
            imgSendComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtComment != null) {
                        String comment = edtComment.getText().toString();
                        CommentRequest commentRequest = new CommentRequest(comment);
                        Call<ResponseDTO> responseCommentDTOCall = ApiClient.getFilmService().addCommentFilm(
                                StoreUtil.get(DetailFilmActivity.this, Contants.accessToken)
                                , idFilm, commentRequest);
                        responseCommentDTOCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                Toast.makeText(DetailFilmActivity.this, "Send success", Toast.LENGTH_SHORT).show();

                                // get list comment after add a new comment
                                Call<CommentResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllCommentFollowFilm(
                                        StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
                                listFavoriteFilmResponseCall.enqueue(new Callback<CommentResponse>() {
                                    @Override
                                    public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                                        listCommentFilmAdapter = new ListCommentFilmAdapter(DetailFilmActivity.this, response.body().getData());
                                        recyclerViewComment.setAdapter(listCommentFilmAdapter);
                                        edtComment.setText("");

                                    }

                                    @Override
                                    public void onFailure(Call<CommentResponse> call, Throwable t) {
                                    }
                                });

                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                                Toast.makeText(DetailFilmActivity.this, "Send wroong", Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        Toast.makeText(DetailFilmActivity.this, "Your comment is blank", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            //get list comment
            Call<CommentResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllCommentFollowFilm(
                    StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
            listFavoriteFilmResponseCall.enqueue(new Callback<CommentResponse>() {
                @Override
                public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                    listCommentFilmAdapter = new ListCommentFilmAdapter(DetailFilmActivity.this, response.body().getData());
                    recyclerViewComment.setAdapter(listCommentFilmAdapter);
                }

                @Override
                public void onFailure(Call<CommentResponse> call, Throwable t) {
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailFilmActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewComment.setLayoutManager(linearLayoutManager);

            // get series film
            Call<SeriesFilmResponse> serieslFilmResponseCall = ApiClient.getFilmService().getSeries(
                    StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
            serieslFilmResponseCall.enqueue(new Callback<SeriesFilmResponse>() {
                @Override
                public void onResponse(Call<SeriesFilmResponse> call, Response<SeriesFilmResponse> response) {
                    seriesFilmAdapter = new SeriesFilmAdapter(DetailFilmActivity.this, response.body().getData().get(0).getSeriesFilm());
                    rcvSeriesFilm.setAdapter(seriesFilmAdapter);
                    seriesFilmAdapter.notifyDataSetChanged();
                    String strName = response.body().getData().get(0).getSeriesFilm().get(0).getUrlVideo();
                    imgPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(DetailFilmActivity.this, DetailVideoActivity.class);
                            i.putExtra("video", strName);
                            startActivity(i);
                        }
                    });
                }

                @Override
                public void onFailure(Call<SeriesFilmResponse> call, Throwable t) {

                }
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailFilmActivity.this, 2);
            rcvSeriesFilm.setLayoutManager(gridLayoutManager);


            //get list favorite
            Call<ListFavoriteFilmResponse> listFavoriteFilm = ApiClient.getFilmService().getListFavoriteFilm(
                    StoreUtil.get(DetailFilmActivity.this, Contants.accessToken));
            listFavoriteFilm.enqueue(new Callback<ListFavoriteFilmResponse>() {
                @Override
                public void onResponse(Call<ListFavoriteFilmResponse> call, Response<ListFavoriteFilmResponse> response) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (response.body().getData().get(i).getFilm().getId().equals(idFilm)) {
                            checkFavorite.setChecked(true);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListFavoriteFilmResponse> call, Throwable t) {

                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // get list comment
                    Call<CommentResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllCommentFollowFilm(
                            StoreUtil.get(DetailFilmActivity.this, Contants.accessToken), idFilm);
                    listFavoriteFilmResponseCall.enqueue(new Callback<CommentResponse>() {
                        @Override
                        public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                            listCommentFilmAdapter = new ListCommentFilmAdapter(DetailFilmActivity.this, response.body().getData());
                            recyclerViewComment.setAdapter(listCommentFilmAdapter);
                            listCommentFilmAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<CommentResponse> call, Throwable t) {
                        }
                    });
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }
            });


        }

    }

    private void initUi() {
        checkFavorite = findViewById(R.id.check_favorite);
        imgShare = findViewById(R.id.img_share);
        tvDirector = findViewById(R.id.tv_director);
        tvNameOfFilm = findViewById(R.id.tv_title_film);
        tvDescription = findViewById(R.id.tv_storyline);
        tvDate = findViewById(R.id.tv_date_film);
        tvCategory = findViewById(R.id.tv_category);
        tvCountry = findViewById(R.id.tv_country);
        imgFilm = findViewById(R.id.img_film);
        tvTime = findViewById(R.id.tv_time_film);
        ratingBar = findViewById(R.id.rating_bar);
        imgUser = findViewById(R.id.img_user);
        imgSendComment = findViewById(R.id.img_send_comment);
        imgPlay = findViewById(R.id.img_play);
        edtComment = findViewById(R.id.edt_comment);
        recyclerViewComment = findViewById(R.id.rcv_comment);
        rcvSeriesFilm = findViewById(R.id.rcv_series_film);
        swipeRefreshLayout = findViewById(R.id.refresh);

    }

    private void getImageUser() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(DetailFilmActivity.this, "Authorization"));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    String im = response.body().getUser().getImage().getUrl();
                    Glide.with(DetailFilmActivity.this)
                            .load(im)
                            .into(imgUser);
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
            }
        });
    }
}