package pizza.time.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import pizza.time.R;

public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {

    ArrayList<String> pizza_name;
    ArrayList<Double> pizza_price;
    ArrayList<Long> quantity;
    ArrayList<String> pizza_photo;
    ArrayList<String> pizza_size;
    Context ct;


    public CartAdapter(Context context, ArrayList<String> name, ArrayList<Double>  price, ArrayList<Long> Quantity,ArrayList<String> Pizza_photo, ArrayList<String> Pizza_size){
        pizza_name=name;
        pizza_price=price;
        ct=context;
        quantity=Quantity;
        pizza_photo=Pizza_photo;
        pizza_size=Pizza_size;
    }



    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(ct);
        View view= inflater.inflate(R.layout.cart_product_card,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseAuth user= FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        holder.qty.setText(pizza_price.get(position).toString());
        holder.pizza_name_cart.setText(pizza_name.get(position));
        holder.pizza_price_cart.setText(pizza_price.get(position).toString());
        holder.qty.setText(quantity.get(position).toString());
        holder.size.setText(pizza_size.get(position));

        int resId = ct.getResources().getIdentifier(pizza_photo.get(position), "drawable", ct.getPackageName());
        holder.pizza_cart_img.setImageResource(resId);


        holder.add_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference dbre=database.getReference();
                dbre.child(user.getUid()).child("order_Details").child("Cart").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull  Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else{

                            long pizza_qty=task.getResult().child(pizza_name.get(position)+" "+pizza_size.get(position)).child("qty").getValue(Long.class);
                            double pizza_price=task.getResult().child(pizza_name.get(position)+" "+pizza_size.get(position)).child("price").getValue(Double.class);
                            dbre.child(user.getUid()).child("order_Details").child("Cart").child(pizza_name.get(position)+" "+pizza_size.get(position)).child("qty").setValue(pizza_qty+1);
                            dbre.child(user.getUid()).child("order_Details").child("Cart").child(pizza_name.get(position)+" "+pizza_size.get(position)).child("price").setValue(pizza_price + (pizza_price/pizza_qty));
                        }

                    }
                });


            }
        });

        holder.remove_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference dbre=database.getReference();
                dbre.child(user.getUid()).child("order_Details").child("Cart").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull  Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else{
                            long pizza_qty=task.getResult().child(pizza_name.get(position)+" "+pizza_size.get(position)).child("qty").getValue(Long.class);
                            double pizza_price=task.getResult().child(pizza_name.get(position)+" "+pizza_size.get(position)).child("price").getValue(Double.class);
                            if(pizza_qty >1)
                            {
                                dbre.child(user.getUid()).child("order_Details").child("Cart").child(pizza_name.get(position)+" "+pizza_size.get(position)).child("qty").setValue(pizza_qty-1);
                                dbre.child(user.getUid()).child("order_Details").child("Cart").child(pizza_name.get(position)+" "+pizza_size.get(position)).child("price").setValue(pizza_price - (pizza_price/pizza_qty));
                            }
                            else {
                                dbre.child(user.getUid()).child("order_Details").child("Cart").child(pizza_name.get(position)+" "+pizza_size.get(position)).removeValue();
                            }

                        }

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return pizza_name.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        TextView Sub_total,GST,Grand_total;
        ImageView pizza_cart_img;
        TextView pizza_name_cart;
        TextView pizza_price_cart;
        Button add_qty;
        Button remove_qty;
        TextView qty,size;
        MaterialCardView pizza_Card;
        public CartViewHolder(@NonNull  View itemView) {
            
            super(itemView);
            Sub_total=itemView.findViewById(R.id.cart_subtotal);
            GST=itemView.findViewById(R.id.Tax_cart);
            Grand_total=itemView.findViewById(R.id.grand_total_cart);
            pizza_name_cart=itemView.findViewById(R.id.pizza_name_cart);
            pizza_price_cart=itemView.findViewById(R.id.pizza_cart_price);
            add_qty=itemView.findViewById(R.id.increase_cart_qty);
            remove_qty=itemView.findViewById(R.id.decrease_cart_qty);
            qty=itemView.findViewById(R.id.Qty_text);
            pizza_Card=itemView.findViewById(R.id.pizza_Cart_card);
            pizza_cart_img=itemView.findViewById(R.id.pizza_Cart_image);
            size=itemView.findViewById(R.id.pizza_Cart_size);
        }
    }

}
