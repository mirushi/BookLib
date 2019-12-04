package luubieunghi.lbn.booklib.UI.OpenAlbum;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Song.Song;

public class OpenAlbumContract {
    interface IOpenAlbumView{
        void addControls();
        void addEvents();
        void updateListView(ArrayList<Song> baihats);
    }

    interface  IOpenAlbumPresenter{
        void updateListView();
        void addSong(Song song);
    }
}
