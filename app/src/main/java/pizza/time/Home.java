package pizza.time;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pizza.time.Admin.Dashboard;
import pizza.time.mainScreen.dashboard;
import pizza.time.signUp.Login;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        if(user!= null ){
            db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        String role=task.getResult().getString("role");
                        if(role.equals("Admin")){
                            startActivity(new Intent(Home.this, Dashboard.class));
                            finish();
                        }
                        else{
                            startActivity(new Intent(Home.this, dashboard.class));
                            System.out.println(role);
                        }
                    }
                    else{
                        System.out.println("Not successful");
                    }
                }
            });

        }
        else{
            startActivity(new Intent(Home.this, Login.class));
        }
    }
}