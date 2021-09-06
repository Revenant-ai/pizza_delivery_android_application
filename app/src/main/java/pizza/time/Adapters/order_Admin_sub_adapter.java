package pizza.time.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

import pizza.time.R;

public class order_Admin_sub_adapter extends RecyclerView.Adapter<order_Admin_sub_adapter.ViewHolder>
        {

            ArrayList<String> pizza_name;
            ArrayList<Double> pizza_price;
            ArrayList<Long> quantity;
            Context ct;
            ArrayList<String> size;

            public order_Admin_sub_adapter(ArrayList<String> pizza_name, ArrayList<Double> pizza_price, ArrayList<Long> quantity,ArrayList<String> size, Context ct) {
                this.pizza_name = pizza_name;
                this.pizza_price = pizza_price;
                this.quantity = quantity;
                this.ct = ct;
                this.size=size;
            }

            @NonNull
            @Override
            public order_Admin_sub_adapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                LayoutInflater inflater=LayoutInflater.from(ct);
                View view= inflater.inflate(R.layout.second_adapter_recycle_card,parent,false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull  order_Admin_sub_adapter.ViewHolder holder, int position) {

                holder.name.setText(pizza_name.get(position));
                holder.qty.setText(quantity.get(position).toString());
                holder.price.setText(pizza_price.get(position).toString());
                holder.size.setText(size.get(position));


            }

            @Override
            public int getItemCount() {
                return pizza_name.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {

                TextView name,qty,price,size;


                public ViewHolder(@NonNull View itemView) {
                    super(itemView);

                    name=itemView.findViewById(R.id.pizza_name_admin);
                    qty=itemView.findViewById(R.id.pizza_qty_admin);
                    price=itemView.findViewById(R.id.pizza_price_admin);
                    size=itemView.findViewById(R.id.pizza_size_admin);
                }
            }
        }
