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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.UI.OpenAlbum.OpenAlbum;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.AlbumAdapter;
import luubieunghi.lbn.booklib.Model.Album.Album;

public class  AlbumFragment extends Fragment implements AdapterView.OnItemClickListener, AlbumFragmentContract.IAlbumFragmentView {

    View view;
    private ListView lv_DanhSachAlbum;
    private ArrayList<Album> dsAlbum;
    private AlbumAdapter adapter;
    private AlbumFragmentPresenter presenter;
    private Context context;
    public  AlbumFragment(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_album,container,false);
        lv_DanhSachAlbum=view.findViewById(R.id.lv_danhSachAlbum);
        dsAlbum=new ArrayList<>();
        presenter=new AlbumFragmentPresenter(view.getContext(),this);
        updateListView(dsAlbum);
        presenter.updateListView();
        lv_DanhSachAlbum.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getContext(), OpenAlbum.class);
        Album ab=(Album) lv_DanhSachAlbum.getItemAtPosition(position);
        intent.putExtra("album",ab);
        startActivity(intent);
    }

    @Override
    public void addEvents() {

    }

    @Override
    public void setUp() {

    }

    @Override
    public void updateListView(ArrayList<Album> albums) {
        dsAlbum=albums;
        adapter=new AlbumAdapter((Activity) context,R.layout.item_album,dsAlbum);
        lv_DanhSachAlbum.setAdapter(adapter);
    }


}
