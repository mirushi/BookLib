package luubieunghi.lbn.booklib.Activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import luubieunghi.lbn.booklib.Fragment.BookReadingFirstPageFrm;
import luubieunghi.lbn.booklib.Fragment.BookReadingSecondPageFrm;
import luubieunghi.lbn.booklib.Fragment.BookReadingThirdPageFrm;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.ViewPagerAdapter;

public class ReadBookActivity extends AppCompatActivity {
    ViewPager viewPager;
    int scr_height;
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    Fragment frm_1;
    BookReadingSecondPageFrm frm_2;
    BookReadingThirdPageFrm frm_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.text_view_zoom_out, R.anim.text_view_zoom_in);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        initiateValue();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hide statusbar
    }

    private void initiateValue() {

        frm_1 = new BookReadingFirstPageFrm();

        frm_2 = new BookReadingSecondPageFrm();
        frm_3 = new BookReadingThirdPageFrm();
        viewPager = findViewById(R.id.read_book_viewpager);
        scr_height = getScreenHeight();//cal scr width, height
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(viewPager);//setup for tablayout
        addTab(viewPager); //add tab with title and fragment
    }

    public void addTab(ViewPager viewPager) {

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


