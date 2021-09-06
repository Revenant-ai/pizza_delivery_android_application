package pizza.time.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import pizza.time.Admin.Dashboard;
import pizza.time.R;
import pizza.time.data_classes.Order_client;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    ArrayList<String> date;
    ArrayList<String> grand_total;
    ArrayList<String> time;
    Context ct;
    ArrayList<String> user;
    ArrayList<String> order_id;

    public OrderAdapter(Context ct, ArrayList<String> date, ArrayList<String> grand_total, ArrayList<String> time, ArrayList<String> user,ArrayList<String> order_id) {
        this.date = date;
        this.grand_total = grand_total;
        this.time = time;
        this.ct=ct;
        this.user=user;
        this.order_id=order_id;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(ct);
        View view=inflater.inflate(R.layout.order_admin_card,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  OrderAdapter.OrderViewHolder holder, int position) {

        holder.date.setText(date.get(position));
        holder.time.setText(time.get(position));

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference cartred=database.getReference().child("Admin").child("orders").child(user.get(position)).child("Cart").getRef();
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        cartred.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                ArrayList<String> name= new ArrayList<>();
                ArrayList<Double> price= new ArrayList<>();
                ArrayList<Long> qty= new ArrayList<>();
                ArrayList<String> size= new ArrayList<>();

                double total_price=0;
                for(DataSnapshot snappy: snapshot.getChildren()){

                    name.add(snappy.child("name").getValue().toString());
                    qty.add(snappy.child("qty").getValue(Long.class));
                    price.add(snappy.child("price").getValue(Double.class));
                    System.out.println(snappy.child("order_id"));
                    size.add(snappy.child("size").getValue(String.class));
                    total_price += snappy.child("price").getValue(Double.class);
                }
                order_Admin_sub_adapter adap=new order_Admin_sub_adapter(name,price,qty,size,ct);
                holder.sub_recyler.setAdapter(adap);
                holder.sub_recyler.setLayoutManager(new LinearLayoutManager(ct));
                String grand_total= "Total Amount :" + total_price;
                holder.grand_total.setText(grand_total);


                holder.track_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isChecked()){
                            System.out.println("checked");
                            database.getReference().child(user.get(position)).child("order_Details").child("order_info").child("status").setValue("order_confirm");
                        }
                        else
                        {
                            database.getReference().child(user.get(position)).child("order_Details").child("order_info").child("status").setValue("awaiting_conformation");
                        }
                    }
                });

                holder.track_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(buttonView.isChecked()){
                            database.getReference().child(user.get(position)).child("order_Details").child("order_info").child("status").setValue("order_kitchen");
                            holder.track_1.setVisibility(View.INVISIBLE);
                        }
                        else{
                            holder.track_1.setVisibility(View.VISIBLE);
                            database.getReference().child(user.get(position)).child("order_Details").child("order_info").child("status").setValue("order_confirmed");
                        }
                    }
                });

                holder.track_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(buttonView.isChecked()){
                            database.getReference().child(user.get(position)).child("order_Details").child("order_info").child("status").setValue("order_pack");
                            holder.track_2.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            holder.track_2.setVisibility(View.VISIBLE);
                            database.getReference().child(user.get(position)).child("order_Details").child("order_info").child("status").setValue("order_kitchen");
                        }
                    }
                });

                holder.track_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(buttonView.isChecked()){
                            database.getReference().child(user.get(position)).child("order_Details").child("order_info").child("status").setValue("order_dispatch");
                            holder.track_3.setVisibility(View.INVISIBLE);
                        }

                        else
                        {
                            holder.track_3.setVisibility(View.VISIBLE);
                            database.getReference().child(user.get(position)).child("order_Details").child("order_info").child("status").setValue("order_pack");
                        }
                    }
                });

                holder.order_delivered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        database.getReference().child(user.get(position)).child("order_Details").child("order_info").child("status").setValue("Completed");
                        Order_client orderClient =new Order_client(date.get(position),time.get(position),"Completed",grand_total,user.get(position),order_id.get(position));
                        System.out.println(order_id.get(position));
                        db.collection("/Admin").document(order_id.get(position)).set(orderClient).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull  Task<Void> task) {
                                database.getReference().child("Admin").child("orders").child(user.get(position)).removeValue();
                                database.getReference().child(user.get(position)).removeValue();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                System.out.println(e);                           }
                        });


                    }
                });

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView date,price,grand_total,time;
        RecyclerView sub_recyler;
        SwitchMaterial track_1,track_2,track_3,track_4;
        Button order_delivered;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.textView11);
            date=itemView.findViewById(R.id.textView13);
            sub_recyler=itemView.findViewById(R.id.sub_recycle_admin);
            grand_total=itemView.findViewById(R.id.grad_total_admin);
            track_1=itemView.findViewById(R.id.track_one);
            track_2=itemView.findViewById(R.id.track_two);
            track_3=itemView.findViewById(R.id.track_three);
            track_4=itemView.findViewById(R.id.track_four);
            order_delivered=itemView.findViewById(R.id.order_deliver_btn);
        }
    }

}
