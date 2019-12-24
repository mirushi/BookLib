package luubieunghi.lbn.booklib.Model.Tag;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TagDAO {
    @Insert
    void Insert(Tag tag);

    @Delete
    void Delete(Tag tag);

    @Query("SELECT * FROM tag WHERE tag.tagContent LIKE '%' ||:subTag|| '%'")
    List<Tag> searchForTags(String subTag);

    @Query("SELECT * FROM tag")
    List<Tag> getAllTags();

    @Query("DELETE FROM tag")
    void nukeTable();
}
