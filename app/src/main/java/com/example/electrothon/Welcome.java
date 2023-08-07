package com.example.electrothon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;


public class Welcome extends AppCompatActivity {
    LottieAnimationView lottie;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        lottie=findViewById(R.id.anim1);
        imageView=findViewById(R.id.imageView);
        Animation animate= AnimationUtils.loadAnimation(this,R.anim.animate);
        imageView.setAnimation(animate);

        lottie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(Welcome.this,"Chatting Page",Toast.LENGTH_SHORT).show();

            }
        });






    }
}