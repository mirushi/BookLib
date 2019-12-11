package luubieunghi.lbn.booklib.Model.Song;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Song_DAO {

    @Query("select * from Song")
    List<Song> getAll();

    @Query("select * from Song where SongID in (:IDS)")
    List<Song> getByIDs(String ...IDS);

    @Query("select * from Song where SongID like :ID")
    Song findByID(String ID);

    @Insert
    void insert(Song song);

    @Insert
    void insertAll(Song... songs);

    @Update
    int update(Song song);

    @Query("update Song set SongName = :songName, FilePath = :filePath, Duration = :duration, ImagePath = :imagePath, ArtistNames = :artistNames where SongID = :songID")
    int updateByID(String songID, String songName, String filePath, int duration, String imagePath, String artistNames);


    @Delete
    int delete(Song song);

    @Query("delete from Song")
    int deleteAll();

    @Query("delete from Song where SongID like :songID")
    int deleteByID(String songID);
}
