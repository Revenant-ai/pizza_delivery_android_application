package pizza.time.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import pizza.time.R;

public class past_orders_adapter extends RecyclerView.Adapter<past_orders_adapter.OrderViewHolder> {

    ArrayList<String> date;
    ArrayList<String> grand_total;
    ArrayList<String> time;
    Context ct;
    ArrayList<String> order_id;

    public past_orders_adapter(ArrayList<String> date, ArrayList<String> grand_total, ArrayList<String> time, Context ct, ArrayList<String> order_id) {
        this.date = date;
        this.grand_total = grand_total;
        this.time = time;
        this.ct = ct;
        this.order_id = order_id;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("flaggg");
        LayoutInflater inflater=LayoutInflater.from(ct);
        View view=inflater.inflate(R.layout.past_order_cascade,parent,false);
        return new past_orders_adapter.OrderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  past_orders_adapter.OrderViewHolder holder, int position) {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        System.out.println("glagg 2");
        FirebaseAuth user=FirebaseAuth.getInstance();

        holder.date.setText(date.get(position));
        holder.grand_total.setText(grand_total.get(position));
        holder.order_no.setText("Order no. "+order_id.get(position));

        db.collection("users").document(user.getUid()).collection("Previous_orders").document(order_id.get(position)).collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<String> name= new ArrayList<>();
                    ArrayList<Double> price= new ArrayList<>();
                    ArrayList<Long> qty= new ArrayList<>();
                    ArrayList<String> size= new ArrayList<>();

                    for(QueryDocumentSnapshot snapy : task.getResult())
                    {
                        name.add(snapy.getString("name"));
                        price.add(snapy.getDouble("price"));
                        qty.add(snapy.getLong("qty"));
                        size.add(snapy.getString("size"));
                    }
                    order_Admin_sub_adapter adap=new order_Admin_sub_adapter(name,price,qty,size,ct);
                    holder.sub_recycle.setAdapter(adap);
                    holder.sub_recycle.setLayoutManager(new LinearLayoutManager(ct));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView date,grand_total,order_no;
        RecyclerView sub_recycle;
        public OrderViewHolder(@NonNull  View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.past_order_date);
            grand_total=itemView.findViewById(R.id.past_order_amt);
            order_no=itemView.findViewById(R.id.past_order_no);
            sub_recycle=itemView.findViewById(R.id.sub_prev_order);
        }
    }
}
