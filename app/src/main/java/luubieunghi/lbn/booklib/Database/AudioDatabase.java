package luubieunghi.lbn.booklib.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import luubieunghi.lbn.booklib.Model.Album.Album;
import luubieunghi.lbn.booklib.Model.Album.Album_DAO;
import luubieunghi.lbn.booklib.Model.Album_Song.Album_Song;
import luubieunghi.lbn.booklib.Model.Album_Song.Album_Song_DAO;
import luubieunghi.lbn.booklib.Model.Song.Song;
import luubieunghi.lbn.booklib.Model.Song.Song_DAO;

@Database(entities = {Song.class, Album.class, Album_Song.class},version = 1, exportSchema = false)
public abstract class AudioDatabase extends RoomDatabase {

    public  abstract Song_DAO song_dao();
    public  abstract Album_DAO album_dao();
    public  abstract Album_Song_DAO album_song_dao();

}
