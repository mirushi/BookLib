package luubieunghi.lbn.booklib.UI.OpenListSong;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.Model.Album.Album;
import luubieunghi.lbn.booklib.Model.Song.Song;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.ListSongAdapter;

public class OpenListSong extends AppCompatActivity implements OpenListSongContract.IOpenListSongView {

    private Toolbar toolbar_listsong;
    private TabLayout tabLayout_ListSong;
    private ViewPager viewPager_ListSong;
    private ListSongAdapter listSongAdapter;
    private OpenListSongPresenter presenter;
    private SearchView searchView;

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
    public void updateListViewSong(ArrayList<Song> dsBaiHat) {

    }

    @Override
    public void updateListViewAlbum(ArrayList<Album> dsAlbum) {

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewSubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    reset();
                }
                else {
                    searchViewSubmit(newText);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                reset();
                return false;
            }
        });
    }

    private void reset(){
        SongFragment songFragment=(SongFragment)(listSongAdapter.getItem(0));
        songFragment.resetAdapter();
        AlbumFragment albumFragment=(AlbumFragment)(listSongAdapter.getItem(1));
        albumFragment.resetAdapter();
//        switch (tabLayout_ListSong.getSelectedTabPosition()){
//            case 0:{
//
//                break;
//            }
//            case 1:{
//
//                break;
//            }
//            default:
//                break;
//        }
    }

    private void searchViewSubmit(String query){
        switch (tabLayout_ListSong.getSelectedTabPosition()){
            case 0:{
                SongFragment songFragment=(SongFragment)(listSongAdapter.getItem(0));
                ArrayList<Song> dsBaiHat=songFragment.getArrayListSong();
                for(Song s:dsBaiHat){
                    if(!s.getSongName().contains(query)){
                        dsBaiHat.remove(s);
                    }
                }
                updateListViewSong(dsBaiHat);
                break;
            }
            case 1:{
                AlbumFragment albumFragment=(AlbumFragment)(listSongAdapter.getItem(1));
                ArrayList<Album> dsAlbum=albumFragment.getArrayListAlbum();
                for(Album ab:dsAlbum){
                    if(!ab.getAlbumName().contains(query)){
                        dsAlbum.remove(ab);
                    }
                }
                updateListViewAlbum(dsAlbum);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void addControls() {
        toolbar_listsong=findViewById(R.id.toolbar_listsong);
        tabLayout_ListSong=findViewById(R.id.tablayout_listsong_type);
        viewPager_ListSong=findViewById(R.id.viewpager_listsong);
        searchView=findViewById(R.id.sv_open_list_song);
    }
}
