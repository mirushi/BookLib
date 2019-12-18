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
    private final int bookID;
    @ColumnInfo(name = "authorID")
    private final int authorID;

    public BookAuthor (final int bookID, final int authorID){
        this.bookID = bookID;
        this.authorID = authorID;
    }

    public int getBookID() {
        return bookID;
    }

    public int getAuthorID() {
        return authorID;
    }
}