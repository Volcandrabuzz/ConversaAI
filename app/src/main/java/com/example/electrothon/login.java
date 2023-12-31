package com.example.electrothon;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    TextView register;
    EditText email,password;
    ProgressBar progressBar;
    Button login;
    String fullemail,fullpassword;
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=findViewById(R.id.Register_u);
        login=findViewById(R.id.cirLoginButton);
        progressBar=findViewById(R.id.bar);
        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        mAuth=FirebaseAuth.getInstance();

        register.setOnClickListener(view -> {
            Intent intent=new Intent(login.this,Register.class);
            startActivity(intent);

        });
        login.setOnClickListener(view -> {
            fullemail=email.getText().toString();
            fullpassword=password.getText().toString();

            if (isValidate()){
                Log_in();
            }
        });
    }

    private void Log_in(){
        register.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(fullemail,fullpassword)
                .addOnSuccessListener(authResult -> {
                    Intent intent=new Intent(login.this, Welcome.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(login.this,"Logged In Succesfullly",Toast.LENGTH_SHORT).show();


                }).addOnFailureListener(e -> {
                Toast.makeText(login.this,"Error - "+e.getMessage(),Toast.LENGTH_SHORT).show();
                register.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

            });
    }

    private boolean isValidate(){


        if(TextUtils.isEmpty(fullemail)){
            email.setError("Email can't be Empty !");
            return false;
        }
        if(!fullemail.matches(emailpattern)){
            email.setError("Enter a valid Email ID !");
            return false;
        }

        if(TextUtils.isEmpty(fullpassword)){
            password.setError("Password can't be Empty !");
            return false;
        }

        return true;
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(login.this,loginorsignup.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected  void onStart(){
        super.onStart();
        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentuser!=null){
            startActivity(new Intent(login.this,Welcome.class));
            finish();
        }
    }
}