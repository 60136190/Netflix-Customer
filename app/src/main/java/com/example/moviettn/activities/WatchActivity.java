package com.example.moviettn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.moviettn.R;
import com.example.moviettn.model.Film;

public class WatchActivity extends AppCompatActivity {

    private VideoView videoView;
    private Film film;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        initUi();
        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            film = (Film) bundle.get("film");
//
//            Uri uri = Uri.parse(film .getVideoFilm().getUrl());
//            videoView.setVideoURI(uri);
//            DisplayMetrics metrics = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) videoView.getLayoutParams();
//            params.width = metrics.widthPixels;
//            params.height = metrics.heightPixels;
//            params.leftMargin = 0;
//            videoView.setLayoutParams(params);
//            // creating object of
//            // media controller class
//            MediaController mediaController = new MediaController(WatchActivity.this);
//
//            // sets the anchor view
//            // anchor view for the videoView
//            mediaController.setAnchorView(videoView);
//
//            // sets the media player to the videoView
//            mediaController.setMediaPlayer(videoView);
//
//            // sets the media controller to the videoView
//            videoView.setMediaController(mediaController);
//
//            // starts the video
//            videoView.start();
//        }
    }

    private void initUi() {
        videoView = findViewById(R.id.video_film);
    }
}