package luubieunghi.lbn.booklib.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import luubieunghi.lbn.booklib.Fragment.Listsong_Fragment;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.ListSongAdapter;

public class openListSong extends AppCompatActivity {

    Toolbar toolbar_listsong;
    TabLayout tabLayout_ListSong;
    ViewPager viewPager_ListSong;
    ListSongAdapter adapter;

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
        adapter=new ListSongAdapter(getSupportFragmentManager());
        adapter.addFragment(new Listsong_Fragment(),"Album");
        adapter.addFragment(new Listsong_Fragment(),"Singer");
        viewPager_ListSong.setAdapter(adapter);
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
