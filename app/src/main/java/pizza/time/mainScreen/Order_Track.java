package pizza.time.mainScreen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import pizza.time.R;
import pizza.time.data_classes.Admin_cart;
import pizza.time.data_classes.Order_client;

public class Order_Track extends AppCompatActivity {

    TextView step1,step2,step3,step4,header_order;
    ImageView icon1,icon2,icon3,icon4;
    View v1,v2,v3;
    Button menu_btn;
    FirebaseAuth user=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_track);

        header_order=findViewById(R.id.header_no_track);
        menu_btn=findViewById(R.id.explore_menu_btn_track_order);
        step1=findViewById(R.id.tracl_text_1);
        icon1=findViewById(R.id.track_logo1);
        v1=findViewById(R.id.track_vert_1);
        step2=findViewById(R.id.tracl_text_2);
        icon2=findViewById(R.id.track_logo_2);
        v2=findViewById(R.id.track_vert_2);
        step3=findViewById(R.id.tracl_text_3);
        icon3=findViewById(R.id.track_logo3);
        v3=findViewById(R.id.track_vert_3);
        step4=findViewById(R.id.tracl_text_4);
        icon4=findViewById(R.id.track_logo4);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(getApplicationContext() , Menu.class);
                intent.putExtra("Screen","pizza");
                startActivity(intent);

            }
        });
        DatabaseReference order_Ref=database.getReference().child(user.getUid()).child("order_Details").child("order_info").child("status");
        order_Ref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.getValue() == null)
                {
                    header_order.setVisibility(View.VISIBLE);
                    menu_btn.setVisibility(View.VISIBLE);
                    step1.setVisibility(View.INVISIBLE);
                    icon1.setVisibility(View.INVISIBLE);
                    v1.setVisibility(View.INVISIBLE);

                    step2.setVisibility(View.INVISIBLE);
                    icon2.setVisibility(View.INVISIBLE);
                    v2.setVisibility(View.INVISIBLE);

                    step3.setVisibility(View.INVISIBLE);
                    icon3.setVisibility(View.INVISIBLE);
                    v3.setVisibility(View.INVISIBLE);

                    step4.setVisibility(View.INVISIBLE);
                    icon4.setVisibility(View.INVISIBLE);
                }

                else
                {
                    step1.setVisibility(View.VISIBLE);
                    icon1.setVisibility(View.VISIBLE);
                    v1.setVisibility(View.VISIBLE);

                    step2.setVisibility(View.VISIBLE);
                    icon2.setVisibility(View.VISIBLE);
                    v2.setVisibility(View.VISIBLE);

                    step3.setVisibility(View.VISIBLE);
                    icon3.setVisibility(View.VISIBLE);
                    v3.setVisibility(View.VISIBLE);

                    step4.setVisibility(View.VISIBLE);
                    icon4.setVisibility(View.VISIBLE);

                    if(snapshot.getValue().equals("order_confirm") || snapshot.getValue().equals("order_kitchen") || snapshot.getValue().equals("order_pack") ||  (snapshot.getValue().equals("order_dispatch"))){

                        step1.setTextColor(getResources().getColor(R.color.lime));
                        icon1.setBackgroundTintList(getResources().getColorStateList(R.color.lime));

                    }

                    if(snapshot.getValue().equals("order_kitchen") || snapshot.getValue().equals("order_pack") ||  (snapshot.getValue().equals("order_dispatch"))){

                        step2.setTextColor(getResources().getColor(R.color.lime));
                        icon2.setBackgroundTintList(getResources().getColorStateList(R.color.lime));
                        v1.setBackgroundColor(getResources().getColor(R.color.lime));
                    }
                    if(snapshot.getValue().equals("order_pack") ||  (snapshot.getValue().equals("order_dispatch"))){

                        step3.setTextColor(getResources().getColor(R.color.lime));
                        icon3.setBackgroundTintList(getResources().getColorStateList(R.color.lime));
                        v2.setBackgroundColor(getResources().getColor(R.color.lime));
                    }

                    if(snapshot.getValue().equals("order_dispatch")){

                        step4.setTextColor(getResources().getColor(R.color.lime));
                        icon4.setBackgroundTintList(getResources().getColorStateList(R.color.lime));
                        v3.setBackgroundColor(getResources().getColor(R.color.lime));
                    }
                    if(snapshot.getValue().equals("Completed")){
                        System.out.println(snapshot.getValue());

                        database.getReference().child(user.getUid()).child("order_Details").child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<String> name= new ArrayList<>();
                                ArrayList<Double> price= new ArrayList<>();
                                ArrayList<Integer> qty= new ArrayList<>();
                                ArrayList<String> size= new ArrayList<>();

                                for(DataSnapshot snappy: snapshot.getChildren()){

                                    name.add(snappy.child("pizza_name").getValue(String.class));
                                    qty.add(snappy.child("qty").getValue(Integer.class));
                                    price.add(snappy.child("price").getValue(Double.class));
                                    size.add(snappy.child("size").getValue(String.class));




                                    database.getReference().child(user.getUid()).child("order_Details").child("order_info").child("order_id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull  Task<DataSnapshot> task) {

                                            for(int j=0;j<name.size();j++)
                                            {
                                                db.collection("users").document(user.getUid()).collection("Previous_orders")
                                                        .document(task.getResult().getValue(String.class)).collection("Cart").document(name.get(j)+" "+size.get(j))
                                                        .set(new Admin_cart(name.get(j),price.get(j),qty.get(j),size.get(j)));

                                            }
                                            DatabaseReference order_detail=database.getReference().child(user.getUid()).child("order_Details").child("order_info").getRef();
                                            order_detail.addListenerForSingleValueEvent(new ValueEventListener() {


                                                @Override
                                                public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                                    db.collection("users").document(user.getUid()).collection("Previous_orders")
                                                            .document( task.getResult().getValue(String.class))
                                                            .set(new Order_client(snapshot.child("date").getValue().toString(),snapshot.child("time").getValue().toString(),"Completed",snapshot.child("grand_total").getValue().toString(),user.getUid(),snapshot.child("order_id").getValue().toString()))
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull  Task<Void> task) {
                                                                    database.getReference().child(user.getUid()).child("order_Details").removeValue();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull  Exception e) {
                                                            System.out.println(e);
                                                        }
                                                    });



                                                }

                                                @Override
                                                public void onCancelled(@NonNull  DatabaseError error) {

                                                }
                                            });
                                        }
                                    });
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull  DatabaseError error) {
                                System.out.println(error);
                            }
                        });



                    }
                }


            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {



            }
        });
    }
}