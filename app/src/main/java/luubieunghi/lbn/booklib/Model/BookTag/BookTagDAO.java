package luubieunghi.lbn.booklib.Model.BookTag;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import luubieunghi.lbn.booklib.Model.Tag.Tag;

@Dao
public abstract class BookTagDAO {
    @Insert
    public abstract void Insert(BookTag bookTag);

    @Insert
    public abstract void Insert(List<BookTag> bookTags);

    @Delete
    public abstract void Delete(BookTag bookTag);

    @Query("SELECT * from booktag where booktag.bookID = :bookID")
    public abstract List<BookTag> getAllBookTagsOfBook(int bookID);

    @Query("SELECT tag.tagID, tag.tagContent from booktag inner join tag " +
            "ON booktag.tagID = tag.tagID WHERE booktag.bookID = :bookID")
    public abstract List<Tag> getAllTagsOfBook(int bookID);

    @Query("DELETE FROM booktag")
    public abstract void nukeTable();
}
