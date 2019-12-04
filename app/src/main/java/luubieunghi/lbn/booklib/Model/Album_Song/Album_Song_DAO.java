package luubieunghi.lbn.booklib.Model.Album_Song;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface Album_Song_DAO {

    @Insert
    void insert(Album_Song album_song);

    @Insert
    void insertAll(Album_Song... album_songs);

    @Update
    void update(Album_Song album_song);

    @Delete
    void delete(Album_Song album_song);

}
