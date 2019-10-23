package luubieunghi.lbn.booklib.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.BaiHatAdapter;
import luubieunghi.lbn.booklib.model.BaiHat;

public class OpenAlbum extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView txt_TenAlbum_DanhSachBaiHat_Album, txt_SoLuongBaiHat_DanhSachBaiHat_Album;
    private ListView lv_DanhSachBaiHat_Album;
    private ArrayList<BaiHat> dsBaiHat;
    private BaiHatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album);
        addControls();
        setUp();
        addEvents();
    }

    private void setUp() {
        dsBaiHat=new ArrayList<>();
        // copy ArrayList trong intent rồi gán cho dsBaiHat
        //giả lập
        for(int i=0;i<50;i++){
            dsBaiHat.add(new BaiHat("Bài hát "+i,"Ca sĩ "+i,i,i));
        }
        adapter=new BaiHatAdapter(OpenAlbum.this,R.layout.item_song,dsBaiHat);
        lv_DanhSachBaiHat_Album.setAdapter(adapter);
    }

    private void addEvents() {
        lv_DanhSachBaiHat_Album.setOnItemClickListener(this);
    }

    private void addControls() {
        txt_SoLuongBaiHat_DanhSachBaiHat_Album=findViewById(R.id.txt_sobaihat_danhsachbaihat_album);
        txt_TenAlbum_DanhSachBaiHat_Album=findViewById(R.id.txt_tenalbum_danhsachbaihat_album);
        lv_DanhSachBaiHat_Album=findViewById(R.id.lv_danhsachbaihat_album);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(OpenAlbum.this,PlayMusic.class);
        startActivity(intent);
    }
}
