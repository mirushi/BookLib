package luubieunghi.lbn.booklib.Model.Album_Song;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Album_Song_DAO {

    @Insert
    void insert(Album_Song album_song);

    @Query("select * from Album_Song")
    List<Album_Song> getAll();

    @Query("select * from Album_Song where AlbumID like :ID")
    List<Album_Song> getByID(String ID);

    @Query("select SongID from Album_Song where AlbumID like :ID")
    List<String> getSongIDByID(String ID);


    @Insert
    void insertAll(Album_Song... album_songs);

    @Update
    void update(Album_Song album_song);

    @Delete
    void delete(Album_Song album_song);

    @Query("delete from Album_Song")
    void deleteAll();

}
