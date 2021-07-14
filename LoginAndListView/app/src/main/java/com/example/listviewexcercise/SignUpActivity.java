package com.example.listviewexcercise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {


    EditText username;


    EditText password;

    EditText repassword;

    Button btnSignUp , btnSignIn;
    DBHelper myDB;


    private ImageView btnBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnBackToLogin = (ImageView) findViewById(R.id.btnBackToLogin);
        
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);



        myDB = new DBHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(SignUpActivity.this , "Fill all the fields." , Toast.LENGTH_SHORT).show();
                }
                else {
                    if(pass.equals(repass)){
                        Boolean userCheckResult = myDB.checkusername(user);
                        if(userCheckResult == false) {
                            Boolean regResult = myDB.insertData(user , pass);
                            if(regResult == true) {
                                Toast.makeText(SignUpActivity.this , "Registration Successful." , Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SignUpActivity.this , "Registration Failed." , Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(SignUpActivity.this , "User already exists.\n Please Sign In" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(SignUpActivity.this , "Password not match." , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
