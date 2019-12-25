package luubieunghi.lbn.booklib.UI.OpenListSong;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Album.Album;

public class AlbumFragmentContract {
    interface  IAlbumFragmentView{
        void addEvents();
        void setUp();
        void updateListView(ArrayList<Album> albums);
        ArrayList<Album> getArrayListAlbum();
        void resetAdapter();
    }
    interface IAlbumFragmentPresenter{
        void updateListView();
    }
}
