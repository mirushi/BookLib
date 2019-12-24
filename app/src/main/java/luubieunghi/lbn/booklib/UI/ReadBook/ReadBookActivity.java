package luubieunghi.lbn.booklib.UI.ReadBook;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import luubieunghi.lbn.booklib.Adapter.ViewPagerAdapter;
import luubieunghi.lbn.booklib.R;

public class ReadBookActivity extends AppCompatActivity implements ReadBookContract.ReadBookView {
    ViewPager viewPager;
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    Fragment frm_1;
    BookReadingSecondPageFrm frm_2;
    BookReadingThirdPageFrm frm_3;

    ReadBookPresenter readBookPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.text_view_zoom_out, R.anim.text_view_zoom_in);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        generalSetUp();
        setUpViews();
        this.readBookPresenter = new ReadBookPresenter(this);
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setUpViews() {
        //set up pages for viewpager
        frm_1 = new BookReadingFirstPageFrm();
        frm_2 = new BookReadingSecondPageFrm();
        frm_3 = new BookReadingThirdPageFrm();
        viewPager = findViewById(R.id.read_book_viewpager);
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(viewPager);//setup for tablayout
        addTab(viewPager); //add tab with title and fragment

    }

    @Override
    public void generalSetUp() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hide statusbar
    }

    public void addTab(ViewPager viewPager) {
        adapter.addFragment(frm_1, "ONE");
        adapter.addFragment(frm_2, "TWO");
        adapter.addFragment(frm_3, "THREE");
        viewPager.setAdapter(adapter);
    }
}


