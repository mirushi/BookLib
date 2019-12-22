package luubieunghi.lbn.booklib.Model.BookTag;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.Tag.Tag;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "booktag",
        indices = {@Index("tagID")},
        primaryKeys = {"bookID","tagID"},
        foreignKeys = {@ForeignKey(onDelete = CASCADE, entity = Book.class, parentColumns = "bookID", childColumns = "bookID"),
                        @ForeignKey(onDelete = CASCADE, entity = Tag.class, parentColumns = "tagID", childColumns = "tagID")})
public class BookTag {
    @ColumnInfo(name = "bookID")
    private long bookID;

    @ColumnInfo(name = "tagID")
    private long tagID;

    public BookTag(long bookID, long tagID) {
        this.bookID = bookID;
        this.tagID = tagID;
    }

    public long getBookID() {
        return bookID;
    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public long getTagID() {
        return tagID;
    }

    public void setTagID(long tagID) {
        this.tagID = tagID;
    }

}
