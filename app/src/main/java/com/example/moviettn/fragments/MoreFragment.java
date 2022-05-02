package com.example.moviettn.fragments;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.moviettn.R;
import com.example.moviettn.activities.ChangePasswordActivity;
import com.example.moviettn.activities.InformationUserActivity;
import com.example.moviettn.activities.ListFavotireActivity;
import com.example.moviettn.activities.SendFeedBackActivity;
import com.example.moviettn.activities.UpdateInformationUserActivity;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreFragment extends Fragment {

    private ImageView imgUser;
    private TextView tvSendFeedBack;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_more, container, false);
        getProfile();
        initUi();

        tvSendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SendFeedBackActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initUi() {
        imgUser = view.findViewById(R.id.img_user);
        tvSendFeedBack = view.findViewById(R.id.tv_send_feedback);
    }

    public void getProfile() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(getContext(), "Authorization"));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    String im = response.body().getUser().getImage().getUrl();
                    String adult = response.body().getUser().getAdult();
                    StoreUtil.save(getContext(), Contants.adult, adult);
                    String a="1";

                    if (adult.equals(a)) {
                        Glide.with(getContext())
                                .load(im)
                                .into(imgUser);
                    } else {
                        imgUser.setImageResource(R.drawable.logokid);
                    }

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}