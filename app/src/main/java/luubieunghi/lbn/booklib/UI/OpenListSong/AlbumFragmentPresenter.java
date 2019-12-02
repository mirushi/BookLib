package luubieunghi.lbn.booklib.UI.OpenListSong;

import android.content.Context;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Album;
import luubieunghi.lbn.booklib.Model.BaiHat;

public class AlbumFragmentPresenter implements AlbumFragmentContract.IAlbumFragmentPresenter {
    private Context context;
    private ArrayList<Album> dsAlbum=new ArrayList<>();
    private AlbumFragmentContract.IAlbumFragmentView view;
    public AlbumFragmentPresenter(Context context, AlbumFragmentContract.IAlbumFragmentView view){
        this.context=context;
        this.view=view;
        for(int i=0;i<50;i++){
            dsAlbum.add(new Album("Album "+i,new ArrayList<BaiHat>()));
        }
    }


    @Override
    public void updateListView() {
        view.updateListView(dsAlbum);
    }
}
