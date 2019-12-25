package luubieunghi.lbn.booklib.UI.OpenAlbum;

import android.content.Context;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Song.Song;

public class OpenAlbumPresenter implements OpenAlbumContract.IOpenAlbumPresenter {

    private Context context;
    private OpenAlbumContract.IOpenAlbumView view;
    public OpenAlbumPresenter(Context context, OpenAlbumContract.IOpenAlbumView view) {
        this.context = context;
        this.view=view;
    }
    

}
