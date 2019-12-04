package luubieunghi.lbn.booklib.UI.OpenListSong;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Album.Album;

public class AlbumFragmentContract {
    interface  IAlbumFragmentView{
        void addEvents();
        void setUp();
        void updateListView(ArrayList<Album> albums);
    }
    interface IAlbumFragmentPresenter{
        void updateListView();
    }
}
