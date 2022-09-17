package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OTPactivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtPincode,edtConfirm;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        btnLogin=findViewById(R.id.btn_loginPincode);
        edtPincode=findViewById(R.id.edtEnterpincode);
        edtConfirm=findViewById(R.id.edtConfirm);
        Intent intent=getIntent();
        String phone= intent.getStringExtra("PHONE");






        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pincode=edtPincode.getText().toString();
                String confirm=edtConfirm.getText().toString();

                databaseReference.child(phone).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DatabaseReference pincodeDR=task.getResult().child("pincode").getRef();
                            pincodeDR.setValue(pincode);

                            if(pincode.equals(confirm)){
                                Toast.makeText(OTPactivity.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                Intent intent1=new Intent(OTPactivity.this,MainActivity.class);
                                startActivity(intent1);
                            }else {
                                Toast.makeText(OTPactivity.this,"Mã pin không hợp lệ",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(OTPactivity.this,"Có lỗi xảy ra",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }
}