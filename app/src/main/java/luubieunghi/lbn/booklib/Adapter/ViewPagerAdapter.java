package luubieunghi.lbn.booklib.Adapter;

import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    public ArrayList<String> fragmentTitle = new ArrayList<String>();
    public ArrayList<TextView> fragmentTextView = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    public void addFragment(Fragment frm, String title) {
        fragmentList.add(frm);
        fragmentTitle.add(title);
    }

}
