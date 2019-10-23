package luubieunghi.lbn.booklib.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Fragment.Frm_1;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<Frm_1> fragmentList = new ArrayList<Frm_1>();
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

    public void addFragment(Frm_1 frm, String title) {
        fragmentList.add(frm);
        fragmentTitle.add(title);
    }

}
