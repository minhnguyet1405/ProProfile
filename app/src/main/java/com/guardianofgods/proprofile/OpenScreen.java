package com.guardianofgods.proprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpenScreen extends AppCompatActivity {
    Button btnlogin, btnsignup;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);

        btnlogin=findViewById(R.id.button_login);
        btnsignup=findViewById(R.id.button_signup);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(OpenScreen.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);


            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(OpenScreen.this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);


            }
        });

    }

}