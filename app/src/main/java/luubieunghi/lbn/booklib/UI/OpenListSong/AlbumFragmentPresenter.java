package luubieunghi.lbn.booklib.UI.OpenListSong;

import android.content.Context;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Album.Album;

public class AlbumFragmentPresenter implements AlbumFragmentContract.IAlbumFragmentPresenter {
    private Context context;
    private ArrayList<Album> listAlbum=new ArrayList<>();
    private AlbumFragmentContract.IAlbumFragmentView view;
    public AlbumFragmentPresenter(Context context, AlbumFragmentContract.IAlbumFragmentView view){
        this.context=context;
        this.view=view;
        for(int i=0;i<50;i++){
            listAlbum.add(new Album("ID "+i,"AlbumName "+i,"Album Image "+i));
        }
    }


    @Override
    public void updateListView() {
        view.updateListView(listAlbum);
    }
}