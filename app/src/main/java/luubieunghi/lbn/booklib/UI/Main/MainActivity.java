package luubieunghi.lbn.booklib.UI.Main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.BookListingPagerAdapter;
import luubieunghi.lbn.booklib.UI.About.AboutActivity;
import luubieunghi.lbn.booklib.UI.AddNewBook.AddNewBookActivity;
import luubieunghi.lbn.booklib.UI.PlayMusic.PlayMusic;
import luubieunghi.lbn.booklib.UI.Setting.SettingsActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentPagerAdapter adapterViewPager;
    private ViewPager viewPager;
    //Các biến liên quan đến Drawer.
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    //Request code cho việc nhận thông tin đóng mở file.
    private final int contentActRequestCode = 311;

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
                 intent.putExtra("ID", "BH1");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Xử lý việc chọn file sách.
        if (requestCode == contentActRequestCode && resultCode == RESULT_OK){
            Uri selectedFile = data.getData();
            Intent intentGoToAddNewBook = new Intent(MainActivity.this, AddNewBookActivity.class);
            //Thêm URI vào nữa chứ.
            intentGoToAddNewBook.putExtra("EXTRA_BOOK_URI", selectedFile);
            startActivity(intentGoToAddNewBook);
        }
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
                switch (item.getItemId())
                {
                    case R.id.menu_item_about:
                    {
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.menu_main_item_add_book:
                    {
                        Intent intentOpenContentUI = new Intent().setType("application/epub+zip").setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intentOpenContentUI, "Select a epub book"), contentActRequestCode);
                        break;
                    }
                    case R.id.menu_item_settings:
                    {
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    }
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
