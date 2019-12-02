package luubieunghi.lbn.booklib.UI.OpenAlbum;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.BaiHat;

public class OpenAlbumContract {
    interface IOpenAlbumView{
        void addControls();
        void addEvents();
        void updateListView(ArrayList<BaiHat> baihats);
    }

    interface  IOpenAlbumPresenter{
        void updateListView();
        void addSong(BaiHat baiHat);
    }
}
