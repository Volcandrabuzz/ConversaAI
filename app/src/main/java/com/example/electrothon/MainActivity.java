package com.example.electrothon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView bot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView bot;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bot= findViewById(R.id.imageView3);


        bot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Button Is Clicked",Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(MainActivity.this,Welcome.class);
                startActivity(intent);

            }
        });
    }
}