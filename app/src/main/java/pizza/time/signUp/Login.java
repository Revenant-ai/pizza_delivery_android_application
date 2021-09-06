package pizza.time.signUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import pizza.time.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_layout);

        TextInputEditText phone = findViewById(R.id.phone);
        Button otp_butt = findViewById(R.id.butt_otp);

        ProgressBar prog_otp= findViewById(R.id.otp_progress);

        otp_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!phone.getText().toString().trim().isEmpty()){
                    if((phone.getText().toString().trim()).length() == 10){

                        prog_otp.setVisibility(View.VISIBLE);
                        otp_butt.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + phone.getText().toString().trim(),
                                60,
                                TimeUnit.SECONDS,
                                Login.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull  PhoneAuthCredential phoneAuthCredential) {
                                        prog_otp.setVisibility(View.GONE);
                                        otp_butt.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull  FirebaseException e) {
                                        prog_otp.setVisibility(View.GONE);
                                        otp_butt.setVisibility(View.VISIBLE);
                                        Toast.makeText(Login.this, "Please check your internet connection",Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String otp, @NonNull  PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        prog_otp.setVisibility(View.GONE);
                                        otp_butt.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(),otp.class);
                                        intent.putExtra("mobile",phone.getText().toString());
                                        intent.putExtra("otp",otp);
                                        startActivity(intent);
                                    }
                                }
                        );

                    }else{
                        Toast.makeText(Login.this,"Please enter correct mobile number",Toast.LENGTH_SHORT);
                    }
                }else {
                    Toast.makeText(Login.this,"Enter mobile number",Toast.LENGTH_SHORT);
                }
            }
        });
    }
}