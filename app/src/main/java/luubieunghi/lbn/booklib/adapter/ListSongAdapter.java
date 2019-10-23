package luubieunghi.lbn.booklib.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ListSongAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> dsFgarment=new ArrayList<>();
    ArrayList<String> dsTitle=new ArrayList<>();

    public ListSongAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return dsFgarment.get(position);
    }

    @Override
    public int getCount() {
        return dsFgarment.size();
    }

    public void addFragment(Fragment fragment, String title){
        dsFgarment.add(fragment);
        dsTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return dsTitle.get(position);
    }
}
