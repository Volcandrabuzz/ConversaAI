package com.example.electrothon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class Welcome extends AppCompatActivity {
    ImageView img;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        img=findViewById(R.id.imageView);
        Animation animate= AnimationUtils.loadAnimation(this,R.anim.animate);
        img.setAnimation(animate);

        Button btn;
        btn= findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Button Is Clicked",Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(Welcome.this,ChatActivity.class);
                startActivity(intent);

            }
        });






    }
}