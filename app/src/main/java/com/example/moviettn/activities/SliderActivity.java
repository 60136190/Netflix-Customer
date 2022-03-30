package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moviettn.R;
import com.example.moviettn.adapters.SliderAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SliderActivity extends AppCompatActivity {
    private ViewPager mSlideviewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private Button mNext;
    private int mCurrentPage;
    private SliderAdapter sliderAdapter;

    private TextView tvSupport;
    private TextView tvPrivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        initUi();
        sliderAdapter = new SliderAdapter(this);
        mSlideviewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSlideviewPager.addOnPageChangeListener(viewListener);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(SliderActivity.this, LoginActivity.class);
                startActivity(intent4);
                finish();
            }
        });

            tvSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewdialog = getLayoutInflater().inflate(R.layout.bottom_sheet_help, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SliderActivity.this);
                bottomSheetDialog.setContentView(viewdialog);
                bottomSheetDialog.show();
                LinearLayout lnForgotPassword = viewdialog.findViewById(R.id.ln_forgot_password);
                lnForgotPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SliderActivity.this,ForgotPasswordActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        tvPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewdialog = getLayoutInflater().inflate(R.layout.bottom_sheet_help, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SliderActivity.this);
                bottomSheetDialog.setContentView(viewdialog);
                bottomSheetDialog.show();


            }
        });


    }


    public void initUi() {
        mSlideviewPager = (ViewPager) findViewById(R.id.slideviewpager);
        mDotLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mNext = findViewById(R.id.btn_login);
        tvSupport = findViewById(R.id.tv_support);
        tvPrivate = findViewById(R.id.tv_private);
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[4];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.gray_dot));
            mDotLayout.addView(mDots[i]);

        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.red));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}