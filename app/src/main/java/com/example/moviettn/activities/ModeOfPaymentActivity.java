package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moviettn.R;
import com.example.moviettn.adapters.ListModeOfPaymentAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.ModeOfPaymentResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeOfPaymentActivity extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView rcvModeofPayment;
    ListModeOfPaymentAdapter listModeOfPaymentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_of_payment);
        initUi();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getAllModeOfPayment();
        LinearLayoutManager linearLayoutManagera = new LinearLayoutManager(ModeOfPaymentActivity.this);
        linearLayoutManagera.setOrientation(LinearLayoutManager.VERTICAL);
        rcvModeofPayment.setLayoutManager(linearLayoutManagera);
    }

    private void getAllModeOfPayment() {
        Call<ModeOfPaymentResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getAllModeOfPayment(
                StoreUtil.get(ModeOfPaymentActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ModeOfPaymentResponse>() {
            @Override
            public void onResponse(Call<ModeOfPaymentResponse> call, Response<ModeOfPaymentResponse> response) {
                listModeOfPaymentAdapter = new ListModeOfPaymentAdapter(ModeOfPaymentActivity.this,response.body().getData());
                rcvModeofPayment.setAdapter(listModeOfPaymentAdapter);

            }

            @Override
            public void onFailure(Call<ModeOfPaymentResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        rcvModeofPayment = findViewById(R.id.rcv_mode_of_payment);
    }


}