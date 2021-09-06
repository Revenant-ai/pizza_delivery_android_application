package pizza.time.mainScreen.MenuFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Vector;

import pizza.time.Adapters.Myadapter;
import pizza.time.R;



public class Sides extends Fragment {
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        db = FirebaseFirestore.getInstance();

        db.collection("Menu").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Vector<String> pizza_names=new Vector<String>();
                        Vector<String> pizza_descs=new Vector<String>();
                        Vector<Double> pizza_prices=new Vector<Double>();
                        Vector<String> pizza_photo= new Vector<>();
                        Vector<String> pizza_size = new Vector<>();
                        if(task.isSuccessful()){
                            int i=0;
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                if (documentSnapshot.getString("type").equals("sides"))
                                {
                                    pizza_names.add(documentSnapshot.getString("Name"));
                                    pizza_descs.add(documentSnapshot.getString("Desc"));
                                    pizza_prices.add(documentSnapshot.getDouble("Price"));
                                    pizza_photo.add(documentSnapshot.getString("photo"));
                                    pizza_size.add("small");
                                    i++;
                                }
                            }
                            details(pizza_names,pizza_descs,pizza_prices,pizza_photo,pizza_size);
                        }
                        else {
                            Log.d("errorrrrr", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return inflater.inflate(R.layout.fragment_sides, container, false);

    }


    void details(Vector<String> name, Vector<String>  desc, Vector<Double> price,Vector<String> photo,Vector<String> pizza_size)
    {
        RecyclerView menu_recycle= getActivity().findViewById(R.id.recycler_sides_pizza);
        Myadapter myadapter=new Myadapter(getActivity(),name,desc,price,photo,pizza_size);
        menu_recycle.setAdapter(myadapter);
        menu_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));


    }
}