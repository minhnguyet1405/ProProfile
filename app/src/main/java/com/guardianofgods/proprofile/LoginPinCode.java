package com.guardianofgods.proprofile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class LoginPinCode extends AppCompatActivity {

    TextView edtname;
    Button btnConfirmOTP;
    FirebaseAuth mAuth;
    String verifyID;
    PinView pinView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");

        setContentView(R.layout.activity_login_pin_code);
        edtname=findViewById(R.id.name_pincode);
        pinView=findViewById(R.id.pinview);
        btnConfirmOTP=findViewById(R.id.btn_login_pincode);

        Intent intent=getIntent();
        user=(User) intent.getSerializableExtra("OBJECT");
        Toast.makeText(LoginPinCode.this,user.phone,Toast.LENGTH_SHORT).show();
        edtname.setText("Xin chào, "+user.username);
        mAuth=FirebaseAuth.getInstance();

        Toast.makeText(LoginPinCode.this,GetCountryZipCode(),Toast.LENGTH_SHORT).show();
        VerifyOTP(user.phone);
        String OTP=pinView.getText().toString();

        btnConfirmOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSentOTP();
            }
        });
    }
    // gửi api để nhận mã OTP
    private void VerifyOTP(String phone){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            // hàm trả về xác nhận thành công
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                                Toast.makeText(LoginPinCode.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();

                            }
                            // hàm trả về thất bại
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(LoginPinCode.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            //hàm đọc mã OTP trả về từ firebase
                            @Override
                            public void onCodeSent(@NonNull String firebaseVerifyID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(firebaseVerifyID, forceResendingToken);
                                verifyID = firebaseVerifyID;
                                //pinView.setText(verifyID);
                                //goToEnterOtpActivity()
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    //gửi verify lên firebase
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(LoginPinCode.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                            databaseReference.child(user.phone).setValue(user,new DatabaseReference.CompletionListener(){
                                @Override
                                public void onComplete(@Nullable DatabaseError er, @Nullable DatabaseReference ref){
                                    Toast.makeText(LoginPinCode.this,
                                            "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(LoginPinCode.this,LoginActivity.class);
                                    //intent.putExtra("NAME",user.username);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(LoginPinCode.this,"Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                            }
                        }
                    }
                });
    }
    public String GetCountryZipCode(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }

        return CountryZipCode;
    }
    public void clickSentOTP(){
        String OTP=pinView.getText().toString();
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verifyID,OTP);
        signInWithPhoneAuthCredential(credential);

    }


}