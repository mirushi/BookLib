package luubieunghi.lbn.booklib.Activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import luubieunghi.lbn.booklib.Fragment.AlbumFragment;
import luubieunghi.lbn.booklib.Fragment.BaihatFragment;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.AlbumAdapter;
import luubieunghi.lbn.booklib.adapter.ListSongAdapter;

public class openListSong extends AppCompatActivity {

    private Toolbar toolbar_listsong;
    private TabLayout tabLayout_ListSong;
    private ViewPager viewPager_ListSong;
    private ListSongAdapter listSongAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_list_song);
        addControls();
        setUp();
        addEvents();
    }

    private void setUp() {
        setSupportActionBar(toolbar_listsong);
        listSongAdapter=new ListSongAdapter(getSupportFragmentManager());
        listSongAdapter.addFragment(new BaihatFragment(),"Song");
        listSongAdapter.addFragment(new AlbumFragment(),"Album");
        viewPager_ListSong.setAdapter(listSongAdapter);
        tabLayout_ListSong.setupWithViewPager(viewPager_ListSong);
    }

    private void addEvents() {
        tabLayout_ListSong.setSelectedTabIndicatorColor(Color.parseColor("#8c2065"));
        tabLayout_ListSong.setTabTextColors(Color.parseColor("#8c2065"), Color.parseColor("#a6771f"));
    }

    private void addControls() {
        toolbar_listsong=findViewById(R.id.toolbar_listsong);
        tabLayout_ListSong=findViewById(R.id.tablayout_listsong_type);
        viewPager_ListSong=findViewById(R.id.viewpager_listsong);
    }
}
