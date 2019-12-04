package luubieunghi.lbn.booklib.Model.Album;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface Album_DAO {

    @Query("select * from Album")
    List<Album> getAll();

    @Query("select * from Album where AlbumID in (:IDS)")
    List<Album> getByIDs(String... IDS);

    @Query("select * from Album where AlbumID like :ID")
    List<Album> getByID(String ID);

    @Insert
    boolean insert(Album album);

    @Insert
    boolean insertAll(Album... albums);

    @Update
    boolean Update(Album album);

    @Query("update Album set AlbumName = :albumName, ImagePath = :imagePath where AlbumID = :albumID")
    boolean updateByID(String albumID, String albumName, String imagePath);

    @Delete
    boolean delete(Album album);

    @Query("delete from Album where AlbumID like albumID")
    boolean deleteByID(String albumID);

}
