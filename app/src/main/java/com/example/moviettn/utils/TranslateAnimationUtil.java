package com.example.moviettn.utils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.example.moviettn.R;

public class TranslateAnimationUtil implements View.OnTouchListener {
    private GestureDetector mGestureDetector;

    public TranslateAnimationUtil(Context context, View view){
        mGestureDetector = new GestureDetector(context, new SimpleGestureDetector(view));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    public class SimpleGestureDetector extends GestureDetector.SimpleOnGestureListener{
        private View mViewAnimation;
        private boolean isFinishAnimation = true;

        public SimpleGestureDetector(View mViewAnimation){
            this.mViewAnimation = mViewAnimation;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (distanceY > 0){
                hiddenView();
            }
            else{
                showView();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        private void hiddenView(){
            if (mViewAnimation == null || mViewAnimation.getVisibility() ==View.GONE){
                return;
            }

            Animation animationDown = AnimationUtils.loadAnimation(mViewAnimation.getContext(), R.anim.bottom_down);
            animationDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mViewAnimation.setVisibility(View.VISIBLE);
                    isFinishAnimation = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mViewAnimation.setVisibility(View.GONE);
                    isFinishAnimation = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            if (isFinishAnimation){
                mViewAnimation.startAnimation(animationDown);
            }
        }

        private void showView(){
            if (mViewAnimation == null || mViewAnimation.getVisibility() ==View.VISIBLE){
                return;
            }

            Animation animationUp = AnimationUtils.loadAnimation(mViewAnimation.getContext(), R.anim.bottom_up);
            animationUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mViewAnimation.setVisibility(View.VISIBLE);
                    isFinishAnimation = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isFinishAnimation = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            if (isFinishAnimation){
                mViewAnimation.startAnimation(animationUp);
            }
        }
    }
}
