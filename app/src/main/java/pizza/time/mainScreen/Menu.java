 package pizza.time.mainScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import pizza.time.Adapters.FragAdapter;
import pizza.time.Adapters.Myadapter;
import pizza.time.R;
import pizza.time.mainScreen.MenuFragments.Beverages;
import pizza.time.mainScreen.MenuFragments.Dessert;
import pizza.time.mainScreen.MenuFragments.New_Launches;
import pizza.time.mainScreen.MenuFragments.Sides;
import pizza.time.mainScreen.MenuFragments.Veg_Pizza;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Vector;


 public class Menu extends AppCompatActivity {


    FirebaseFirestore db;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.menu_view);

        tabLayout.setupWithViewPager(viewPager);

        FragAdapter frag_adap=new FragAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        frag_adap.add_fragment(new Veg_Pizza(),"Veg Pizza ");
        frag_adap.add_fragment(new New_Launches(),"New Launches");
        frag_adap.add_fragment(new Sides(),"Sides");
        frag_adap.add_fragment(new Dessert(),"Dessert");
        frag_adap.add_fragment(new Beverages(), "Beverages");
        viewPager.setAdapter(frag_adap);

        Bundle bundle=getIntent().getExtras();
        String intentFragment=bundle.getString("Screen");

        if(intentFragment.equals("pizza")) viewPager.setCurrentItem(0);

        else if (intentFragment.equals("sides")) viewPager.setCurrentItem(2);

        else if(intentFragment.equals("drinks")) viewPager.setCurrentItem(4);

        else viewPager.setCurrentItem(3);
    }

 }
