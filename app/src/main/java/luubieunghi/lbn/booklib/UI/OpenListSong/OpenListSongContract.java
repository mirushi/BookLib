package luubieunghi.lbn.booklib.UI.OpenListSong;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Album.Album;
import luubieunghi.lbn.booklib.Model.Song.Song;

public class OpenListSongContract {
    interface IOpenListSongView{
        void addControls();
        void addEvents();
        void setUp();
        void updateListViewSong(ArrayList<Song> dsBaiHat);
        void updateListViewAlbum(ArrayList<Album> dsAlbum);
    }

    interface IOpenListSongPresenter{

    }
}
