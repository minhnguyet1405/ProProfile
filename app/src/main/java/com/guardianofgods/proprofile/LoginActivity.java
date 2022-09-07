package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    ImageView back,ic_facebook,ic_instagram;
    TextInputLayout til_Name,til_pass;
    EditText edtPhone, edtPassword;
    Button btnLogin;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String phone;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back=findViewById(R.id.back);
        edtPhone=findViewById(R.id.phone_login);
        edtPassword=findViewById(R.id.password_login);
        btnLogin=findViewById(R.id.btn_login);
        ic_facebook=findViewById(R.id.icon_facebook);
        ic_instagram=findViewById(R.id.icon_instagram);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone=edtPhone.getText().toString();
                password=edtPassword.getText().toString();

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference("Users");
                databaseReference.child(phone).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {

                            Login(phone,password,task.getResult().child("phone").getValue().toString(), task.getResult().child("password").getValue().toString());
                        }
                        else {
                            Toast.makeText(LoginActivity.this,String.valueOf(task.getResult().getValue()),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


        ic_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebookIntent("100014907154914");
            }
        });

        ic_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInstagramIntent();
            }
        });
    }

    private void Login(String phone, String password, String phoneFireBase, String passwordFireBase){
        Toast.makeText(LoginActivity.this,phoneFireBase,Toast.LENGTH_SHORT).show();
        Toast.makeText(LoginActivity.this,phone,Toast.LENGTH_SHORT).show();
        if(phoneFireBase.equals(phone) && passwordFireBase.equals(password)){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("PHONE",phone);
            intent.putExtra("PASS",password);
            startActivity(intent);
            finish();
            Toast.makeText(LoginActivity.this,"success",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_SHORT).show();

        }
    }

    private void openFacebookIntent(String id){
        try {
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+id));
            startActivity(intent);
        } catch (Exception e) {
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" +id));
            startActivity(intent);
        }
    }

    private void openInstagramIntent(){
        Uri uri = Uri.parse("https://www.instagram.com/mnguyet.1405/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/mnguyet.1405/")));
        }
    }

}