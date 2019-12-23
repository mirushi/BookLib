package luubieunghi.lbn.booklib.UI.OpenAlbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.Database.AudioDatabase;
import luubieunghi.lbn.booklib.Model.Album.Album;
import luubieunghi.lbn.booklib.Model.Album_Song.Album_Song;
import luubieunghi.lbn.booklib.Model.Song.Song;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.BaiHatAdapter;
import luubieunghi.lbn.booklib.UI.PlayMusic.PlayMusic;

public class OpenAlbum extends AppCompatActivity implements AdapterView.OnItemClickListener, OpenAlbumContract.IOpenAlbumView {

    private TextView txt_TenAlbum_DanhSachBaiHat_Album, txt_SoLuongBaiHat_DanhSachBaiHat_Album;
    private ListView lv_DanhSachBaiHat_Album;
    private ArrayList<Song> dsSong;
    private BaiHatAdapter adapter;
    private androidx.appcompat.widget.Toolbar toolbar_OpenAlbum;
    private OpenAlbumPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album);
        AudioDatabase ab=AudioDatabase.getInstance(this);
        Album album=(Album) getIntent().getSerializableExtra("album");
        ArrayList<String> dsIDS=new ArrayList<>();
        dsIDS.addAll(ab.album_song_dao().getSongIDByID(album.getAlbumID()));
        String IDS[]=new String[dsIDS.size()];
        IDS=dsIDS.toArray(IDS);
        dsSong=new ArrayList<>();
        dsSong.addAll(ab.song_dao().getByIDs(IDS));
        addControls();
        addEvents();
        presenter=new OpenAlbumPresenter(OpenAlbum.this,this,dsSong);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.play_music_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void addEvents() {
        lv_DanhSachBaiHat_Album.setOnItemClickListener(this);
        toolbar_OpenAlbum.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar_OpenAlbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void updateListView(ArrayList<Song> baihats) {
        dsSong =baihats;
        adapter=new BaiHatAdapter(OpenAlbum.this,R.layout.item_song, dsSong);
        lv_DanhSachBaiHat_Album.setAdapter(adapter);
    }


    @Override
    public void addControls() {
        toolbar_OpenAlbum=findViewById(R.id.toolbar_open_album);
        txt_SoLuongBaiHat_DanhSachBaiHat_Album=findViewById(R.id.txt_sobaihat_danhsachbaihat_album);
        txt_TenAlbum_DanhSachBaiHat_Album=findViewById(R.id.txt_tenalbum_danhsachbaihat_album);
        lv_DanhSachBaiHat_Album=findViewById(R.id.lv_danhsachbaihat_album);
        setSupportActionBar(toolbar_OpenAlbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(OpenAlbum.this, PlayMusic.class);
        Song s=(Song)lv_DanhSachBaiHat_Album.getItemAtPosition(position);
        intent.putExtra("song",s);
        startActivity(intent);
    }
}
