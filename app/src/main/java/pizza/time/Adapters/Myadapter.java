package pizza.time.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pizza.time.data_classes.Cart;
import pizza.time.mainScreen.pizza_customize;

import java.util.ArrayList;
import java.util.Vector;

import pizza.time.R;
import pizza.time.data_classes.Menu;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {

    Vector<String> pizza_name;
    Vector<String> pizza_desc;
    Vector<Double> pizza_price;
    Context ct;
    Vector<String> pizza_photo;
    Vector<String> pizza_size;


    public Myadapter(Context context, Vector<String> name, Vector<String>  desc, Vector<Double> price, Vector<String> photo, Vector<String> size) {

        pizza_name=name;
        pizza_desc=desc;
        pizza_price=price;
        ct=context;
        pizza_photo=photo;
        pizza_size=size;
    }


    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(ct);
        View view=inflater.inflate(R.layout.menu_card,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Myadapter.MyViewHolder holder, int position) {
        holder.Pizza_name.setText(pizza_name.get(position));
        holder.Pizza_desc.setText(pizza_desc.get(position));
        holder.Pizza_price.setText("₹"+pizza_price.get(position).toString());
        System.out.println(pizza_price.get(position).toString());


        int resId = ct.getResources().getIdentifier(pizza_photo.get(position), "drawable", ct.getPackageName());
        holder.pizza_image.setImageResource(resId);


        double price=pizza_price.get(position);
        holder.small_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizza_price.set(position, price*1);
                pizza_size.set(position,"Small");

                holder.box_s.setBackgroundColor(ct.getResources().getColor(R.color.yellow));
                holder.box_m.setBackgroundColor(ct.getResources().getColor(R.color.white));
                holder.box_l.setBackgroundColor(ct.getResources().getColor(R.color.white));

            }
        });


        holder.mid_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizza_price.set(position, price*2);
                pizza_size.set(position,"Medium");
                holder.Pizza_price.setText("₹"+pizza_price.get(position).toString());
                holder.box_m.setBackgroundColor(ct.getResources().getColor(R.color.yellow));
                holder.box_s.setBackgroundColor(ct.getResources().getColor(R.color.white));
                holder.box_l.setBackgroundColor(ct.getResources().getColor(R.color.white));


            }
        });


        holder.large_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizza_price.set(position, price*3);
                pizza_size.set(position,"large");
                holder.Pizza_price.setText("₹"+pizza_price.get(position).toString());

                holder.box_l.setBackgroundColor(ct.getResources().getColor(R.color.yellow));
                holder.box_m.setBackgroundColor(ct.getResources().getColor(R.color.white));
                holder.box_s.setBackgroundColor(ct.getResources().getColor(R.color.white));

            }
        });




        holder.Add_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference dbre=database.getReference();
                DatabaseReference cartref=dbre.child(user.getUid()).child("order_Details").child("Cart");

                cartref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        if(!snapshot.hasChild(pizza_name.get(position)+" "+pizza_size.get(position)))
                        {
                            Cart cart =new Cart(pizza_name.get(position),pizza_price.get(position),1,pizza_photo.get(position),pizza_size.get(position));
                            cartref.child(pizza_name.get(position)+" "+pizza_size.get(position)).setValue(cart);


                        }
                        else{
                           double price=snapshot.child(pizza_name.get(position)+" "+pizza_size.get(position)).child("price").getValue(Double.class);
                            long qty=snapshot.child(pizza_name.get(position)+" "+pizza_size.get(position)).child("qty").getValue(Long.class);
                            cartref.child(pizza_name.get(position)+" "+pizza_size.get(position)).child("qty").setValue(qty+1);
                            cartref.child(pizza_name.get(position)+" "+pizza_size.get(position)).child("price").setValue(price + (price/qty));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {
                        System.out.println(error);
                    }
                });
            }
        });

        ArrayList<String> crust= new ArrayList<>();
        crust.add("Small");
        crust.add("Medium");
        crust.add("Large");

        holder.customise_pizza.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(ct,pizza_customize.class);
                holder.pizza_image.setTransitionName("pizza_transition");
                ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) ct,holder.pizza_image,"pizza_transition");
                ct.startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {

        return pizza_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Pizza_name;
        TextView Pizza_price;
        TextView Pizza_desc;
        Button Add_Cart;
        Button customise_pizza;
        ImageView pizza_image;
        MaterialCardView small_size,mid_size,large_size,box_s,box_m,box_l;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            Pizza_name=itemView.findViewById(R.id.pizza_name);
            Pizza_price=itemView.findViewById(R.id.pizza_price);
            Pizza_desc=itemView.findViewById(R.id.pizza_desc);
            Add_Cart=itemView.findViewById(R.id.add_to_cart);
            customise_pizza=itemView.findViewById(R.id.customise_pizza);
            pizza_image=itemView.findViewById(R.id.pizza_image);
            small_size=itemView.findViewById(R.id.small_button);
            mid_size=itemView.findViewById(R.id.medium_button);
            large_size=itemView.findViewById(R.id.large_button);
            box_s=itemView.findViewById(R.id.box_small);
            box_m=itemView.findViewById(R.id.box_med);
            box_l=itemView.findViewById(R.id.box_large);
        }
    }

    private String get_size(String size){
        return size;
    }

}
