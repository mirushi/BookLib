package luubieunghi.lbn.booklib.Model.Album_Song;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

public interface Album_Song_DAO {

    @Insert
    boolean insert(Album_Song album_song);

    @Insert
    boolean insertAll(Album_Song... album_songs);

    @Update
    boolean update(Album_Song album_song);

    @Delete
    boolean delete(Album_Song album_song);

}
