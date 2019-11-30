package luubieunghi.lbn.booklib.UI.OpenAlbum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.UI.PlayMusic.PlayMusic;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.BaiHatAdapter;
import luubieunghi.lbn.booklib.Model.BaiHat;

public class  BaihatFragment extends Fragment implements AdapterView.OnItemClickListener {


    View view;
    private ListView lv_DanhSachBaiHat;
    private ArrayList<BaiHat> dsBaiHat;
    private BaiHatAdapter adapter;
    private Context context;

    public BaihatFragment(Context context){
        this.context=context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_baihat,container,false);
        lv_DanhSachBaiHat=view.findViewById(R.id.lv_danhSachBaiHat);
        dsBaiHat=new ArrayList<>();
        for(int i=0;i<50;i++){
            dsBaiHat.add(new BaiHat("Bài hát "+i,"Ca sĩ "+i,i,i));
        }
        adapter=new BaiHatAdapter((Activity) context,R.layout.item_song,dsBaiHat);
        lv_DanhSachBaiHat.setAdapter(adapter);
        lv_DanhSachBaiHat.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(context, PlayMusic.class);
        startActivity(intent);
    }
}
