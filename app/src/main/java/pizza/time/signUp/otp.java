package pizza.time.signUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;

import pizza.time.R;
import pizza.time.mainScreen.dashboard;

public class otp extends AppCompatActivity {

    EditText in_otp1,in_otp2,in_otp3,in_otp4,in_otp5,in_otp6;
    Button verify_otp;
    ProgressBar otp_prog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        in_otp1=findViewById(R.id.otp1);
        in_otp2=findViewById(R.id.otp2);
        in_otp3=findViewById(R.id.otp3);
        in_otp4=findViewById(R.id.otp4);
        in_otp5=findViewById(R.id.otp5);
        in_otp6=findViewById(R.id.otp6);
        verify_otp=findViewById(R.id.verify_otp);
        otp_prog=findViewById(R.id.otp_verify_progress);


        String getOtp=getIntent().getStringExtra("otp");

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!in_otp1.getText().toString().trim().isEmpty() &&
                        !in_otp2.getText().toString().trim().isEmpty() &&
                        !in_otp3.getText().toString().trim().isEmpty() &&
                        !in_otp4.getText().toString().trim().isEmpty() &&
                        !in_otp5.getText().toString().trim().isEmpty() &&
                        !in_otp6.getText().toString().trim().isEmpty()){
                    String entercodeotp=in_otp1.getText().toString() +
                            in_otp2.getText().toString() +
                            in_otp3.getText().toString() +
                            in_otp4.getText().toString() +
                            in_otp5.getText().toString() +
                            in_otp6.getText().toString();

                    if(getOtp != null){
                        otp_prog.setVisibility(View.VISIBLE);
                        verify_otp.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthCredential.zzc(
                          getOtp,entercodeotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull  Task<AuthResult> task) {
                                        otp_prog.setVisibility(View.GONE);
                                        verify_otp.setVisibility(View.VISIBLE);

                                        if(task.isSuccessful()){
                                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                                            Log.d("MyTAG", "onComplete: " + (isNew ? "new user" : "old user"));
                                            System.out.println(isNew);
                                            Intent intent;
                                            if(isNew)
                                            {
                                                intent = new Intent(getApplicationContext(), user_registration.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                intent = new Intent(getApplicationContext(), dashboard.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }


                                        }else{
                                            Toast.makeText(otp.this,"Enter correct OTP",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(otp.this, "Please check internet connection",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(otp.this,"Please enter all numbers",Toast.LENGTH_SHORT).show();
                }
            }
        });
        otpMove();
    }

    private void otpMove() {
        in_otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    in_otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        in_otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    in_otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        in_otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    in_otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        in_otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    in_otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        in_otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    in_otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}