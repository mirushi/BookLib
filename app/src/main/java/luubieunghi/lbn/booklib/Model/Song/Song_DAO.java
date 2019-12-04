package luubieunghi.lbn.booklib.Model.Song;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface Song_DAO {

    @Query("select * from Song")
    List<Song> getAll();

    @Query("select * from Song where SongID in (:IDS)")
    List<Song> getByIDs(String ...IDS);

    @Query("select * from Song where SongID like :ID")
    Song findByID(String ID);

    @Insert
    boolean insert(Song song);

    @Insert
    boolean insertAll(Song... songs);

    @Update
    boolean update(Song song);

    @Query("update Song set SongName = :songName, FilePath = :filePath, Duration = :duration, ImagePath = :imagePath, Artists = :artists where SongID = :songID")
    boolean updateByID(String songID, String songName, String filePath, int duration, String imagePath, String artists);


    @Delete
    boolean delete(Song song);

    @Query("delete from Song where SongID like songID")
    boolean deleteByID(String songID);
}
