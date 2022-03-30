package com.example.moviettn.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.moviettn.R;

import java.util.ArrayList;

public class DetailVideoActivity extends AppCompatActivity implements View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnClickListener {
    RelativeLayout zoomLayout;
    ScaleGestureDetector scaleDetector;
    GestureDetectorCompat gestureDetector;
    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 5.0f;
    boolean intLeft, intRight;
    private Display display;
    private Point size;
    private Mode mode = Mode.NONE;
    private enum Mode {
        NONE,
        DRAG,
        ZOOM;
    }
    int device_width;
    private int sWidth;
    private boolean isEnable = true;
    private boolean isOpen = true;
    private float scale = 1.0f;
    private float lastScaleFactor = 0f;
    // Where the finger first  touches the screen
    private float startX = 0f;
    private float startY = 0f;
    // How much to translate the canvas
    private float dx = 0f;
    private float dy = 0f;
    private float prevDx = 0f;
    private float prevDy = 0f;


    int position = -1;
    private VideoView videoView;
    LinearLayout one, two , three , four, five, lockControls, unlockControls, rotate, audioTrack;
    ImageButton goBack, rewind, forward, playPause;
    TextView title, endTime, lockTextOne, lockTextTwo;
    SeekBar videoSeekBar;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                hideDefaultControls();
                if (scale > MIN_ZOOM) {
                    mode = Mode.DRAG;
                    startX = event.getX() - prevDx;
                    startY = event.getY() - prevDy;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                hideDefaultControls();
                isEnable = false;
                if (mode == Mode.DRAG) {
                    dx = event.getX() - startX;
                    dy = event.getY() - startY;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = Mode.ZOOM;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = Mode.DRAG;
                break;
            case MotionEvent.ACTION_UP:
                mode = Mode.NONE;
                prevDx = dx;
                prevDy = dy;
                break;
        }
        scaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        if ((mode == Mode.DRAG && scale >= MIN_ZOOM) || mode == Mode.ZOOM) {
            zoomLayout.requestDisallowInterceptTouchEvent(true);
            float maxDx = (child().getWidth() - (child().getWidth() / scale)) / 2 * scale;
            float maxDy = (child().getHeight() - (child().getHeight() / scale)) / 2 * scale;
            dx = Math.min(Math.max(dx, -maxDx), maxDx);
            dy = Math.min(Math.max(dy, -maxDy), maxDy);
            applyScaleAndTranslation();
        }
        return true;
    }

    private void applyScaleAndTranslation() {
        child().setScaleX(scale);
        child().setScaleY(scale);
        child().setTranslationX(dx);
        child().setTranslationY(dy);
    }

    private View child() {
        return zoomLayout(0);
    }

