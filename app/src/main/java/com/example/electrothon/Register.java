package com.example.electrothon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    TextView login;
    EditText name,email,mobile,password;
    ProgressBar progressBar;
    Button register;
    String fullname,fullemail,fullmobile,fullpassword;
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login=findViewById(R.id.txtlogin);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobile);
        password=findViewById(R.id.password);
        register=findViewById(R.id.Register);
        progressBar=findViewById(R.id.bar);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname=name.getText().toString();
                fullemail=email.getText().toString();
                fullmobile=mobile.getText().toString();
                fullpassword=password.getText().toString();

                if (isValidate()){
                    Register();
                }
            }
        });



    }

    private void Register(){

    }
    private boolean isValidate(){
        if(TextUtils.isEmpty(fullname)){
            name.setError("Full Name can't be Empty !");
            return false;
        }

        if(TextUtils.isEmpty(fullemail)){
            email.setError("Email can't be Empty !");
            return false;
        }
        if(fullemail.matches(emailpattern)){
            email.setError("Enter a valid Email ID !");
            return false;
        }

        if(TextUtils.isEmpty(fullmobile)){
            mobile.setError("Mobile Number can't be Empty !");
            return false;
        }

        if(TextUtils.isEmpty(fullpassword)){
            password.setError("Password can't be Empty !");
            return false;
        }

        return false;
    }
}