package luubieunghi.lbn.booklib.UI.OpenAlbum;

import android.content.Context;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Adapter.BaiHatAdapter;
import luubieunghi.lbn.booklib.Model.BaiHat;
import luubieunghi.lbn.booklib.R;

public class OpenAlbumPresenter implements OpenAlbumContract.IOpenAlbumPresenter {

    private Context context;
    private OpenAlbumContract.IOpenAlbumView view;
    private ArrayList<BaiHat> dsBaiHat;
    public OpenAlbumPresenter(Context context, OpenAlbumContract.IOpenAlbumView view) {
        this.context = context;
        this.view=view;
        dsBaiHat = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dsBaiHat.add(new BaiHat("Bai hat " + i, "Ca si " + i, 1, 1));
        }
        updateListView();
    }

    public void updateListView(){
        view.updateListView(dsBaiHat);
    }

    public void addSong(BaiHat baiHat){
        dsBaiHat.add(baiHat);
    }

}
