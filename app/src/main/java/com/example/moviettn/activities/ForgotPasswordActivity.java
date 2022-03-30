package com.example.moviettn.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviettn.R;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.ForgetPasswordRequest;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ImageView imgBack;
    private Button btnForgetPassword;
    private EditText edtEmail;
    private TextInputLayout tilEmail;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initUi();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        btnForgetPassword = findViewById(R.id.btn_forget_password);
        edtEmail = findViewById(R.id.edt_email);
        tilEmail = findViewById(R.id.til_email);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
    }

    public void forgetPassword(){
        String email = edtEmail.getText().toString();
        ForgetPasswordRequest forgotPassword = new ForgetPasswordRequest(email);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Contants.contentType, "application/json");
        hashMap.put(Contants.contentLength, "<calculated when request is sent>");
        if (validateEmail()){
            Call<ResponseDTO> forgotPasswordCall = ApiClient.getUserService().forgetPassword(hashMap, forgotPassword);

            forgotPasswordCall.enqueue(new Callback<ResponseDTO>() {
                @Override
                public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                    btnForgetPassword.setVisibility(View.INVISIBLE);
                    Sprite cubeGrid = new Circle();
                    progressBar.setIndeterminateDrawable(cubeGrid);
                    progressBar.setVisibility(View.VISIBLE);

                    CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            int current = progressBar.getProgress();
                            if (current >= progressBar.getMax()){
                                current = 0;
                            }
                            progressBar.setProgress(current + 10);
                        }
                        @Override
                        public void onFinish() {
                            progressBar.setVisibility(View.INVISIBLE);
                            onBackPressed();
                        }
                    };
                    countDownTimer.start();
                }

                @Override
                public void onFailure(Call<ResponseDTO> call, Throwable t) {

                }
            });
        }
    }

    private boolean validateEmail() {
        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty()){
            tilEmail.setError("Email can't empty");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Please enter a valid email address");
            return false;
        }
        else {
            tilEmail.setError(null);
            return true;
        }
    }
}