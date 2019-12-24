package luubieunghi.lbn.booklib.UI.OpenListSong;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import luubieunghi.lbn.booklib.Adapter.ListSongAdapter;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.OpenAlbum.SongFragment;

public class OpenListSong extends AppCompatActivity implements OpenListSongContract.IOpenListSongView {

    private Toolbar toolbar_listsong;
    private TabLayout tabLayout_ListSong;
    private ViewPager viewPager_ListSong;
    private ListSongAdapter listSongAdapter;
    private OpenListSongPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_list_song);
        addControls();
        setUp();
        addEvents();
        presenter=new OpenListSongPresenter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.play_music_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setUp() {
        setSupportActionBar(toolbar_listsong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        listSongAdapter=new ListSongAdapter(getSupportFragmentManager());
        listSongAdapter.addFragment(new SongFragment(this),"Bài Hát");
        listSongAdapter.addFragment(new AlbumFragment(this),"Album");
        viewPager_ListSong.setAdapter(listSongAdapter);
        tabLayout_ListSong.setupWithViewPager(viewPager_ListSong);
    }

    @Override
    public void addEvents() {
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

    @Override
    public void addControls() {
        toolbar_listsong=findViewById(R.id.toolbar_listsong);
        tabLayout_ListSong=findViewById(R.id.tablayout_listsong_type);
        viewPager_ListSong=findViewById(R.id.viewpager_listsong);
    }
}
