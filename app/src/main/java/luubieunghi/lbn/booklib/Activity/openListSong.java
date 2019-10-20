package luubieunghi.lbn.booklib.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.BaiHatAdapter;
import luubieunghi.lbn.booklib.model.BaiHat;

public class openListSong extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ListView lv_DanhSachBaiHat;
    private ArrayList<BaiHat> arrayList;
    private BaiHatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_list_song);
        initListView();

    }

    private void initListView() {
        lv_DanhSachBaiHat=findViewById(R.id.lv_danhSachBaiHat);
        arrayList=new ArrayList<>();
        for(int i=0;i<50;i++){
            arrayList.add(new BaiHat("Hình "+i,"Bài hát "+i,"Ca sĩ "+i));
        }
        adapter=new BaiHatAdapter(openListSong.this,R.layout.layout_song,arrayList);
        lv_DanhSachBaiHat.setAdapter(adapter);
        lv_DanhSachBaiHat.setOnItemClickListener(openListSong.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(openListSong.this, MainActivity.class);
        startActivity(intent);
    }
}
