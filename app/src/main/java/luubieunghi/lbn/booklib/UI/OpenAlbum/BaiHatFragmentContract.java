package luubieunghi.lbn.booklib.UI.OpenAlbum;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.BaiHat;

public class BaiHatFragmentContract {
    interface IBaiHatFragmentView{
        void updateListView(ArrayList<BaiHat> dsBaiHat);
        void addEvents();
        void setUp();

    }
    interface IBaiHatFragmentPresenter{

        void updateListView();
    }
}
