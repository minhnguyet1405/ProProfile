package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    static boolean isValidate=true;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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

        btnSignup=findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseDatabase =FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference("Users");


                String username=edtName.getText().toString();
                String password=edtPass.getText().toString();
                String phone=edtPhone.getText().toString();
                String email=edtEmail.getText().toString();


//

                if(username==null){
                    Toast.makeText(RegisterActivity.this,
                            "Tên không hợp lệ", Toast.LENGTH_SHORT).show();
                    isValidate=false;

                }
                if(password.length() < 6 ){
                    Toast.makeText(RegisterActivity.this,
                            "Password không hợp lệ", Toast.LENGTH_SHORT).show();
                    isValidate=false;
                }

                if(!edtRepass.getText().toString().equals(password)){



                    Toast.makeText(RegisterActivity.this,
                            "Password không trùng" + edtRepass.getText() + " " + edtPass.getText(), Toast.LENGTH_SHORT).show();
                    isValidate=false;
                }
                if(!PhoneNumberUtils.isGlobalPhoneNumber(phone)){
                    Toast.makeText(RegisterActivity.this,
                            "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    isValidate=false;
                }
                if(!Pattern.compile("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$").matcher(email).matches()){
                    Toast.makeText(RegisterActivity.this,
                            "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    isValidate=false;
                }


                databaseReference.child(phone).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().getValue()!=null){
                                Toast.makeText(RegisterActivity.this,
                                        "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();
                                isValidate=false;
                            }else {
                                if(isValidate){
                                    try {
                                        User user=new User(username,AESCrypt.encrypt(password),phone,email);
                                        Intent intent=new Intent(RegisterActivity.this, OTPActivity.class);
                                                intent.putExtra("OBJECT",user);
                                                startActivity(intent);


                                    } catch (Exception e) {
                                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this,"Có lỗi xảy ra",Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });


    }

}