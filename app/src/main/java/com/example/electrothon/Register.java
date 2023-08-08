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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView login;
    EditText name,email,mobile,password;
    ProgressBar progressBar;
    Button register;
    String fullname,fullemail,fullmobile,fullpassword;
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login=findViewById(R.id.txtlogin);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobile);
        password=findViewById(R.id.password);
        register=findViewById(R.id.btnregister);
        progressBar=findViewById(R.id.bar);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        login.setOnClickListener(view -> {

            Intent intent=new Intent(Register.this, login.class);
            startActivity(intent);
            Toast.makeText(Register.this,"Registered Succesfullly",Toast.LENGTH_SHORT).show();

        });

        register.setOnClickListener(view -> {
            fullname=name.getText().toString();
            fullemail=email.getText().toString();
            fullmobile=mobile.getText().toString();
            fullpassword=password.getText().toString();

            if (isValidate()){
                Register_up();
            }
        });



    }

    private void Register_up(){
        register.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(fullemail,fullpassword).addOnSuccessListener(authResult -> {
            Map<String,Object> user=new HashMap<>();
            user.put("Full Name",fullname);
            user.put("Email",fullemail);
            user.put("Mobile",fullmobile);
            user.put("Password",fullpassword);

            db.collection("Users").document(fullemail).set(user).addOnSuccessListener(unused -> {
                Intent intent=new Intent(Register.this, Welcome.class);
                startActivity(intent);
            }).addOnFailureListener(e -> {
                Toast.makeText(Register.this,"Error - "+e.getMessage(),Toast.LENGTH_SHORT).show();
                register.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

            });


        }).addOnFailureListener(e -> {
            Toast.makeText(Register.this,"Error - "+e.getMessage(),Toast.LENGTH_SHORT).show();
            register.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        });

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
        if(!fullemail.matches(emailpattern)){
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

        return true;
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(Register.this,loginorsignup.class);
        startActivity(intent);
        finish();
    }
}