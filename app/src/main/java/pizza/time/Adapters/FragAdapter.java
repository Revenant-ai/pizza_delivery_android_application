package pizza.time.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FragAdapter extends FragmentPagerAdapter
{

    private final ArrayList<Fragment> frag_array= new ArrayList<>();
    private final ArrayList<String> frag_title= new ArrayList<>();

    public FragAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return frag_array.get(position);
    }

    @Override
    public int getCount() {
        return frag_array.size();
    }


    public void add_fragment(Fragment fragment, String title){
        frag_array.add(fragment);
        frag_title.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return frag_title.get(position);
    }
}
