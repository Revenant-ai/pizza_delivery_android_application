package pizza.time.mainScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pizza.time.R;
import pizza.time.data_classes.Admin_cart;
import pizza.time.data_classes.Order_client;
import pizza.time.data_classes.User;

public class checkout_page extends AppCompatActivity {

    TextInputEditText name,mail,phone;
    Button place_order;
    TextInputLayout name_textlay,phone_lay,mail_lay;
    private TextWatcher checkout = new TextWatcher() {



        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {



            String nameInput=name.getText().toString().trim();
            String mailInput=mail.getText().toString().trim();
            String phoneInput=phone.getText().toString().trim();

            if(nameInput.isEmpty() ){

                name.setError("You Cannot leave this field empty");
                name_textlay.setBoxStrokeColor(getResources().getColor(R.color.dark_red));
                place_order.setEnabled(false);
            }
            else {
                name_textlay.setBoxStrokeColor(getResources().getColor(R.color.design_default_color_primary_dark));
                place_order.setEnabled(true);
                name.setError(null);
            }

            if(phoneInput.isEmpty()){
                phone.setError("You Cannot leave this field empty");
                phone_lay.setBoxStrokeColor(getResources().getColor(R.color.dark_red));
                place_order.setEnabled(false);
            }
            else {
                phone_lay.setBoxStrokeColor(getResources().getColor(R.color.design_default_color_primary_dark));
                place_order.setEnabled(true);
                phone.setError(null);
            }


            if(mailInput.isEmpty()){
                mail.setError("You Cannot leave this field empty");
                System.out.println(mail_lay.isErrorEnabled());
                mail_lay.setBoxStrokeColor(getResources().getColor(R.color.dark_red));
                place_order.setEnabled(false);
            }
            else {
                mail_lay.setBoxStrokeColor(getResources().getColor(R.color.design_default_color_primary_dark));
                place_order.setEnabled(true);
                mail.setError(null);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {




        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page_layout);

        Intent intent = getIntent();
        String checkout_price=intent.getStringExtra("cart_total");


        place_order=findViewById(R.id.place_order);
        name=findViewById(R.id.checkout_name_field);
        mail=findViewById(R.id.checkout_mail_field);
        phone=findViewById(R.id.checkout_phone_field);

       name_textlay=findViewById(R.id.checkout_name);
       phone_lay=findViewById(R.id.checkout_phone);
       mail_lay=findViewById(R.id.checkout_email);

        name.addTextChangedListener(checkout);
        mail.addTextChangedListener(checkout);
        phone.addTextChangedListener(checkout);

        FirebaseAuth user=FirebaseAuth.getInstance();
        FirebaseFirestore fstore=FirebaseFirestore.getInstance();



        FirebaseAuth auth=FirebaseAuth.getInstance();


        fstore.collection("users").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User use=new User(task.getResult().get("name").toString(),task.getResult().get("email").toString(),task.getResult().get("phone").toString());
                name_textlay.getEditText().setText(use.getName());
                phone_lay.getEditText().setText(use.getPhone());
                mail_lay.getEditText().setText(use.getEmail());
            }
        });




        phone_lay.getEditText().setText(user.getUid());
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth user=FirebaseAuth.getInstance();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference order_ref=database.getReference().child(user.getUid()).getRef();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());

                SimpleDateFormat df = new SimpleDateFormat("h:mm a");
                SimpleDateFormat order_id_1=new SimpleDateFormat("dM");
                SimpleDateFormat order_id_2=new SimpleDateFormat("hmS");
                String orderID=order_id_1.format(Calendar.getInstance().getTime()) + order_id_2.format(Calendar.getInstance().getTime());

                String time=df.format(Calendar.getInstance().getTime());

                Order_client orderClient =new Order_client(date,time,"Awaiting conformation",checkout_price,user.getUid(),orderID);
                order_ref.child("order_Details").child("order_info").setValue(orderClient);
                database.getReference().child("Admin").child("orders").child(user.getUid()).setValue(orderClient);
               order_ref.child("order_Details").child("Cart").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                   @Override
                   public void onComplete(@NonNull  Task<DataSnapshot> task) {


                       ArrayList<String> pizza_names=new ArrayList<>();
                       ArrayList<Double> pizza_prices=new ArrayList<>();
                       ArrayList<Integer> qty_cart= new ArrayList<>();
                       ArrayList<String> size=new ArrayList<>();

                       for(DataSnapshot children: task.getResult().getChildren()){
                           pizza_names.add(children.child("pizza_name").getValue(String.class));
                           pizza_prices.add(children.child("price").getValue(Double.class));
                           qty_cart.add(children.child("qty").getValue(Integer.class));
                           size.add(children.child("size").getValue(String.class));

                       }
                       for(int i=0;i<pizza_names.size();i++)
                       {
                           database.getReference().child("Admin").child("orders").child(user.getUid()).child("Cart").child(pizza_names.get(i)+" "+size.get(i)).
                                   setValue(new Admin_cart(pizza_names.get(i),pizza_prices.get(i),qty_cart.get(i),size.get(i)));
                       }

                       
                   }
               });
               Intent intent;
               intent= new Intent(getApplicationContext(),Order_Track.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);


            }
        });
    }

}