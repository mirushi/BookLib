package luubieunghi.lbn.booklib.Model.BookTag;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import luubieunghi.lbn.booklib.Model.Tag.Tag;

@Dao
public interface BookTagDAO {
    @Insert
    void Insert(BookTag bookTag);

    @Delete
    void Delete(BookTag bookTag);

    @Query("SELECT * from booktag where booktag.bookID = :bookID")
    List<BookTag> getAllBookTagsOfBook(int bookID);

    @Query("SELECT tag.tagID, tag.tagContent from booktag inner join tag " +
            "ON booktag.tagID = tag.tagID WHERE booktag.bookID = :bookID")
    List<Tag> getAllTagsOfBook(int bookID);

    @Query("DELETE FROM booktag")
    void nukeTable();

}
