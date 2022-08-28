package com.example.proprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    ImageView back;
    EditText edtName, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back=findViewById(R.id.back);
        edtName=findViewById(R.id.email_login);
        edtPassword=findViewById(R.id.password_login);
        btnLogin=findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edtName.getText().toString();
                String password=edtPassword.getText().toString();

                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                    intent.putExtra("EMAIL",email);
                    intent.putExtra("PASS",password);
                    startActivity(intent);
                    finish();



            }
        });

    }
}