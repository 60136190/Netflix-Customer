package com.example.moviettn.tab_layout_home;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.squareup.picasso.Picasso;
import com.steelkiwi.library.SlidingSquareLoaderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabHomeFragment extends Fragment {
    RecyclerView rcvFilm;
    AllFilmAdapter allFilmAdapter;
    TextView tv_Title, tvMyList;
    ImageView img_Film, img_AddMyList, infoFilm;
    Button btn_Play;
    ProgressBar progressBar;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_home, container, false);
        initUi();
        getProfile();

        return view;
    }

    private void initUi() {
        rcvFilm = view.findViewById(R.id.rcv_film);
        img_Film = view.findViewById(R.id.img_film);
        infoFilm = view.findViewById(R.id.img_info);
        btn_Play = view.findViewById(R.id.btn_play);
        img_AddMyList = view.findViewById(R.id.img_my_list);
        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        tv_Title = view.findViewById(R.id.tv_title_film);
        tvMyList = view.findViewById(R.id.mylist);
    }

    private void getDataAllFilmAdult() {
        Call<AllFilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilmAdult(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<AllFilmResponse>() {
            @Override
            public void onResponse(Call<AllFilmResponse> call, Response<AllFilmResponse> response) {
                allFilmAdapter = new AllFilmAdapter(getContext(), response.body().getResults());
                rcvFilm.setAdapter(allFilmAdapter);
                String title = response.body().getResults().get(0).getData().get(0).getTitle();
                String idFilm = response.body().getResults().get(0).getData().get(0).getId();
                String price = String.valueOf(response.body().getResults().get(0).getData().get(0).getPrice());
                String description = response.body().getResults().get(0).getData().get(0).getDescription();
                String img = response.body().getResults().get(0).getData().get(0).getImageTitle().getUrl();
                tv_Title.setText(title);
                img_AddMyList.setVisibility(View.VISIBLE);
                tvMyList.setVisibility(View.VISIBLE);

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

                infoFilm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), DetailFilmActivity.class);
                        i.putExtra("Id_film", idFilm);
                        startActivity(i);
                    }
                });

                    String delim = " •";
                    int i = 0;
                    StringBuilder str = new StringBuilder();
                    StringBuilder strCategory = new StringBuilder();
                    while (i < response.body().getResults().get(0).getData().get(0).getDirector().size()-1) {
                        str.append(response.body().getResults().get(0).getData().get(0).getDirector().get(i).getName());
                        str.append(delim);
                        i++;
                    }
                    str.append(response.body().getResults().get(0).getData().get(0).getDirector().get(i).getName());
                    String directors = str.toString();

                while (i < response.body().getResults().get(0).getData().get(0).getCategory().size()-1) {
                    strCategory.append(response.body().getResults().get(0).getData().get(0).getCategory().get(i).getName());
                    strCategory.append(delim);
                    i++;
                }
                strCategory.append(response.body().getResults().get(0).getData().get(0).getCategory().get(i).getName());
                String category = strCategory.toString();

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
                                            setProgres();
                                        }

                                        @Override
                                        public void onFinish() {
                                            setProgres();

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
        Call<AllFilmResponse> responseDTOCall = ApiClient.getFilmService().getAllFilmKid(
                StoreUtil.get(getContext(), Contants.accessToken));
        responseDTOCall.enqueue(new Callback<AllFilmResponse>() {
            @Override
            public void onResponse(Call<AllFilmResponse> call, Response<AllFilmResponse> response) {
                allFilmAdapter = new AllFilmAdapter(getContext(), response.body().getResults());
                rcvFilm.setAdapter(allFilmAdapter);
                if (response.body().getResults().size()!=0) {
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

                    infoFilm.setOnClickListener(new View.OnClickListener() {
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
                                                setProgres();
                                            }

                                            @Override
                                            public void onFinish() {
                                                setProgres();
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
                }

                @Override
                public void onFailure (Call < AllFilmResponse > call, Throwable t){
                    t.printStackTrace();
                }
        });
    }

    public void getProfile() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(getContext(), "Authorization"));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                String adult = response.body().getUser().getAdult();
                String a = "1";
                if (adult.equals(a)) {
                    getDataAllFilmAdult();
                    rcvFilm.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    rcvFilm.setHasFixedSize(true);
                }else{
                    getDataFilmKid();
                    rcvFilm.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    rcvFilm.setHasFixedSize(true);
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProgres() {
        Circle foldingCube = new Circle();
        progressBar.setIndeterminateDrawable(foldingCube);
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
            }

        };
        countDownTimer.start();
    }

}