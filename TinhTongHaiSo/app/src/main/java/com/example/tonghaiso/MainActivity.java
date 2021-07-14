package com.example.tonghaiso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edtSo1 , edtSo2;
    Button btnTong;
    Button btnHieu;
    Button btnThuong;
    Button btnNhanl;
    TextView txtvKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSo1 = (EditText)findViewById(R.id.editTextTextPersonName3);
        edtSo2 = (EditText)findViewById(R.id.editTextTextPersonName4);
        txtvKetQua = (TextView) findViewById(R.id.textView2);
        btnTong = (Button) findViewById((R.id.btncong));
        btnHieu = (Button) findViewById(R.id.btntru);
        btnNhanl = (Button) findViewById(R.id.btnnhan);
        btnThuong = (Button) findViewById(R.id.btnchia);


        btnHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chuoi1 = edtSo1.getText().toString();
                int so1 = Integer.parseInt(chuoi1);
                String chuoi2 = edtSo2.getText().toString();
                int so2 = Integer.parseInt(chuoi2);

                int hieu = so1 - so2;
                txtvKetQua.setText(String.valueOf(hieu) );
            }
        });


        btnTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(MainActivity.this, ResultActivity.class);
                Bundle bundle=new Bundle();
                String chuoi1 = edtSo1.getText().toString();
                int so1 = Integer.parseInt(chuoi1);
                String chuoi2 = edtSo2.getText().toString();
                int so2 = Integer.parseInt(chuoi2);
                bundle.putInt("so1", so1);
                bundle.putInt("so2", so2);
                myIntent.putExtra("MyPackage", bundle);
                startActivity(myIntent);
            }
        });

        btnNhanl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ResultActivity.class);
                startActivity(intent);
            }
        });






    }
}