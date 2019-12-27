package luubieunghi.lbn.booklib.UI.OpenListSong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import luubieunghi.lbn.booklib.Database.AudioDatabase;
import luubieunghi.lbn.booklib.UI.PlayMusic.PlayMusic;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.BaiHatAdapter;
import luubieunghi.lbn.booklib.Model.Song.Song;

public class SongFragment extends Fragment implements AdapterView.OnItemClickListener, SongFragmentContract.IBaiHatFragmentView {


    View view;
    private ListView lv_DanhSachBaiHat;
    private ArrayList<Song> dsSong;
    private BaiHatAdapter adapter;
    private Context context;
    private SongFragmentPresenter presenter;

    public SongFragment(Context context){
        this.context=context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_baihat,container,false);
        lv_DanhSachBaiHat=view.findViewById(R.id.lv_danhSachBaiHat);
        resetAdapter();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(context, PlayMusic.class);
        Song song=(Song)lv_DanhSachBaiHat.getItemAtPosition(position);
        intent.putExtra("isMusic",true);
        intent.putExtra("song",song);
        startActivity(intent);
    }

    @Override
    public void updateListView(ArrayList<Song> dsSong) {
        this.dsSong = dsSong;
        adapter=new BaiHatAdapter((Activity) context,R.layout.item_song, dsSong);
        lv_DanhSachBaiHat.setAdapter(adapter);
    }

    @Override
    public void addEvents() {

    }

    @Override
    public void setUp() {

    }

    @Override
    public ArrayList<Song> getArrayListSong() {
        return dsSong;
    }

    @Override
    public void resetAdapter() {
        dsSong=new ArrayList<>();
        dsSong.addAll(AudioDatabase.getInstance(view.getContext()).song_dao().getAll());
        presenter=new SongFragmentPresenter(context,this,dsSong);
        lv_DanhSachBaiHat.setOnItemClickListener(this::onItemClick);
    }
}
