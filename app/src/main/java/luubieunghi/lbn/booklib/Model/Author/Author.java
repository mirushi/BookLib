package luubieunghi.lbn.booklib.Model.Author;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "author")
public class Author {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "authorID")
    private long authorID;

    @ColumnInfo (name = "authorName")
    String authorName;

    public Author (long authorID, String authorName){
        this.authorID = authorID;
        this.authorName = authorName;
    }

    @Ignore
    public Author(String authorName){
        this.authorID = -1;
        this.authorName = authorName;
    }

    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
