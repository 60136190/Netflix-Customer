package com.example.moviettn.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moviettn.R;
import com.example.moviettn.adapters.ListFavoritelFilmAdapter;
import com.example.moviettn.adapters.ListHistoryBillAdapter;
import com.example.moviettn.api.ApiClient;
import com.example.moviettn.model.response.HistoryBillResponse;
import com.example.moviettn.model.response.ListFavoriteFilmResponse;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryBillActivity extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView rcvHistoryBill;
    ListHistoryBillAdapter listHistoryBillAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_bill);
        initUi();

        getHistoryBill();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryBillActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvHistoryBill.setLayoutManager(linearLayoutManager);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        rcvHistoryBill = findViewById(R.id.rcv_history_bill);
    }

    private void getHistoryBill(){
        Call<HistoryBillResponse> listFavoriteFilmResponseCall = ApiClient.getFilmService().getHistoryBill(
                StoreUtil.get(HistoryBillActivity.this, Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<HistoryBillResponse>() {
            @Override
            public void onResponse(Call<HistoryBillResponse> call, Response<HistoryBillResponse> response) {
                listHistoryBillAdapter = new ListHistoryBillAdapter(HistoryBillActivity.this, response.body().getHistory());
                rcvHistoryBill.setAdapter(listHistoryBillAdapter);
            }

            @Override
            public void onFailure(Call<HistoryBillResponse> call, Throwable t) {
                Toast.makeText(HistoryBillActivity.this, "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistoryBill();
    }
}