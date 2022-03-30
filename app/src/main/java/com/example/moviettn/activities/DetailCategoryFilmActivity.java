package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.moviettn.R;

public class DetailCategoryFilmActivity extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category_film);

        // hide keyboard
        DetailCategoryFilmActivity.this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initUi();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // slide
        int images[] = {R.drawable.vpa, R.drawable.vpb, R.drawable.vpc,
                R.drawable.vpd, R.drawable.vpe, R.drawable.vpf};

        for (int image : images) {
            flipperImages(image);
        }
    }

    private void initUi() {
        viewFlipper = findViewById(R.id.viewflipper_slide);
        imgBack = findViewById(R.id.img_back);
    }

    public void flipperImages(int image) {
        ImageView imageView = new ImageView(DetailCategoryFilmActivity.this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3500);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(DetailCategoryFilmActivity.this, android.R.anim.slide_in_left);
    }
}