package pizza.time.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pizza.time.Adapters.OrderAdapter;
import pizza.time.R;

public class Dashboard extends AppCompatActivity {

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_admin);


        database=FirebaseDatabase.getInstance();
        DatabaseReference order_Ref=database.getReference();
        order_Ref.child("Admin").child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                ArrayList<String> date= new ArrayList<>();
                ArrayList<String> grand_total= new ArrayList<>();
                ArrayList<String> time= new ArrayList<>();
                ArrayList<String> user= new ArrayList<>();
                ArrayList<String> order_id= new ArrayList<>();
                for(DataSnapshot snapyy: snapshot.getChildren()){

                    date.add(snapyy.child("date").getValue().toString());
                    grand_total.add(snapyy.child("grand_total").getValue().toString());
                    time.add(snapyy.child("time").getValue().toString());
                    System.out.println(snapyy.child("date").getValue().toString());
                    user.add(snapyy.child("user").getValue().toString());
                    order_id.add(snapyy.child("order_id").getValue().toString());

                }
                buildorders(date,grand_total,time,user,order_id);
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }

    private void buildorders(ArrayList<String> date,ArrayList<String> total, ArrayList<String> time,ArrayList<String> user,ArrayList<String> order_id) {
        System.out.println(total);

        RecyclerView order=findViewById(R.id.admin_order_recycle);
        OrderAdapter order_adap= new OrderAdapter(Dashboard.this,date,total,time,user,order_id);
        order.setAdapter(order_adap);
        order.setLayoutManager(new LinearLayoutManager(Dashboard.this));
    }
}