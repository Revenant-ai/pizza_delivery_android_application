package pizza.time.signUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import pizza.time.R;
import pizza.time.data_classes.User;
import pizza.time.mainScreen.dashboard;

public class user_registration extends AppCompatActivity {

    private FirebaseFirestore db;
    TextView name,mail;
    Button reg_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();

        setContentView(R.layout.user_registration);


        name=findViewById(R.id.user_name);
        mail=findViewById(R.id.user_mail);
        reg_button=findViewById(R.id.reg_button);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("mytag", "addUsertodb: data1 ");
                addUsertodb(name.getText().toString(),mail.getText().toString());
            }
        });
    }



    private void addUsertodb(String name,String mail){


        String phone= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("mytag", "addUsertodb: data2 ");
        User user= new User(name,mail,phone);
        Log.d("mytag", "addUsertodb: data1 "+user.getEmail());
        db.collection("users").document(uid).set(user)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "registration is completed", Toast.LENGTH_SHORT).show();
                Intent intent;
                intent = new Intent(getApplicationContext(), dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                System.out.println("data point 1");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(getApplicationContext() ,"Registration failed", Toast.LENGTH_SHORT).show();
                System.out.println("data point 2");
            }
        });


    }
}