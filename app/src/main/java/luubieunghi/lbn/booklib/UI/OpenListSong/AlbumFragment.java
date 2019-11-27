package luubieunghi.lbn.booklib.UI.OpenListSong;

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

import luubieunghi.lbn.booklib.UI.OpenAlbum.OpenAlbum;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.AlbumAdapter;
import luubieunghi.lbn.booklib.Model.Album;
import luubieunghi.lbn.booklib.Model.BaiHat;

public class  AlbumFragment extends Fragment implements AdapterView.OnItemClickListener {

    View view;
    private ListView lv_DanhSachAlbum;
    private ArrayList<Album> dsAlbum;
    private AlbumAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_album,container,false);
        lv_DanhSachAlbum=view.findViewById(R.id.lv_danhSachAlbum);
        dsAlbum=new ArrayList<>();
        for(int i=0;i<50;i++){
            dsAlbum.add(new Album("Album"+i,new ArrayList<BaiHat>()));
        }
        adapter=new AlbumAdapter(getActivity(),R.layout.item_album,dsAlbum);
        lv_DanhSachAlbum.setAdapter(adapter);
        lv_DanhSachAlbum.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), OpenAlbum.class);
        getActivity().finish();
        startActivity(intent);
    }
}
