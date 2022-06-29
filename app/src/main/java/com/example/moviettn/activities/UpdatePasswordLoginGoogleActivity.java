package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.moviettn.model.NewPassLoginGoogle;
import com.example.moviettn.model.request.ChangePasswordRequest;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordLoginGoogleActivity extends AppCompatActivity {

    ImageView imgBack;
    EditText edtNewPassword, edtConfirmNewPassword;
    TextInputLayout tilNewpass, tilConfirmNewpass;
    Button btnSavePassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_login_google);
        initUi();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                onBackPressed();
            }
        });

        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPassword();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirmNewPassword = findViewById(R.id.edt_confirm_new_password);
        btnSavePassword = findViewById(R.id.btn_save_new_password);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        tilNewpass = findViewById(R.id.til_newpass);
        tilConfirmNewpass = findViewById(R.id.til_confirm_newpass);
    }

    public void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(UpdatePasswordLoginGoogleActivity.this,gso);
        googleSignInClient.signOut();

    }

    private void createPassword() {
        String newpass = edtNewPassword.getText().toString();
        String confirmNewPass = edtConfirmNewPassword.getText().toString();
        NewPassLoginGoogle changePasswordRequest = new NewPassLoginGoogle( newpass, confirmNewPass);

        if (validateNewPassword() && validateConfirmNewPassword()) {
            Call<ResponseDTO> responseDTOCall = ApiClient.getUserService().createNewPass(
                    StoreUtil.get(UpdatePasswordLoginGoogleActivity.this, "Authorization"), changePasswordRequest);
            responseDTOCall.enqueue(new Callback<ResponseDTO>() {
                @Override
                public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                    if (response.body().getStatus() == 200) {
                        setProgressBar();
                    } else {
                        Toast.makeText(UpdatePasswordLoginGoogleActivity.this
                                , String.valueOf(response.body().getMsg())
                                , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseDTO> call, Throwable t) {

                }
            });
        }

    }

    public void setProgressBar() {
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
                Intent intent = new Intent(UpdatePasswordLoginGoogleActivity.this,SelectStateActivity.class);
                startActivity(intent);
                finish();
            }

        };
        countDownTimer.start();
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