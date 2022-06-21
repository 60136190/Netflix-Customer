package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.moviettn.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PayPaymentDetails extends AppCompatActivity {

    TextView tvId, tvAmount, tvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_payment_details);
        tvId = findViewById(R.id.tv_id);
        tvAmount = findViewById(R.id.tv_amount);
        tvStatus = findViewById(R.id.tv_status);

        // get intent
        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            tvId.setText(response.getString("id"));
            tvStatus.setText(response.getString("state"));
            tvAmount.setText(response.getString(String.format("$%s",paymentAmount)));

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}