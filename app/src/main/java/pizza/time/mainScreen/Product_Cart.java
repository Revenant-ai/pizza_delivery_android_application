package pizza.time.mainScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import pizza.time.Adapters.CartAdapter;
import pizza.time.R;

public class Product_Cart extends AppCompatActivity {

    TextView Sub_total,GST,Grand_total,empty_cart_text;
    Button checkout,explore;
    MaterialCardView card;
    ImageView empty_img;

    FirebaseDatabase Database=FirebaseDatabase.getInstance();;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DatabaseReference cartref=Database.getReference().child(user.getUid()).child("order_Details").child("Cart");
        setContentView(R.layout.pizza_cart);

        Toolbar tool= (Toolbar)findViewById(R.id.menu_toolbar_cart);

        checkout=findViewById(R.id.checkout_btn);
        card=findViewById(R.id.cart_details_total);
        empty_cart_text=findViewById(R.id.empty_cart_Text);
        explore=findViewById(R.id.explore_menu_btn_cart);
        empty_img=findViewById(R.id.empty_Cart_img);


        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(getApplicationContext() , Menu.class);
                intent.putExtra("Screen","pizza");
                startActivity(intent);
            }
        });
        cartref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                ArrayList<String> pizza_names=new ArrayList<>();
                ArrayList<Double> pizza_prices=new ArrayList<>();
                ArrayList<Long> qty_cart= new ArrayList<>();
                ArrayList<String> photo=new ArrayList<>();
                ArrayList<String> size=new ArrayList<>();
                for(DataSnapshot children : snapshot.getChildren()){
                    pizza_names.add(children.child("pizza_name").getValue(String.class));
                    pizza_prices.add(children.child("price").getValue(Double.class));
                    qty_cart.add(children.child("qty").getValue(Long.class));
                    photo.add(children.child("photo").getValue(String.class));
                    size.add(children.child("size").getValue(String.class));
                }
                build_cart(pizza_names,pizza_prices,qty_cart,photo,size);

                if(pizza_names.size()!= 0)
                {
                    empty_cart_text.setVisibility(View.INVISIBLE);
                    explore.setVisibility(View.INVISIBLE);
                    empty_img.setVisibility(View.INVISIBLE);
                    checkout.setVisibility(View.VISIBLE);
                    card.setVisibility(View.VISIBLE);
                }
                else {
                    empty_cart_text.setVisibility(View.VISIBLE);
                    explore.setVisibility(View.VISIBLE);
                    empty_img.setVisibility(View.VISIBLE);
                    checkout.setVisibility(View.INVISIBLE);
                    card.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        checkout=findViewById(R.id.checkout_btn);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(getApplicationContext(),checkout_page.class);
                intent.putExtra("cart_total",Grand_total.getText());
                startActivity(intent);
            }
        });

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(),dashboard.class);
                startActivity(intent);

            }
        });
    }
    void build_cart(ArrayList<String> name, ArrayList<Double> price, ArrayList<Long> qty_cart, ArrayList<String> photo,ArrayList<String> size){

        RecyclerView cart_recycle=findViewById(R.id.recycler_cart);
        Sub_total=findViewById(R.id.cart_subtotal);
        GST=findViewById(R.id.Tax_cart);
        Grand_total=findViewById(R.id.grand_total_cart);

        CartAdapter cart_adap=new CartAdapter(Product_Cart.this,name,price,qty_cart,photo,size);
        cart_recycle.setAdapter(cart_adap);
        cart_recycle.setLayoutManager(new LinearLayoutManager(Product_Cart.this));


        double cart_sub=0,VAT;

        for(int j=0;j<price.size();j++){
            cart_sub+=price.get(j);
            System.out.println(cart_sub);
        }
        Sub_total.setText("₹ "+Double.toString(cart_sub));

        VAT=cart_sub*5/100;
        GST.setText("₹ "+Double.toString(VAT));

        long grand_total = Math.round(cart_sub + VAT);
        Grand_total.setText("₹ "+Long.toString(grand_total));

    }


}