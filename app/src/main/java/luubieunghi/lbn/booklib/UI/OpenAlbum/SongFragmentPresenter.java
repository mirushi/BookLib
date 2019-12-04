package luubieunghi.lbn.booklib.UI.OpenAlbum;

import android.content.Context;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Song.Song;

public class SongFragmentPresenter implements SongFragmentContract.IBaiHatFragmentPresenter {
    private SongFragmentContract.IBaiHatFragmentView view;
    private Context context;
    private ArrayList<Song> listSong;
    public SongFragmentPresenter(Context context, SongFragmentContract.IBaiHatFragmentView view)
    {
        this.context=context;
        this.view=view;
        listSong =new ArrayList<>();
        for(int i=0;i<50;i++){
            listSong.add(new Song("ID "+i,"Song "+i,"FilePath "+i,0,"ImagePath "+i,"Artist "+i));
        }
    }

    @Override
    public void updateListView() {
        view.updateListView(listSong);
    }
}
