package luubieunghi.lbn.booklib.UI.OpenListSong;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.Database.AudioDatabase;
import luubieunghi.lbn.booklib.Model.Album.Album;
import luubieunghi.lbn.booklib.Model.Album_Song.Album_Song;

public class AlbumFragmentPresenter implements AlbumFragmentContract.IAlbumFragmentPresenter {
    private Context context;
    private ArrayList<Album> listAlbum=new ArrayList<>();
    private AlbumFragmentContract.IAlbumFragmentView view;
    public AlbumFragmentPresenter(Context context, AlbumFragmentContract.IAlbumFragmentView view){
        this.context=context;
        this.view=view;
        AudioDatabase database=AudioDatabase.getInstance(context);
        //database.album_dao().insert(new Album("AB1","Album 1","/sdcard/Download/BH1.png"));
        //database.album_song_dao().insert(new Album_Song("AB1","BH1"));
        List<Album> abs=database.album_dao().getAll();
        listAlbum.addAll(abs);

    }


    @Override
    public void updateListView() {
        view.updateListView(listAlbum);
    }
}
