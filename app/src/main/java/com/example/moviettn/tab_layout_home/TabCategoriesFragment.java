package com.example.moviettn.tab_layout_home;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviettn.R;
import com.example.moviettn.adapters.ListCategoryAdapter;
import com.example.moviettn.adapters.ListDirectorAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.CategoriesResponse;
import com.example.moviettn.model.response.DirectorResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabCategoriesFragment extends Fragment {
    private Button btnChooseCategory, btnChooseDirector;
    private View view;
    ArrayList<String> categoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_categories, container, false);
        initUi();
        chooseCategory();
        chooseDirector();

        return view;
    }

    private void initUi() {
        btnChooseCategory = view.findViewById(R.id.btn_chooseCategory);
        btnChooseDirector = view.findViewById(R.id.btn_chooseDirector);
    }

    private void chooseCategory(){
        btnChooseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_choose_option);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtribute = window.getAttributes();
                window.setAttributes(windowAtribute);

                ImageView imgClose = dialog.findViewById(R.id.img_close);
                TextView tvOption = dialog.findViewById(R.id.tv_option);
                RecyclerView rcvCategory = dialog.findViewById(R.id.rcv_film_follow_category);

                tvOption.setText("Choose your category");
                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Call<CategoriesResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllCategory(
                        StoreUtil.get(getContext(), Contants.accessToken));
                listFavoriteFilmResponseCall.enqueue(new Callback<CategoriesResponse>() {
                    @Override
                    public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                        if (response.isSuccessful()) {
                            ListCategoryAdapter listCategoryAdapter = new ListCategoryAdapter(getContext(), response.body().getData());
                            rcvCategory.setAdapter(listCategoryAdapter);
                            rcvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            rcvCategory.setHasFixedSize(true);
                            rcvCategory.setAdapter(listCategoryAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                // show dialog
                dialog.show();
            }
        });

    }

    private void chooseDirector(){
        btnChooseDirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_choose_option);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtribute = window.getAttributes();
                window.setAttributes(windowAtribute);

                ImageView imgClose = dialog.findViewById(R.id.img_close);
                TextView tvOption = dialog.findViewById(R.id.tv_option);
                RecyclerView rcvCategory = dialog.findViewById(R.id.rcv_film_follow_category);

                tvOption.setText("Choose your director");
                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Call<DirectorResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllDirector(
                        StoreUtil.get(getContext(), Contants.accessToken));
                listFavoriteFilmResponseCall.enqueue(new Callback<DirectorResponse>() {
                    @Override
                    public void onResponse(Call<DirectorResponse> call, Response<DirectorResponse> response) {
                        if (response.isSuccessful()) {
                            ListDirectorAdapter listCategoryAdapter = new ListDirectorAdapter(getContext(), response.body().getData());
                            rcvCategory.setAdapter(listCategoryAdapter);
                            rcvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            rcvCategory.setHasFixedSize(true);
                            rcvCategory.setAdapter(listCategoryAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<DirectorResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                // show dialog
                dialog.show();
            }
        });

    }




//    private void spinner(){
//        Call<CategoriesResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllCategory(
//                StoreUtil.get(getContext(), Contants.accessToken));
//        listFavoriteFilmResponseCall.enqueue(new Callback<CategoriesResponse>() {
//            @Override
//            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
//                for(int i = 0; i < response.body().getData().toArray().length; i++){
//                    String data = response.body().getData().get(i).getId();
//                    categoryList.add(data);
//                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,categoryList);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerCategory.setAdapter(adapter);
//
//                    spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            getListFilmFollowCategory(data);
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}