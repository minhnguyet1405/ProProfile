package com.guardianofgods.proprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LoginPinCode extends AppCompatActivity {

    TextView edtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin_code);
        edtname=findViewById(R.id.name_pincode);

        Intent intent=getIntent();
        String name=intent.getStringExtra("NAME");
        edtname.setText("Xin chào,"+name);


    }
}