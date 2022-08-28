package com.example.proprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {
    ImageView imgBack;
    EditText edtName, edtPass, edtRepass, edtPhone, edtEmail, edtOTP;
    Button btnSend, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imgBack=findViewById(R.id.back);
        edtName=findViewById(R.id.edt_account);
        edtPass=findViewById(R.id.edt_password);
        edtRepass=findViewById(R.id.edt_repassword);
        edtPhone=findViewById(R.id.edt_phone);
        edtEmail=findViewById(R.id.edt_email);
        edtOTP=findViewById(R.id.edt_otp);
        btnSend=findViewById(R.id.btn_send);
        btnSignup=findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Account, Pass, Repass, Phone, Email, OTP;
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra("ACCOUNT", edtName.getText().toString());
                intent.putExtra("PASS",edtPass.getText().toString());
                intent.putExtra("REPASS",edtRepass.getText().toString());
                intent.putExtra("PHONE", edtPhone.getText().toString());
                intent.putExtra("EMAIL",edtEmail.getText().toString());
                intent.putExtra("OTP", edtOTP.getText().toString());
                startActivity(intent);
                finish();
            }
        });




    }
}