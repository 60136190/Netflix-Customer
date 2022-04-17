package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviettn.R;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.LoginGoogleRequest;
import com.example.moviettn.model.request.LoginRequest;
import com.example.moviettn.model.response.LoginGoogleResponse;
import com.example.moviettn.model.response.LoginResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageView imgBack;
    private ImageView imgLoginGoogle;
    private TextView tvSupport;
    private TextView tvRegister;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View viewdialog = getLayoutInflater().inflate(R.layout.bottom_sheet_help, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(LoginActivity.this);
                bottomSheetDialog.setContentView(viewdialog);
                bottomSheetDialog.show();

                LinearLayout lnForgotPassword = viewdialog.findViewById(R.id.ln_forgot_password);
                lnForgotPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        imgLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
//                loginGoogle();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        imgLoginGoogle = findViewById(R.id.img_login_google);
        tvSupport = findViewById(R.id.tv_support);
        tvRegister = findViewById(R.id.tv_register);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    public void login() {
        String email = edtEmail.getText().toString();
        String pass = edtPassword.getText().toString();
        LoginRequest loginRequest = new LoginRequest(email, pass);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Contants.contentType, "application/json");
        hashMap.put(Contants.contentLength, "<calculated when request is sent>");
        if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
            String message = "Email or password blank...";
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        } else {
            Call<LoginResponse> loginResponeCall = ApiClient.getUserService().login(hashMap, loginRequest);

            loginResponeCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.body().getStatus() == 200) {
                        String string = response.headers().value(2);
                        String[] parts = string.split(";");
                        String part1 = parts[0]; // 004
                        StoreUtil.save(LoginActivity.this, Contants.refreshToken, part1);

                        StoreUtil.save(LoginActivity.this, Contants.accessToken, response.body().getAccessToken());
                        Intent intentslide = new Intent(LoginActivity.this, SelectStateActivity.class);
                        startActivity(intentslide);
                        finish();
                    } else {
                        String message = response.body().getMsg();
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(Contants.contentType, "application/json");
            hashMap.put(Contants.contentLength, "<calculated when request is sent>");
            hashMap.put(Contants.host, "<calculated when request is sent>");

            LoginGoogleRequest loginGoogleRequest = new LoginGoogleRequest(account.getIdToken());
            Call<LoginGoogleResponse> responseDTOCall = ApiClient.getUserService().loginGoogle(hashMap,loginGoogleRequest);
            responseDTOCall.enqueue(new Callback<LoginGoogleResponse>() {
                @Override
                public void onResponse(Call<LoginGoogleResponse> call, Response<LoginGoogleResponse> response) {
                    if (response.body().getStatus() == 200) {
                        String string = response.headers().value(2);
                        String[] parts = string.split(";");
                        String part1 = parts[0]; // 004
                        StoreUtil.save(LoginActivity.this, Contants.refreshToken, part1);

                        StoreUtil.save(LoginActivity.this, Contants.accessToken, response.body().getAccesstoken());
                        Intent intentslide = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentslide);
                        finish();
                    } else {
                        String message = response.body().getMsg();
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginGoogleResponse> call, Throwable t) {

                }
            });


        } catch (ApiException e) {

        }
    }
}