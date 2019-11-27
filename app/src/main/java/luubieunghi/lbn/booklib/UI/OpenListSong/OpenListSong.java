package luubieunghi.lbn.booklib.UI.OpenListSong;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import luubieunghi.lbn.booklib.UI.OpenAlbum.BaihatFragment;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.ListSongAdapter;

public class OpenListSong extends AppCompatActivity {

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.play_music_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUp() {
        setSupportActionBar(toolbar_listsong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        listSongAdapter=new ListSongAdapter(getSupportFragmentManager());
        listSongAdapter.addFragment(new BaihatFragment(),"Song");
        listSongAdapter.addFragment(new AlbumFragment(),"Album");
        viewPager_ListSong.setAdapter(listSongAdapter);
        tabLayout_ListSong.setupWithViewPager(viewPager_ListSong);
    }

    private void addEvents() {
        toolbar_listsong.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar_listsong.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout_ListSong.setSelectedTabIndicatorColor(Color.parseColor("#8c2065"));
        tabLayout_ListSong.setTabTextColors(Color.parseColor("#8c2065"), Color.parseColor("#a6771f"));
    }

    private void addControls() {
        toolbar_listsong=findViewById(R.id.toolbar_listsong);
        tabLayout_ListSong=findViewById(R.id.tablayout_listsong_type);
        viewPager_ListSong=findViewById(R.id.viewpager_listsong);
    }
}
