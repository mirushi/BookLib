package luubieunghi.lbn.booklib.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

import luubieunghi.lbn.booklib.Fragment.Frm_1;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.ViewPagerAdapter;

public class ReadBookActivity extends AppCompatActivity {
    ViewPager viewPager;
    int scr_height;
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    Frm_1 frm_1;
    Frm_1 frm_2;
    Frm_1 frm_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.read_book_viewpager);
        scr_height = getScreenHeight();//cal scr width, height
        getSupportActionBar().hide(); //hide the title bar
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(viewPager);//setup for tablayout
        addTab(viewPager); //add tab with title and fragment
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hide statusbar
    }

    public void addTab(ViewPager viewPager) {
        frm_1 = new Frm_1();
        frm_2 = new Frm_1();
        frm_3 = new Frm_1();

        adapter.addFragment(frm_1, "ONE");
        adapter.addFragment(frm_2, "TWO");
        adapter.addFragment(frm_3, "THREE");

        viewPager.setAdapter(adapter);
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}


