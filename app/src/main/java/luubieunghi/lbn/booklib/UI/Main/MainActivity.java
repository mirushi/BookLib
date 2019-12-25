package luubieunghi.lbn.booklib.UI.Main;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import luubieunghi.lbn.booklib.Adapter.BookListingPagerAdapter;
import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.About.AboutActivity;
import luubieunghi.lbn.booklib.UI.AddNewBook.AddNewBookActivity;
import luubieunghi.lbn.booklib.UI.PlayMusic.PlayMusic;
import luubieunghi.lbn.booklib.UI.Setting.SettingsActivity;

public class MainActivity extends AppCompatActivity implements PickiTCallbacks {

    //Request code cho việc nhận thông tin đóng mở file.
    private final int requestCodeEbook = 311;

    private final int requestCodeAudioBook = 201;

    private boolean storagePermissionGranted = false;

    private Toolbar toolbar;
    private FragmentPagerAdapter adapterViewPager;
    private ViewPager viewPager;
    //Các biến liên quan đến Drawer.
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    PickiT pickiT;

    //ArrayList giữ các URI khi người dùng lựa chọn.
    ArrayList<Uri> uriOfChoosenFiles;
    //ArrayList giữ các String dẫn đến đường dẫn thực khi người dùng chọn file.
    ArrayList<String> pathOfChoosenFiles;

    String bookType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfigToolbar();
        ConfigViewPager();
        ConfigDrawerLayout();
        ConfigGesturesListener();
        pickiT = new PickiT(this,this);
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

        switch(requestCode){
            //Xử lý việc chọn file sách.
            case requestCodeEbook:{
                if (resultCode == RESULT_OK && data != null){
                    uriOfChoosenFiles = new ArrayList<>();
                    pathOfChoosenFiles = new ArrayList<>();

                    //Nếu người dùng không chọn nhiều file thì chỉ lấy thông tin 1 file duy nhất.
                    if (data.getClipData() == null)
                        uriOfChoosenFiles.add(data.getData());
                    else{
                        int itemCount = data.getClipData().getItemCount();
                        for (int i =0;i<itemCount;++i){
                            Uri filePath = data.getClipData().getItemAt(i).getUri();
                            uriOfChoosenFiles.add(filePath);
                        }
                    }
                    bookType = "EBOOK";
                    for (Uri file : uriOfChoosenFiles){
                        pickiT.getPath(file, Build.VERSION.SDK_INT);
                    }

                }
                break;
            }
            //Xử lý việc chọn file audio book.
            case requestCodeAudioBook:{
                if (resultCode == Activity.RESULT_OK && data != null){
                    Intent intentGoToAddNewBook = new Intent(MainActivity.this, AddNewBookActivity.class);
                    uriOfChoosenFiles = new ArrayList<>();
                    pathOfChoosenFiles = new ArrayList<>();

                    //Nếu người dùng không chọn nhiều file thì chỉ lấy thông tin 1 file duy nhất.
                    if (data.getClipData() == null)
                        uriOfChoosenFiles.add(data.getData());
                    else{
                        int itemCount = data.getClipData().getItemCount();
                        for (int i =0;i<itemCount;++i){
                            Uri filePath = data.getClipData().getItemAt(i).getUri();
                            uriOfChoosenFiles.add(filePath);
                        }
                    }
                    bookType = "AUDIO_BOOK";
                    for (Uri file : uriOfChoosenFiles){
                        pickiT.getPath(file, Build.VERSION.SDK_INT);
                    }
                }
                break;
            }

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

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.EbookTypesSelectorDialog);
                        builder.setTitle("Chọn loại sách");

                        //Danh sách.
                        String[] bookTypes = {"Ebook", "Audio Book"};

                        builder.setItems(bookTypes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                    {
                                        //Nếu chọn Ebook thì ta mở cho chọn 1 file thôi.
                                        Intent intentOpenContentUI = new Intent().setType("application/epub+zip").setAction(Intent.ACTION_GET_CONTENT);
                                        intentOpenContentUI.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                                        startActivityForResult(Intent.createChooser(intentOpenContentUI, "Select a epub book"), requestCodeEbook);
                                        break;
                                    }
                                    case 1:
                                    {
                                        Intent intentOpenContentUI = new Intent().setType("audio/mpeg").setAction(Intent.ACTION_GET_CONTENT);
                                        intentOpenContentUI.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                        startActivityForResult(Intent.createChooser(intentOpenContentUI, "Select audio book files"), requestCodeAudioBook);
                                        break;
                                    }
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
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

    private synchronized void AddFilePath(String filePath){
        //Khi đã dịch đủ URI sang RealPath rồi thì ta tiến hành chạy activity.
        pathOfChoosenFiles.add(filePath);
        if (pathOfChoosenFiles.size() >= uriOfChoosenFiles.size()){
            Intent intentGoToAddNewBook = new Intent(MainActivity.this, AddNewBookActivity.class);
            intentGoToAddNewBook.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //Thêm URI vào nữa chứ.
            intentGoToAddNewBook.putStringArrayListExtra("EXTRA_BOOK_URI", pathOfChoosenFiles);
            intentGoToAddNewBook.putExtra("BOOK_TYPE", bookType);

            startActivity(intentGoToAddNewBook);
        }
    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        Log.d("REAL_PATH_FROM_PICKIT", path);
        AddFilePath(path);
    }
}
