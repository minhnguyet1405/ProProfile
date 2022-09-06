package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    ImageView back;
    TextInputLayout til_Name,til_pass;
    EditText edtName, edtPassword;
    Button btnLogin;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String email;
    String password;
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
                email=edtName.getText().toString();
                password=edtPassword.getText().toString();

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference("Users");
                databaseReference.child("0123456789").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {

                            Login(email,password,task.getResult().child("email").getValue().toString(),task.getResult().child("password").getValue().toString());
                        }
                        else {
                            Toast.makeText(LoginActivity.this,String.valueOf(task.getResult().getValue()),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    private void Login(String email, String password, String emailFireBase, String passwordFireBase){
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("EMAIL",email);
        intent.putExtra("PASS",password);
        startActivity(intent);
        finish();
        return;
//        if(emailFireBase.equals(email) && passwordFireBase.equals(password)){
//
//
//            Toast.makeText(LoginActivity.this,"success",Toast.LENGTH_SHORT).show();
//
//        }else {
//            Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_SHORT).show();
//
//        }
    }
}