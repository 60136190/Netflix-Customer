//package com.example.moviettn.activities;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.moviettn.R;
//import com.example.moviettn.api.ApiClient;
//import com.example.moviettn.config.Config;
//import com.example.moviettn.model.request.CreateBillRequest;
//import com.example.moviettn.model.response.DetailFilmResponse;
//import com.example.moviettn.model.response.ResponseDTO;
//import com.example.moviettn.utils.Contants;
//import com.example.moviettn.utils.StoreUtil;
//import com.paypal.android.sdk.payments.PayPalConfiguration;
//import com.paypal.android.sdk.payments.PayPalPayment;
//import com.paypal.android.sdk.payments.PayPalService;
//import com.paypal.android.sdk.payments.PaymentActivity;
//import com.paypal.android.sdk.payments.PaymentConfirmation;
//import com.squareup.picasso.Picasso;
//
//import org.json.JSONException;
//
//import java.math.BigDecimal;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class CreateBillActivity extends AppCompatActivity {
//
//    public static final int PAYPAL_REQUEST_CODE = 7171;
//
//    private static PayPalConfiguration config = new PayPalConfiguration()
//            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
//            .clientId(Config.PAYPAL_CLIENT_ID);
//
//    ImageView imgBack, imgFilm, imgModeOfPayment;
//    TextView tvTitleFilm, tvPrice, tvModeOfPayment;
//    Button btnBuy;
//
//    @Override
//    protected void onDestroy() {
//        stopService(new Intent(this, PayPalService.class));
//        super.onDestroy();
//    }
//
//    String amount = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bill);
//        initUi();
//        Intent iin = getIntent();
//        Bundle b = iin.getExtras();
//        String idFilm = StoreUtil.get(CreateBillActivity.this, Contants.idFilm);
//        String price = StoreUtil.get(CreateBillActivity.this, Contants.price);
//        getInfoFilm(idFilm);
//
//        if (b != null) {
//            String name = (String) b.get("name_mode");
//            String idMode = (String) b.get("id_mode");
//            String url = (String) b.get("url_mode");
//            Picasso.with(CreateBillActivity.this).load(url).into(imgModeOfPayment);
//            tvModeOfPayment.setText(name);
//
//            Log.i("xemthusao", price);
//            // Start paypal service
//            Intent intent = new Intent(this, PayPalService.class);
//            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//            startService(intent);
//
//            imgBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onBackPressed();
//                }
//            });
//
//            btnBuy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                processPayment();
//                    createBill(idFilm, price,idMode,"91K02466UY433503E");
//
//                }
//            });
//        }
//    }
//
//    private void initUi() {
//
//        imgBack = findViewById(R.id.img_back);
//        tvTitleFilm = findViewById(R.id.tv_title_film);
//        tvPrice = findViewById(R.id.tv_price);
//        tvModeOfPayment = findViewById(R.id.tv_title_mode_of_payment);
//        btnBuy = findViewById(R.id.btn_buy);
//        imgFilm = findViewById(R.id.img_film);
//        imgModeOfPayment = findViewById(R.id.img_mode_of_payment);
//    }
//
//    private void processPayment() {
//        amount = tvPrice.getText().toString();
//        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount))
//                , "USD"
//                , "Pay for us"
//                , PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PAYPAL_REQUEST_CODE) {
//            if (requestCode == RESULT_OK) {
//                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//                if (confirmation != null) {
//                    try {
//                        String paymentDetails = confirmation.toJSONObject().toString(4);
//                        startActivity(new Intent(this, PayPaymentDetails.class)
//                                .putExtra("PaymentDetails", paymentDetails)
//                                .putExtra("PaymentAmount", amount));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED)
//                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
//        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
//            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
//    }
//
//    private void getInfoFilm(String idFilm) {
//        Call<DetailFilmResponse> detailFilmResponseCall = ApiClient.getFilmService().detailFilm(
//                StoreUtil.get(CreateBillActivity.this, Contants.accessToken), idFilm);
//        detailFilmResponseCall.enqueue(new Callback<DetailFilmResponse>() {
//            @Override
//            public void onResponse(Call<DetailFilmResponse> call, Response<DetailFilmResponse> response) {
//                if (response.isSuccessful()) {
//                    tvTitleFilm.setText(response.body().getData().get(0).getTitle());
//                    tvPrice.setText(String.valueOf(response.body().getData().get(0).getPrice()));
//                    Picasso.with(CreateBillActivity.this)
//                            .load(String.valueOf(response.body().getData().get(0).getImageFilm().getUrl()))
//                            .into(imgFilm);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DetailFilmResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void createBill(String idFilm,String price, String mode, String idPayment){
//        CreateBillRequest createBillRequest = new CreateBillRequest(mode,idPayment,idFilm,price);
//        Call<ResponseDTO> detailFilmResponseCall = ApiClient.getFilmService().createBill(
//                StoreUtil.get(CreateBillActivity.this, Contants.accessToken), createBillRequest);
//        detailFilmResponseCall.enqueue(new Callback<ResponseDTO>() {
//            @Override
//            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(CreateBillActivity.this, "Buy successfully", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(CreateBillActivity.this,HistoryBillActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDTO> call, Throwable t) {
//
//            }
//        });
//    }
//}