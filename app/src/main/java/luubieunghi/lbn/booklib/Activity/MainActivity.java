package luubieunghi.lbn.booklib.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.BookListingPagerAdapter;
import luubieunghi.lbn.booklib.service.AppWideGesturesListener;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentPagerAdapter adapterViewPager;
    private ViewPager viewPager;
    //Các biến liên quan đến Drawer.
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfigToolbar();
        ConfigViewPager();
        ConfigDrawerLayout();
        ConfigGesturesListener();
    }

    private void ConfigGesturesListener()
    {
        viewPager.setOnTouchListener(new AppWideGesturesListener(getApplicationContext()){
            @Override
            public void SwipeUpFromBottom()
            {
                 Intent intent = new Intent(MainActivity.this, PlayMusic.class);
                 startActivity(intent);
                 overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
            }
        });
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        //Đồng bộ trạng thái sau khi diễn ra onRestoreInstanceState
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void ConfigToolbar()
    {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BookLib");
        toolbar.setSubtitle("Ứng dụng hỗ trợ quản lý ebook toàn diện");
        toolbar.inflateMenu(R.menu.menu_main);

        //Đi đến Activities giới thiệu sản phẩm.
        toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_item_about)
                {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void ConfigViewPager()
    {
        viewPager = (ViewPager)findViewById(R.id.activity_main_viewPager);

        //Set layout cho ViewPager.
        adapterViewPager = new BookListingPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        //Xâu chuỗi TabLayout với ViewPager.
        TabLayout tabLayout = (TabLayout)findViewById(R.id.activity_main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void ConfigDrawerLayout()
    {
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();
    }
}
