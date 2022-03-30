package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviettn.R;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.ChangePasswordRequest;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private ImageView imgBack;
    private EditText edtNewPassword;
    private EditText edtConfirmNewPassword;
    private EditText edtOldPassword;

    private TextInputLayout tilOldpass;
    private TextInputLayout tilNewpass;
    private TextInputLayout tilConfirmNewpass;

    private Button btnSavePassword;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initUi();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirmNewPassword = findViewById(R.id.edt_confirm_new_password);
        edtOldPassword = findViewById(R.id.edt_old_password);
        btnSavePassword = findViewById(R.id.btn_save_new_password);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        tilOldpass = findViewById(R.id.til_oldpass);
        tilNewpass = findViewById(R.id.til_newpass);
        tilConfirmNewpass = findViewById(R.id.til_confirm_newpass);
    }

    private void changePassword() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Contants.accessToken, StoreUtil.get(ChangePasswordActivity.this, Contants.accessToken));
        hashMap.put(Contants.contentType, "application/json");
        hashMap.put(Contants.contentLength, "<calculated when request is sent>");
        String oldpass = edtOldPassword.getText().toString();
        String newpass = edtNewPassword.getText().toString();
        String confirmNewPass = edtConfirmNewPassword.getText().toString();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(oldpass,newpass,confirmNewPass);

        if (validateOldPassword() && validateNewPassword() && validateConfirmNewPassword()) {
            Call<ResponseDTO> responseDTOCall = ApiClient.getUserService().changePassword(hashMap, changePasswordRequest);
            responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                @Override
                public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                    if (response.body().getStatus() == 200) {
                       setProgressBar();
                    }
                }

                @Override
                public void onFailure(Call<ResponseDTO> call, Throwable t) {

                }
            });
        }

    }

    public void setProgressBar(){
        btnSavePassword.setVisibility(View.INVISIBLE);
        Sprite cubeGrid = new Circle();
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
                onBackPressed();
            }

        };
        countDownTimer.start();
    }

    private boolean validateOldPassword() {
        String oldpass = edtOldPassword.getText().toString().trim();
        if (oldpass.length() < 8){
            tilOldpass.setError("Minimum 8 Character");
            return false;
        }else if (!oldpass.matches(".*[A-Z].*")){
            tilOldpass.setError("Must contain 1 upper-case Character");
            return false;
        }else if (!oldpass.matches(".*[a-z].*")) {
            tilOldpass.setError("Must contain 1 Lower-case Character");
            return false;
        }else if (!oldpass.matches(".*[@!#$%^&*()_+=<>?/|].*")) {
            tilOldpass.setError("Must contain 1 special character (@!#$%^&*()_+=<>?/|)");
            return false;
        }else if (!oldpass.matches(".*[0-9].*")) {
            tilOldpass.setError("Must contain at least 1 number");
            return false;
        }
        else if (!oldpass.matches("\\S+$")) {
            tilOldpass.setError("Must be no white space");
            return false;
        }
        else {
            tilOldpass.setError(null);
            return true;
        }

    }
    private boolean validateNewPassword() {
        String newpass = edtNewPassword.getText().toString().trim();
        if (newpass.length() < 8){
            tilNewpass.setError("Minimum 8 Character");
            return false;
        }else if (!newpass.matches(".*[A-Z].*")){
            tilNewpass.setError("Must contain 1 upper-case Character");
            return false;
        }else if (!newpass.matches(".*[a-z].*")) {
            tilNewpass.setError("Must contain 1 Lower-case Character");
            return false;
        }else if (!newpass.matches(".*[@!#$%^&*()_+=<>?/|].*")) {
            tilNewpass.setError("Must contain 1 special character (@!#$%^&*()_+=<>?/|)");
            return false;
        }else if (!newpass.matches(".*[0-9].*")) {
            tilNewpass.setError("Must contain at least 1 number");
            return false;
        }
        else if (!newpass.matches("\\S+$")) {
            tilNewpass.setError("Must be no white space");
            return false;
        }
        else {
            tilNewpass.setError(null);
            return true;
        }
    }
    private boolean validateConfirmNewPassword() {
        String password = edtNewPassword.getText().toString().trim();
        String confirmPass = edtConfirmNewPassword.getText().toString().trim();
        if (confirmPass.equals(password)){
            tilConfirmNewpass.setError(null);
            return true;
        }
        else {
            tilConfirmNewpass.setError("Password and Confirm are not match");
            return false;
        }
    }
}