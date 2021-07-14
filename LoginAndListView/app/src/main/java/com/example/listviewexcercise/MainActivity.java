package com.example.listviewexcercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView btnSignUp;

    EditText username,password;
    Button btnLogin;

    DBHelper myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = (TextView) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , SignUpActivity.class);
                startActivity(intent);

            }
        });

        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        myDB = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this , "Fill all the fields." , Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean result = myDB.checkusernamePassword(user , pass);
                    if(result == true ) {
                        Intent intent = new Intent(getApplicationContext() , ListViewActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this , "Wrong Username or Password.." , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }

}