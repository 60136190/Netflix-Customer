package com.example.moviettn.tab_layout_home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviettn.R;
import com.example.moviettn.activities.DetailFilmActivity;
import com.example.moviettn.adapters.AllFilmAdapter;
import com.example.moviettn.adapters.EveyoneWatchingAdapter;
import com.example.moviettn.adapters.VerticalFilmAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.fragments.HomeFragment;
import com.example.moviettn.model.response.AllFilmResponse;
import com.example.moviettn.model.response.FilmResponse;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.example.moviettn.utils.TranslateAnimationUtil;
import com.squareup.picasso.Picasso;
import com.steelkiwi.library.SlidingSquareLoaderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabHomeFragment extends Fragment {
    RecyclerView rcvAdult, rcvKid;
    AllFilmAdapter allFilmAdapter;
    private View view;

     TextView tv_Title;
     ImageView img_Film;
     ImageView img_AddMyList;
    private Button btn_Play;
    private SlidingSquareLoaderView slidingSquareLoaderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_home, container, false);
        initUi();
        getProfile();
        getDataAllFilmAdult();
        rcvAdult.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvAdult.setHasFixedSize(true);

        return view;
    }

    private void initUi() {
        rcvKid = view.findViewById(R.id.rcv_kid);
        rcvAdult = view.findViewById(R.id.rcv_adult);
        rcvKid = view.findViewById(R.id.rcv_kid);
        img_Film = view.findViewById(R.id.img_film);
        btn_Play = view.findViewById(R.id.btn_play);
        img_AddMyList = view.findViewById(R.id.img_my_list);
        slidingSquareLoaderView = view.findViewById(R.id.progress_item_add_list);
        tv_Title = view.findViewById(R.id.tv_title_film);
    }

    private void getDataAllFilmAdult() {
        Call<AllFilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilmAdult(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<AllFilmResponse>() {
            @Override
            public void onResponse(Call<AllFilmResponse> call, Response<AllFilmResponse> response) {
                allFilmAdapter = new AllFilmAdapter(getContext(), response.body().getResults());
                rcvAdult.setAdapter(allFilmAdapter);
                String title = response.body().getResults().get(0).getData().get(0).getTitle();
                String idFilm = response.body().getResults().get(0).getData().get(0).getId();
                String img = response.body().getResults().get(0).getData().get(0).getImageTitle().getUrl();
                tv_Title.setText(title);

                Picasso.with(getContext())
                        .load(img).error(R.drawable.backgroundslider).fit().centerInside().into(img_Film);

                btn_Play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), DetailFilmActivity.class);
                        i.putExtra("Id_film", idFilm);
                        startActivity(i);
                    }
                });

                img_AddMyList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<ResponseDTO> responseDTOCall = ApiClient.getFilmService().addFavoriteFilm(
                                StoreUtil.get(v.getContext(), Contants.accessToken), idFilm);
                        responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                if (response.isSuccessful()) {
                                    CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            slidingSquareLoaderView.show();
                                        }

                                        @Override
                                        public void onFinish() {
                                            slidingSquareLoaderView.setVisibility(View.INVISIBLE);

                                        }

                                    };
                                    countDownTimer.start();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                            }
                        });
                    }
                });

            }

            @Override
            public void onFailure(Call<AllFilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getDataFilmKid() {
        Call<FilmResponse> responseDTOCall = ApiClient.getFilmService().getFilmKid(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void listFilmKid() {
        getDataFilmKid();
        rcvKid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvKid.setHasFixedSize(true);
        rcvKid.setAdapter(allFilmAdapter);
    }

    public void getProfile() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(getContext(), "Authorization"));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                String adult = response.body().getUser().getAdult();
                String a = "0";
                if (adult.equals(a)) {
                    rcvKid.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}