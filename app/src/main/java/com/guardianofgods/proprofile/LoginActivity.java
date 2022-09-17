package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.annotation.Nullable;

public class LoginActivity extends AppCompatActivity {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    ImageView back;
    TextView tvSignup, tvForgotPass;
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
        tvSignup=findViewById(R.id.signup);
        tvForgotPass=findViewById(R.id.forgot_password);

        try {
            String decrypt= AESCrypt.decrypt("gDzNC/97HsLYVxeya6hRvQ==");
            Toast.makeText(LoginActivity.this,decrypt,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone=edtPhone.getText().toString();
                password=edtPassword.getText().toString();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference("Users");


                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(LoginActivity.this,"Nhập số điện thoại",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Nhập mật khẩu",Toast.LENGTH_SHORT).show();
                }else {
                    databaseReference.child(phone).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            Toast.makeText(LoginActivity.this,task.getResult().toString(),Toast.LENGTH_SHORT).show();
                            if (task.isSuccessful()) {
                                if(task.getResult().getValue()!=null) {
                                    if(task.getResult().child("pincode").getValue().toString().equals("null")){
                                        Intent intent=new Intent(LoginActivity.this,OTPactivity.class);
                                        intent.putExtra("PHONE",phone);
                                        startActivity(intent);
                                    }else{
                                        Login(phone, password, task.getResult().child("phone").getValue().toString(), task.getResult().child("password").getValue().toString());
                                    }



                                }else{
                                    Toast.makeText(LoginActivity.this,"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this,"Có lỗi xảy ra",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

    }

    private void Login(String phone, String password, String phoneFireBase, String passwordFireBase){

        try {
            if( phoneFireBase.equals(phone) && AESCrypt.decrypt(passwordFireBase).equals(password)){
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("PHONE",phone);
                intent.putExtra("PASS",password);
                startActivity(intent);
                finish();
                Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();


            }else {
                Toast.makeText(LoginActivity.this,"Sai số điện thoại hoặc mật khẩu",Toast.LENGTH_SHORT).show();

            }
        }catch (Exception ex){
            Toast.makeText(LoginActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();

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