package luubieunghi.lbn.booklib.Model.Album;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface Album_DAO {

    @Query("select * from Album")
    List<Album> getAll();

    @Query("select * from Album where AlbumID in (:IDS)")
    List<Album> getByIDs(String... IDS);

    @Query("select * from Album where AlbumID like :ID")
    List<Album> getByID(String ID);

    @Insert
    void insert(Album album);

    @Insert
    void insertAll(Album... albums);

    @Update
    void Update(Album album);

    @Query("update Album set AlbumName = :albumName, ImagePath = :imagePath where AlbumID = :albumID")
    void updateByID(String albumID, String albumName, String imagePath);

    @Delete
    void delete(Album album);

    @Query("delete from Album where AlbumID like :albumID")
    void deleteByID(String albumID);

    @Query("delete from Album")
    void deleteAll();

}
