package luubieunghi.lbn.booklib.UI.OpenAlbum;

import android.content.Context;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Song.Song;

public class OpenAlbumPresenter implements OpenAlbumContract.IOpenAlbumPresenter {

    private Context context;
    private OpenAlbumContract.IOpenAlbumView view;
    private ArrayList<Song> listSong;
    public OpenAlbumPresenter(Context context, OpenAlbumContract.IOpenAlbumView view) {
        this.context = context;
        this.view=view;
        listSong = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            listSong.add(new Song("ID "+i,"Song "+i,"FilePath "+i,0,"ImagePath "+i,"Artist "+i));;
        }
        updateListView();
    }

    public void updateListView(){
        view.updateListView(listSong);
    }

    public void addSong(Song song){
        listSong.add(song);
    }

}
