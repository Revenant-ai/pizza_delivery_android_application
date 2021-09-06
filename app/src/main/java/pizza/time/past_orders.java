package pizza.time;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

import pizza.time.Adapters.past_orders_adapter;


public class past_orders extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);


        db=FirebaseFirestore.getInstance();
        user=FirebaseAuth.getInstance();

        db.collection("users").document(user.getUid()).collection("Previous_orders").get().addOnCompleteListener(task -> {
            ArrayList<String> date= new ArrayList<>();
            ArrayList<String> grand_total= new ArrayList<>();
            ArrayList<String> time= new ArrayList<>();
            ArrayList<String> order_id= new ArrayList<>();

            for(DocumentSnapshot snapy : task.getResult()){
                date.add(snapy.getString("date"));
                grand_total.add(snapy.getString("grand_total"));
                order_id.add(snapy.getString("order_id"));
                time.add(snapy.getString("time"));
                System.out.println(time.get(0));
            }
            buildorders(date,grand_total,time,order_id);

        });
    }

    private void buildorders(ArrayList<String> date, ArrayList<String> total, ArrayList<String> time, ArrayList<String> order_id) {
        RecyclerView order=findViewById(R.id.past_order_recycle);
        past_orders_adapter past_order=new past_orders_adapter(date,total,time,past_orders.this,order_id);
        order.setAdapter(past_order);
        order.setLayoutManager(new LinearLayoutManager(past_orders.this));
    }
}