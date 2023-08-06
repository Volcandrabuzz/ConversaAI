package com.example.electrothon;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class SpalshScreen extends AppCompatActivity {
    LottieAnimationView la;



        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            Intent i;
            i=new Intent(SpalshScreen.this, loginorsignup.class);

            new Handler().postDelayed(() -> {
                startActivity(i);
                la.setAnimation(R.raw.first);
                la.playAnimation();
                la.setRepeatCount(ValueAnimator.INFINITE);
                finish();

            }, 5000);
            getSupportActionBar();
            setContentView(R.layout.activity_spalsh_screen);



        }
}