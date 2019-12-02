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

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.BaiHatAdapter;
import luubieunghi.lbn.booklib.Model.BaiHat;
import luubieunghi.lbn.booklib.UI.PlayMusic.PlayMusic;

public class OpenAlbum extends AppCompatActivity implements AdapterView.OnItemClickListener, OpenAlbumContract.IOpenAlbumView {

    private TextView txt_TenAlbum_DanhSachBaiHat_Album, txt_SoLuongBaiHat_DanhSachBaiHat_Album;
    private ListView lv_DanhSachBaiHat_Album;
    private ArrayList<BaiHat> dsBaiHat;
    private BaiHatAdapter adapter;
    private androidx.appcompat.widget.Toolbar toolbar_OpenAlbum;
    private OpenAlbumPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album);
        addControls();
        addEvents();
        presenter=new OpenAlbumPresenter(OpenAlbum.this,this);
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
    public void updateListView(ArrayList<BaiHat> baihats) {
        dsBaiHat=baihats;
        adapter=new BaiHatAdapter(OpenAlbum.this,R.layout.item_song,dsBaiHat);
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
        startActivity(intent);
    }
}
