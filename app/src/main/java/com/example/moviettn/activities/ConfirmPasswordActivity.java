package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviettn.R;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.UpdateStateUserRequest;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.model.response.UpdateStateUserResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPasswordActivity extends AppCompatActivity {

    EditText edtEmail;
    Button btnCheck;
    UpdateStateUserRequest updateStateUserRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);
        initUi();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                        StoreUtil.get(ConfirmPasswordActivity.this, "Authorization"));
                proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        if (response.isSuccessful()) {
                            String sdt = edtEmail.getText().toString();
                            if (sdt.equals(response.body().getUser().getEmail())){
                                UpdateStateUsertoAdultorKid("1");
                                finish();
                            }else{
                                Toast.makeText(ConfirmPasswordActivity.this, "Phone number is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        Toast.makeText(ConfirmPasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initUi() {
        edtEmail = findViewById(R.id.edt_email);
        btnCheck = findViewById(R.id.btn_check);
    }

    private void UpdateStateUsertoAdultorKid(String number) {
        updateStateUserRequest = new UpdateStateUserRequest(number);
        Call<UpdateStateUserResponse> updateStateAdult = ApiClient.getUserService().updateStateUser(
                StoreUtil.get(ConfirmPasswordActivity.this, Contants.accessToken), updateStateUserRequest);
        updateStateAdult.enqueue(new Callback<UpdateStateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateStateUserResponse> call, Response<UpdateStateUserResponse> response) {

            }

            @Override
            public void onFailure(Call<UpdateStateUserResponse> call, Throwable t) {

            }
        });
    }
}