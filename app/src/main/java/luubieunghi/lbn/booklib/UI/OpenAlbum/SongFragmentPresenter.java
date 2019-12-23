package luubieunghi.lbn.booklib.UI.OpenAlbum;

import android.content.Context;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Song.Song;

public class SongFragmentPresenter implements SongFragmentContract.IBaiHatFragmentPresenter {
    private SongFragmentContract.IBaiHatFragmentView view;
    private Context context;
    private ArrayList<Song> listSong;
    public SongFragmentPresenter(Context context, SongFragmentContract.IBaiHatFragmentView view, ArrayList<Song> songs)
    {
        this.context=context;
        this.view=view;
        view.updateListView(songs);
    }
}
