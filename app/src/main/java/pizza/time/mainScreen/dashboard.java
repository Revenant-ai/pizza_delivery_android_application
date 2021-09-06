package pizza.time.mainScreen;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pizza.time.R;
import pizza.time.User_profile;
import pizza.time.mainScreen.MenuFragments.Sides;
import pizza.time.mainScreen.MenuFragments.Veg_Pizza;

public class dashboard extends AppCompatActivity {

TextView cart_count;
MaterialCardView cart_card;
FloatingActionButton go_to_cart;
ImageButton profile_btn,menu_btn,deals_btn,track_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

       cart_count=findViewById(R.id.cart_count_dashboard);
       go_to_cart=findViewById(R.id.floatingActionButton);
       cart_card=findViewById(R.id.cart_count_Card);
       profile_btn=findViewById(R.id.dashboard_profile);
       menu_btn=findViewById(R.id.bottom_menu);
       deals_btn=findViewById(R.id.bottom_deals);
       track_btn=findViewById(R.id.bottom_track);

       MaterialCardView pizza_card=findViewById(R.id.pizza_card);
       MaterialCardView sides_card=findViewById(R.id.sides_Card);
       MaterialCardView drinks_card=findViewById(R.id.drinks_card);
       MaterialCardView dessert_card=findViewById(R.id.dessert_Card);


        DatabaseReference dbre=database.getReference();
        dbre.child(user.getUid()).child("order_Details").child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                int size=(int) snapshot.getChildrenCount();
                String cart_qty=Integer.toString(size);
                cart_count.setText(cart_qty);
                cart_card.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                System.out.println(error);
            }
        });



        pizza_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("Screen","pizza");
                startActivity(intent);
            }
        });
        sides_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("Screen","sides");
                startActivity(intent);

            }
        });
        drinks_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("Screen","drinks");
                startActivity(intent);
            }
        });
        dessert_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("Screen","dessert");
                startActivity(intent);
            }
        });

        go_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(),Product_Cart.class);
                startActivity(intent);
            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(getApplicationContext(), User_profile.class);
                startActivity(intent);


            }
        });

        track_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(),Order_Track.class);
                startActivity(intent);
            }
        });

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("Screen","pizza");
                startActivity(intent);

            }
        });



    }

    private long pressedTime;
    @Override
    public void onBackPressed() {

            if (pressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();
        }

}
