package luubieunghi.lbn.booklib.UI.OpenListSong;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.Database.AudioDatabase;
import luubieunghi.lbn.booklib.Model.Album.Album;

public class AlbumFragmentPresenter implements AlbumFragmentContract.IAlbumFragmentPresenter {
    private Context context;
    private ArrayList<Album> listAlbum=new ArrayList<>();
    private AlbumFragmentContract.IAlbumFragmentView view;
    public AlbumFragmentPresenter(Context context, AlbumFragmentContract.IAlbumFragmentView view){
        this.context=context;
        this.view=view;
        AudioDatabase database=AudioDatabase.getInstance(context);
        List<Album> abs=database.album_dao().getAll();
        listAlbum.addAll(abs);

    }


    @Override
    public void updateListView() {
        view.updateListView(listAlbum);
    }
}
