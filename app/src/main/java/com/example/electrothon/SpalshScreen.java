package com.example.electrothon;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

public class SpalshScreen extends AppCompatActivity {
    LottieAnimationView la;
    FirebaseAuth mAuth;



        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);



            mAuth=FirebaseAuth.getInstance();

            Intent i;
            i=new Intent(SpalshScreen.this, loginorsignup.class);



            new Handler().postDelayed(() -> {
                if(mAuth.getCurrentUser()!=null){
                    startActivity(i);
                }
                else{
                    startActivity(new Intent(SpalshScreen.this,loginorsignup.class));
                }
                la.setAnimation(R.raw.first);
                la.playAnimation();
                la.setRepeatCount(ValueAnimator.INFINITE);
                finish();
            },5000);

            getSupportActionBar();
            setContentView(R.layout.activity_spalsh_screen);




        }
}