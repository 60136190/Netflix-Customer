package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviettn.R;
import com.example.moviettn.utils.Contants;
import com.example.moviettn.utils.StoreUtil;
import com.github.ybq.android.spinkit.style.Circle;
import com.squareup.picasso.Picasso;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestCreateBillActivity extends AppCompatActivity {

    Button btnBuy;
    String SECRET_KEY="sk_test_51LCOV4GD1jbEqbehPnkrPR5geDTgl2Va38zwbLfCcpi2TCk2hwPrzVGKzIKtpjRkOagDXObYKS6htloizsrJAcZl00Dcqx6o64";
    String PUBLIC_KEY="pk_test_51LCOV4GD1jbEqbehPrgJfGaxULqbzr6N2xVv5s9W2VhufAsgHmGJectOtfsYq1aScsAEw46RQDkvN71UpKEuJNBX00G6eeCamu";
    PaymentSheet paymentSheet;

    ImageView imgBack, imgFilm, imgModeOfPayment;
    TextView tvTitleFilm, tvPrice, tvModeOfPayment;
    ProgressBar progressBar;
    String customerID;
    String EphericaKey;
    String ClientSecret;
    public static String idPayment;
    public static String idModeOfPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_create_bill);
        initUi();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        String name = (String) b.get("name_mode");
        String url = (String) b.get("url_mode");
        String idMode = (String) b.get("id_mode");
        idModeOfPayment = idMode;

        String price = StoreUtil.get(TestCreateBillActivity.this,Contants.price);
        String titleFilm = StoreUtil.get(TestCreateBillActivity.this,Contants.titleFilm);
        String urlFilm = StoreUtil.get(TestCreateBillActivity.this,Contants.urlFilm);

        tvPrice.setText(String.valueOf(price+" $"));
        tvModeOfPayment.setText(String.valueOf(name));
        Picasso.with(TestCreateBillActivity.this).load(url).into(imgModeOfPayment);

        tvTitleFilm.setText(String.valueOf(titleFilm));
        Picasso.with(TestCreateBillActivity.this).load(urlFilm).into(imgFilm);

        PaymentConfiguration.init(this,PUBLIC_KEY);

        paymentSheet = new PaymentSheet(this,paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFlow();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            customerID = object.getString("id");
//                            Toast.makeText(TestCreateBillActivity.this, customerID, Toast.LENGTH_SHORT).show();
                            getEphericalKey(customerID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestCreateBillActivity.this);
        requestQueue.add(stringRequest);
    }


    private void initUi() {
        imgBack = findViewById(R.id.img_back);
        tvTitleFilm = findViewById(R.id.tv_title_film);
        tvPrice = findViewById(R.id.tv_price);
        tvModeOfPayment = findViewById(R.id.tv_title_mode_of_payment);
        btnBuy = findViewById(R.id.btn_buy);
        imgFilm = findViewById(R.id.img_film);
        imgModeOfPayment = findViewById(R.id.img_mode_of_payment);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
            createBill();
        }
    }

    private void createBill() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "https://thainammovie.herokuapp.com/api/bill/Paypal/create",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String msg = object.getString("msg");
                                Toast.makeText(TestCreateBillActivity.this, msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TestCreateBillActivity.this,HistoryBillActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("Authorization",StoreUtil.get(TestCreateBillActivity.this
                            ,Contants.accessToken));
                    return header;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String price = StoreUtil.get(TestCreateBillActivity.this, Contants.price);
                    String idFilm = StoreUtil.get(TestCreateBillActivity.this, Contants.idFilm);
                    Map<String, String> params = new HashMap<>();
                    params.put("mode_of_payment",idModeOfPayment);
                    params.put("id_payment",idPayment);
                    params.put("filmId",idFilm);
                    params.put("price",price);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(TestCreateBillActivity.this);
            requestQueue.add(stringRequest);
    }

    private void getEphericalKey(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            EphericaKey = object.getString("id");
//                            Toast.makeText(TestCreateBillActivity.this, EphericaKey, Toast.LENGTH_SHORT).show();

                            getClientSecret(customerID, EphericaKey);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Stripe-Version","2020-08-27");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestCreateBillActivity.this);
        requestQueue.add(stringRequest);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getClientSecret(String customerID, String ephericaKey) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret = object.getString("client_secret");
                            String idpayment = object.getString("id");
                            idPayment = idpayment;
//                            Toast.makeText(TestCreateBillActivity.this, idPayment, Toast.LENGTH_SHORT).show();

                            Circle foldingCube = new Circle();
                            progressBar.setIndeterminateDrawable(foldingCube);
                            progressBar.setVisibility(View.VISIBLE);
                            CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
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
                                    btnBuy.setVisibility(View.VISIBLE);
                                }

                            };
                            countDownTimer.start();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String price = StoreUtil.get(TestCreateBillActivity.this, Contants.price);
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);
                params.put("amount",price+"00");
                params.put("currency","usd");
                params.put("automatic_payment_methods[enabled]","true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestCreateBillActivity.this);
        requestQueue.add(stringRequest);
    }

    private void PaymentFlow() {
        paymentSheet.presentWithPaymentIntent(
                ClientSecret,new PaymentSheet.Configuration("Thainam Company"
                        , new PaymentSheet.CustomerConfiguration(
                        customerID,
                        EphericaKey
                ))
        );
    }

}