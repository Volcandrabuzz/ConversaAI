package com.example.electrothon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class loginorsignup extends AppCompatActivity {

    Button login,Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginorsignup);
        login=findViewById(R.id.login);
        Register=findViewById(R.id.Register);

        login.setOnClickListener(v -> {
            Intent intent=new Intent(loginorsignup.this,login.class);
            startActivity(intent);
            finish();
        });

        Register.setOnClickListener(v -> {
            Intent intent=new Intent(loginorsignup.this,Register.class);
            startActivity(intent);
            finish();
        });


    }
}