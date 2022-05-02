package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviettn.R;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.request.FeedBack;
import com.example.moviettn.model.response.ProfileResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendFeedBackActivity extends AppCompatActivity {

    private EditText edt_Subject;
    private EditText edt_Content;
    private TextView tv_Fullname;
    private TextView tv_Email;
    private Button btn_SendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feed_back);
        getProfile();
        initUi();

        btn_SendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendFeedBack();
            }
        });
    }

    private void initUi() {
        edt_Subject = findViewById(R.id.edt_subject);
        edt_Content = findViewById(R.id.edt_content);
        tv_Email = findViewById(R.id.tv_email);
        tv_Fullname = findViewById(R.id.tv_fullname);
        btn_SendFeedback = findViewById(R.id.btn_send_feedback);
    }

    public void getProfile() {
        Call<ProfileResponse> proifileResponseCall = ApiClient.getUserService().getProfile(
                StoreUtil.get(SendFeedBackActivity.this, "Authorization"));
        proifileResponseCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    String fullname = response.body().getUser().getFullname();
                    String email = response.body().getUser().getEmail();
                    tv_Email.setText(email);
                    tv_Fullname.setText(fullname);

                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
            }
        });
    }

    public void SendFeedBack(){
        String content = edt_Content.getText().toString();
        String subject = edt_Subject.getText().toString();
        String fullname = tv_Fullname.getText().toString();
        String email = tv_Email.getText().toString();

            FeedBack feedBack = new FeedBack(fullname,email,subject,content);
            Call<FeedBack> proifileResponseCall = ApiClient.getUserService().sendFeedback(
                    StoreUtil.get(SendFeedBackActivity.this, "Authorization"),feedBack);
            proifileResponseCall.enqueue(new Callback<FeedBack>() {
                @Override
                public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {

                }

                @Override
                public void onFailure(Call<FeedBack> call, Throwable t) {
                }
            });

    }
}