package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

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
                            "T??n kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                    isValidate=false;

                }
                if(password.length() < 6 ){
                    Toast.makeText(RegisterActivity.this,
                            "Password kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                    isValidate=false;
                }

                if(!edtRepass.getText().toString().equals(password)){



                    Toast.makeText(RegisterActivity.this,
                            "Password kh??ng tr??ng" + edtRepass.getText() + " " + edtPass.getText(), Toast.LENGTH_SHORT).show();
                    isValidate=false;
                }
                if(!PhoneNumberUtils.isGlobalPhoneNumber(phone)){
                    Toast.makeText(RegisterActivity.this,
                            "S??? ??i???n tho???i kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                    isValidate=false;
                }
                if(!Pattern.compile("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$").matcher(email).matches()){
                    Toast.makeText(RegisterActivity.this,
                            "Email kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                    isValidate=false;
                }


                databaseReference.child(phone).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().getValue()!=null){
                                Toast.makeText(RegisterActivity.this,
                                        "S??? ??i???n tho???i ???? ???????c ????ng k??", Toast.LENGTH_SHORT).show();
                                isValidate=false;
                            }else {
                                if(isValidate){
                                    try {
                                        User user=new User(username,AESCrypt.encrypt(password),phone,email);
                                        Intent intent=new Intent(RegisterActivity.this,LoginPinCode.class);
                                                intent.putExtra("OBJECT",user);
                                                startActivity(intent);
                                                finish();

                                    } catch (Exception e) {
                                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this,"C?? l???i x???y ra",Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });




    }
}