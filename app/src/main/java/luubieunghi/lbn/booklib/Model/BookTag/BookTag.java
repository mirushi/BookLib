package luubieunghi.lbn.booklib.Model.BookTag;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.Tag.Tag;

@Entity(tableName = "booktag",
        indices = {@Index("tagID")},
        primaryKeys = {"bookID","tagID"},
        foreignKeys = {@ForeignKey(entity = Book.class, parentColumns = "bookID", childColumns = "bookID"),
                        @ForeignKey(entity = Tag.class, parentColumns = "tagID", childColumns = "tagID")})
public class BookTag {
    @ColumnInfo(name = "bookID")
    private int bookID;

    @ColumnInfo(name = "tagID")
    private int tagID;

    public BookTag(int bookID, int tagID) {
        this.bookID = bookID;
        this.tagID = tagID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

}
