package luubieunghi.lbn.booklib.Model.Tag;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class TagDAO {
    @Insert
    public abstract long Insert(Tag tag);

    @Delete
    public abstract void Delete(Tag tag);

    @Query("SELECT * FROM tag WHERE tag.tagContent LIKE '%' ||:subTag|| '%'")
    public abstract List<Tag> searchForTags(String subTag);

    @Query("SELECT * FROM tag")
    public abstract List<Tag> getAllTags();

    @Query("SELECT * FROM tag WHERE tag.tagContent = :tagName")
    public abstract Tag getExactTagFromName(String tagName);

    @Query("DELETE FROM tag")
    public abstract void nukeTable();

    @Transaction
    public List<Tag> insertIfNotExistAndReturnTags(String[] tagNames){
        List<Tag> result = new ArrayList<>();
        for (String tagName : tagNames){
            Tag tag = getExactTagFromName(tagName);
            if (tag == null){
                tag = new Tag(0, tagName);
                Long tagID = Insert(tag);
                tag.setTagID(tagID);
            }
            result.add(tag);
        }
        return result;
    }
}
