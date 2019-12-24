package luubieunghi.lbn.booklib.Model.BookIdentityNum;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import luubieunghi.lbn.booklib.Model.Book.Book;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "bookidentitynum",
        primaryKeys = {"bookID", "IDName"},
        foreignKeys = @ForeignKey(onDelete = CASCADE, entity = Book.class, parentColumns = "bookID", childColumns = "bookID"))
public class BookIdentityNum {
    @ColumnInfo(name = "bookID")
    private long bookID;

    @NonNull
    @ColumnInfo(name = "IDName")
    private String IDName;

    @ColumnInfo(name = "IDValue")
    private String IDValue;

    public BookIdentityNum(long bookID, @NonNull String IDName, String IDValue) {
        this.bookID = bookID;
        this.IDName = IDName;
        this.IDValue = IDValue;
    }

    public long getBookID() {
        return bookID;
    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    @NonNull
    public String getIDName() {
        return IDName;
    }

    public void setIDName(@NonNull String IDName) {
        this.IDName = IDName;
    }

    public String getIDValue() {
        return IDValue;
    }

    public void setIDValue(String IDValue) {
        this.IDValue = IDValue;
    }
}
