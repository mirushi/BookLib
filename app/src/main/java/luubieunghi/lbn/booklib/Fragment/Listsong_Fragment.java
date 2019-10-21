package luubieunghi.lbn.booklib.Fragment;

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

import luubieunghi.lbn.booklib.Activity.MainActivity;
import luubieunghi.lbn.booklib.Activity.PlayMusic;
import luubieunghi.lbn.booklib.Activity.openListSong;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.BaiHatAdapter;
import luubieunghi.lbn.booklib.model.BaiHat;

public class Listsong_Fragment extends Fragment implements AdapterView.OnItemClickListener {

    View view;
    private ListView lv_DanhSachBaiHat;
    private ArrayList<BaiHat> arrayList;
    private BaiHatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_listsong,container,false);
        lv_DanhSachBaiHat=view.findViewById(R.id.lv_danhSachBaiHat);
        arrayList=new ArrayList<>();
        for(int i=0;i<50;i++){
            arrayList.add(new BaiHat("Hình "+i,"Bài hát "+i,"Ca sĩ "+i));
        }
        adapter=new BaiHatAdapter(getActivity(),R.layout.item_song,arrayList);
        lv_DanhSachBaiHat.setAdapter(adapter);
        lv_DanhSachBaiHat.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), PlayMusic.class);
        startActivity(intent);
    }
}
