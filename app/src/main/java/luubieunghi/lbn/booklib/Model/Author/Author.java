package luubieunghi.lbn.booklib.Model.Author;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "author")
public class Author {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "authorID")
    private int authorID;

    @ColumnInfo (name = "authorName")
    String authorName;

    public Author (int authorID, String authorName){
        this.authorID = authorID;
        this.authorName = authorName;
    }

    @Ignore
    public Author(String authorName){
        this.authorID = -1;
        this.authorName = authorName;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
