package luubieunghi.lbn.booklib.Model.Publisher;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PublisherDAO {
    @Insert
    void Insert(Publisher publisher);

    @Delete
    void Delete(Publisher publisher);

    @Update
    void Update(Publisher publisher);

    @Query("SELECT * FROM publisher")
    List<Publisher> getAllPublisherStored();

    @Query("SELECT * FROM publisher WHERE publisher.publisherID = :publisherID")
    List<Publisher> getPublisherByID(int publisherID);

    @Query("SELECT * FROM publisher WHERE publisher.publisherName like '%' ||:subPublisherName||'%'")
    List<Publisher> searchForPublisher(String subPublisherName);

    @Query("SELECT * FROM publisher WHERE publisher.publisherName = :publisherName")
    List<Publisher> searchForExactPublisher(String publisherName);
}
