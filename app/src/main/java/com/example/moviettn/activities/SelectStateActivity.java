package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moviettn.R;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.DeleteImageRequest;
import com.example.moviettn.model.request.UpdateStateUserRequest;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.model.response.UpdateStateUserResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectStateActivity extends AppCompatActivity {

    private ImageView imgAdult;
    private ImageView imgKid;
    private GifImageView gifChangeState;
    private UpdateStateUserRequest updateStateUserRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_state);
        initUi();
        imgAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateStateUsertoAdultorKid("1");
                Intent intent = new Intent(SelectStateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imgKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateStateUsertoAdultorKid("0");
                CountDownTimer countDownTimer = new CountDownTimer(3500, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        gifChangeState.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        gifChangeState.setVisibility(View.GONE);
                        Intent intent = new Intent(SelectStateActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                };
                countDownTimer.start();

            }
        });
    }

    private void initUi() {
        imgAdult = findViewById(R.id.img_adult);
        imgKid = findViewById(R.id.img_kid);
        gifChangeState = findViewById(R.id.gif_change_state);

    }
    private void UpdateStateUsertoAdultorKid(String number) {
        updateStateUserRequest = new UpdateStateUserRequest(number);
        Call<UpdateStateUserResponse> updateStateAdult = ApiClient.getUserService().updateStateUser(
                StoreUtil.get(SelectStateActivity.this, Contants.accessToken), updateStateUserRequest);
        updateStateAdult.enqueue(new Callback<UpdateStateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateStateUserResponse> call, Response<UpdateStateUserResponse> response) {
                if (response.isSuccessful()){
                    String message = response.body().getMsg();
                    Log.d("AAAAAAAAAAAAAAAAAAAAAAA",message);

                }else {
                    Log.d("AAAAAAAAAAAAAAAAAAAAAAA","thuaa");
                }
            }

            @Override
            public void onFailure(Call<UpdateStateUserResponse> call, Throwable t) {

            }
        });
    }

}