    private View zoomLayout(int i) {
        return videoView;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = scaleDetector.getScaleFactor();
        if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
            scale *= scaleFactor;
            scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
            lastScaleFactor = scaleFactor;
        } else {
            lastScaleFactor = 0;
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) { }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video);
        /*Assigning Variables*/
        videoView = findViewById(R.id.video_film);
        one = findViewById(R.id.videoView_one_layout);
        two = findViewById(R.id.videoView_two_layout);
        three = findViewById(R.id.videoView_three_layout);
        four = findViewById(R.id.videoView_four_layout);
        goBack = findViewById(R.id.videoView_go_back);
        title = findViewById(R.id.videoView_title);
        rewind = findViewById(R.id.videoView_rewind);
        playPause = findViewById(R.id.videoView_play_pause_btn);
        forward = findViewById(R.id.videoView_forward);
        endTime = findViewById(R.id.videoView_endtime);
        videoSeekBar = findViewById(R.id.videoView_seekbar);
        lockControls = findViewById(R.id.videoView_lock_screen);
        five = findViewById(R.id.video_five_layout);
        unlockControls = findViewById(R.id.video_five_child_layout);
        lockTextOne = findViewById(R.id.videoView_lock_text);
        lockTextTwo = findViewById(R.id.videoView_lock_text_two);
        rotate = findViewById(R.id.videoView_rotation);
        audioTrack = findViewById(R.id.videoView_track);

        /*Adding onClickListener*/
        goBack.setOnClickListener(this);
        rewind.setOnClickListener(this);
        playPause.setOnClickListener(this);
        forward.setOnClickListener(this);
        lockControls.setOnClickListener(this);
        five.setOnClickListener(this);
        unlockControls.setOnClickListener(this);
        rotate.setOnClickListener(this);
        /*getting path and preparing for play video*/

        /*
          Intent iin = getIntent();
        Bundle b = iin.getExtras();
        String video = (String) b.get("video");
        videoFilm.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse(video);
        videoFilm.setVideoURI(uri);
        MediaController mediaController = new MediaController(DetailVideoActivity.this);
        mediaController.setAnchorView(videoFilm);
        mediaController.setMediaPlayer(videoFilm);
        videoFilm.setMediaController(mediaController);
        videoFilm.start();
            */


        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        String path = (String) b.get("video");

        if (path!=null){
            videoView.setVideoPath(path);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoSeekBar.setMax(videoView.getDuration());
                    videoView.setVisibility(View.VISIBLE);
                    videoView.start();
                    audioTrack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkMultiAudioTrack(mp);
                        }
                    });
                }
            });
        }else {
            Toast.makeText(this, "path didn't exits", Toast.LENGTH_SHORT).show();
        }
        /*zoom in,out and double tap to go forward,backward
         *  and single tap to hide and show controls */
        zoomLayout = findViewById(R.id.zoom_layout);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        sWidth = size.x;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        device_width = displayMetrics.widthPixels;
        zoomLayout.setOnTouchListener(this);
        scaleDetector = new ScaleGestureDetector(getApplicationContext(), this);
        gestureDetector = new GestureDetectorCompat(getApplicationContext(), new GestureDetector());


        setHandler();
        initalizeSeekBars();
    }

    private void checkMultiAudioTrack(MediaPlayer mediaPlayer) {
        MediaPlayer.TrackInfo[] trackInfos = mediaPlayer.getTrackInfo();

        ArrayList<Integer> audioTracksIndex = new ArrayList<>();

        for (int i = 0; i < trackInfos.length; i++) {
            if (trackInfos[i].getTrackType() == MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_AUDIO) {
                audioTracksIndex.add(i);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailVideoActivity.this);
        builder.setTitle("Select Audio Track");

        String[] values = new String[audioTracksIndex.size()];
        for (int i = 0; i < audioTracksIndex.size(); i++) {
            values[i] = "Track " + i;
        }
        /*
         * SingleChoice means RadioGroup
         * */
        builder.setSingleChoiceItems(values, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.selectTrack(which);
                Toast.makeText(DetailVideoActivity.this, "Track " + which + " Selected", Toast.LENGTH_SHORT).show();
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mediaPlayer.getSelectedTrack(which);
                }
                mediaPlayer.start();
                Toast.makeText(DetailVideoActivity.this, "we are working on that :)", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void initalizeSeekBars(){
        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (videoSeekBar.getId() == R.id.videoView_seekbar) {
                    if (fromUser) {
                        videoView.seekTo(progress);
                        videoView.start();
                        int currentPosition = videoView.getCurrentPosition();
                        endTime.setText("" + convertIntoTime(videoView.getDuration() - currentPosition));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String convertIntoTime(int ms){
        String time;
        int x, seconds, minutes, hours;
        x = ms / 1000;
        seconds = x % 60;
        x /= 60;
        minutes = x % 60;
        x /= 60;
        hours = x % 24;
        if (hours != 0)
            time = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        else time = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        return time;
    }

    private void setHandler(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (videoView.getDuration()>0){
                    int currentPosition = videoView.getCurrentPosition();
                    videoSeekBar.setProgress(currentPosition);
                    endTime.setText(""+convertIntoTime(videoView.getDuration()-currentPosition));
                }
                handler.postDelayed(this,0);
            }
        };
        handler.postDelayed(runnable,500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.videoView_rotation:
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT){
                    //set in landscape
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
                    //set in portrait
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;

            case R.id.videoView_lock_screen:
                hideDefaultControls();
                five.setVisibility(View.VISIBLE);
                break;

            case R.id.video_five_layout:
                if (isOpen){
                    unlockControls.setVisibility(View.INVISIBLE);
                    lockTextOne.setVisibility(View.INVISIBLE);
                    lockTextTwo.setVisibility(View.INVISIBLE);
                    isOpen = false;
                }else {
                    unlockControls.setVisibility(View.VISIBLE);
                    lockTextOne.setVisibility(View.VISIBLE);
                    lockTextTwo.setVisibility(View.VISIBLE);
                    isOpen = true;
                }
                break;

            case R.id.video_five_child_layout:
                five.setVisibility(View.GONE);
                showDefaultControls();
                break;

            case R.id.videoView_go_back:
                onBackPressed();
                break;

            case R.id.videoView_rewind:
                //1000 = 1sec
                videoView.seekTo(videoView.getCurrentPosition() -10000);
                break;

            case R.id.videoView_forward:
                //1000 = 1sec
                videoView.seekTo(videoView.getCurrentPosition() +10000);
                break;

            case R.id.videoView_play_pause_btn:
                if (videoView.isPlaying()){
                    videoView.pause();
                    playPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                }else {
                    videoView.start();
                    playPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                }
                break;
        }
    }

    private class GestureDetector extends android.view.GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (isEnable){
                hideDefaultControls();
                isEnable = false;
            }else {
                showDefaultControls();
                isEnable = true;
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            if (event.getX() < (sWidth / 2)) {
                intLeft = true;
                intRight = false;
                videoView.seekTo(videoView.getCurrentPosition() - 20000);
                Toast.makeText(DetailVideoActivity.this, "-20sec", Toast.LENGTH_SHORT).show();
            } else if (event.getX() > (sWidth / 2)) {
                intLeft = false;
                intRight = true;
                videoView.seekTo(videoView.getCurrentPosition() + 20000);
                Toast.makeText(DetailVideoActivity.this, "+20sec", Toast.LENGTH_SHORT).show();
            }
            return super.onDoubleTap(event);
        }


    }

    private void hideDefaultControls(){
        one.setVisibility(View.GONE);
        two.setVisibility(View.GONE);
        three.setVisibility(View.GONE);
        four.setVisibility(View.GONE);
        //Todo this function will hide status and navigation when we click on screen
        final Window window = this.getWindow();
        if (window == null){
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final View decorView = window.getDecorView();
        if (decorView!=null){
            int uiOption = decorView.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= 14) {
                uiOption |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility(uiOption);
        }
    }

    private void showDefaultControls(){
        one.setVisibility(View.VISIBLE);
        two.setVisibility(View.VISIBLE);
        three.setVisibility(View.VISIBLE);
        four.setVisibility(View.VISIBLE);
        //todo this function will show status and navigation when we click on screen
        final Window window = this.getWindow();
        if (window == null){
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        final View decorView = window.getDecorView();
        if (decorView!=null){
            int uiOption = decorView.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= 14) {
                uiOption &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                uiOption &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                uiOption &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility(uiOption);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}