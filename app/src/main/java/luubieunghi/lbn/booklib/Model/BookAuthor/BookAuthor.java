package luubieunghi.lbn.booklib.Model.BookAuthor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import luubieunghi.lbn.booklib.Model.Author.Author;
import luubieunghi.lbn.booklib.Model.Book.Book;

import static androidx.room.ForeignKey.CASCADE;

@Entity (tableName = "bookAuthor",
        indices = {@Index("authorID")},
        primaryKeys = {"bookID", "authorID"},
        foreignKeys = {
                @ForeignKey(onDelete = CASCADE, entity = Book.class, parentColumns = "bookID", childColumns = "bookID"),
                @ForeignKey(onDelete = CASCADE, entity = Author.class, parentColumns = "authorID", childColumns = "authorID")
                })
public class BookAuthor {

    @ColumnInfo(name = "bookID")
    private final long bookID;
    @ColumnInfo(name = "authorID")
    private final long authorID;

    public BookAuthor (final long bookID, final long authorID){
        this.bookID = bookID;
        this.authorID = authorID;
    }

    public long getBookID() {
        return bookID;
    }

    public long getAuthorID() {
        return authorID;
    }
}