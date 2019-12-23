package luubieunghi.lbn.booklib.UI.OpenAlbum;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Song.Song;

public class SongFragmentContract {
    interface IBaiHatFragmentView{
        void updateListView(ArrayList<Song> dsSong);
        void addEvents();
        void setUp();

    }
    interface IBaiHatFragmentPresenter{

    }
}
